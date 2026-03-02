package org.woridge.ms_internal_users_management.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.promotiom.*;
import org.woridge.ms_internal_users_management.exception.promotion.PromotionException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.model.*;
import org.woridge.ms_internal_users_management.repository.*;
import org.woridge.ms_internal_users_management.service.PromotionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionServiceImpl implements PromotionService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PromotionRepository promotionRepository;
    private final SalaryHistoryRepository salaryHistoryRepository;

    // ============================================================
    // GET EMPLOYEE INFO
    // ============================================================
    @Override
    public PromotionEmployeeInfoResponse getEmployeeInfoByCin(String cin) {

        User user = userRepository.findByCIN(cin)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getPost() == null)
            throw new PromotionException("User has no assigned post");

        return new PromotionEmployeeInfoResponse(
                user.getFirstName() + " " + user.getLastName(),
                user.getDepartment().getName(),
                user.getPost().getName(),
                user.getPost().getBaseSalary()
        );
    }

    // ============================================================
    // CREATE PROMOTION
    // ============================================================
    @Override
    public PromotionResponse promoteUser(CreatePromotionRequest request) {

        User user = userRepository.findByCIN(request.getCin())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getPost() == null)
            throw new PromotionException("User has no assigned post");

        Post currentPost = user.getPost();
        BigDecimal currentSalary = currentPost.getBaseSalary();

        BigDecimal bonus = request.getBonus() != null
                ? request.getBonus()
                : BigDecimal.ZERO;

        if (bonus.compareTo(BigDecimal.ZERO) < 0)
            throw new PromotionException("Bonus cannot be negative");

        BigDecimal newSalary = currentSalary.add(bonus);

        Post newPost = resolvePost(request.getNewPostId(), currentPost);

        closeCurrentSalary(user);
        createNewSalary(user, newPost, newSalary);

        Promotion promotion = createPromotion(
                user,
                currentPost,
                newPost,
                currentSalary,
                newSalary
        );

        user.setPost(newPost);

        return buildResponse(promotion);
    }

    // ============================================================
    // UPDATE PROMOTION
    // ============================================================
    @Override
    public PromotionResponse updatePromotion(UUID id, UpdatePromotionRequest request) {

        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new PromotionException("Promotion not found"));

        BigDecimal bonus = request.getBonus() != null
                ? request.getBonus()
                : BigDecimal.ZERO;

        if (bonus.compareTo(BigDecimal.ZERO) < 0)
            throw new PromotionException("Bonus cannot be negative");

        BigDecimal newSalary = promotion.getPreviousSalary().add(bonus);

        Post newPost = postRepository.findById(request.getNewPostId())
                .orElseThrow(() -> new PromotionException("Post not found"));

        promotion.setNewSalary(newSalary);
        promotion.setNewPost(newPost.getName());
        promotion.setPromotionDate(request.getPromotionDate());

        SalaryHistory history = salaryHistoryRepository
                .findTopByUserOrderByValidFromDesc(promotion.getUser())
                .orElseThrow(() -> new PromotionException("Salary history not found"));

        history.setSalary(newSalary);
        history.setPostName(newPost.getName());

        promotion.getUser().setPost(newPost);

        return buildResponse(promotion);
    }

    // ============================================================
    // GET ALL
    // ============================================================
    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> getAllPromotions() {

        return promotionRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    // ============================================================
    // PRIVATE METHODS
    // ============================================================

    private Post resolvePost(UUID newPostId, Post currentPost) {

        if (newPostId == null)
            return currentPost;

        return postRepository.findById(newPostId)
                .orElseThrow(() -> new PromotionException("Post not found"));
    }

    private void closeCurrentSalary(User user) {

        salaryHistoryRepository.findTopByUserOrderByValidFromDesc(user)
                .ifPresent(history ->
                        history.setValidTo(LocalDate.now())
                );
    }

    private void createNewSalary(User user, Post post, BigDecimal salary) {

        SalaryHistory history = new SalaryHistory();
        history.setUser(user);
        history.setSalary(salary);
        history.setPostName(post.getName());
        history.setValidFrom(LocalDate.now());

        salaryHistoryRepository.save(history);
    }

    private Promotion createPromotion(
            User user,
            Post previousPost,
            Post newPost,
            BigDecimal previousSalary,
            BigDecimal newSalary
    ) {

        Promotion promotion = new Promotion();
        promotion.setUser(user);
        promotion.setPreviousSalary(previousSalary);
        promotion.setNewSalary(newSalary);
        promotion.setPreviousPost(previousPost.getName());
        promotion.setNewPost(newPost.getName());
        promotion.setPromotionDate(LocalDate.now());

        return promotionRepository.save(promotion);
    }

    private PromotionResponse buildResponse(Promotion promotion) {

        return new PromotionResponse(
                promotion.getId(),
                promotion.getUser().getFirstName() + " " + promotion.getUser().getLastName(),
                promotion.getUser().getDepartment().getName(),
                promotion.getPreviousPost(),
                promotion.getNewPost(),
                promotion.getPreviousSalary(),
                promotion.getNewSalary(),
                promotion.getPromotionDate()
        );
    }
}