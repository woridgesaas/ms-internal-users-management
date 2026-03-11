package org.woridge.ms_internal_users_management.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.promotiom.*;
import org.woridge.ms_internal_users_management.exception.promotion.PromotionException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.mapper.PromotionMapper;
import org.woridge.ms_internal_users_management.model.*;
import org.woridge.ms_internal_users_management.repository.*;
import org.woridge.ms_internal_users_management.service.PromotionService;
import org.woridge.ms_internal_users_management.service.SalaryService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionServiceImpl implements PromotionService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionMapper mapper;
    private final SalaryService salaryService;


    @Override
    public PromotionEmployeeInfoResponse getEmployeeInfoByCin(String cin) {

        User user = findUser(cin);

        if (user.getPost() == null)
            throw new PromotionException("User has no assigned post");

        SalaryHistory salary = salaryService.getCurrentSalary(user);

        return new PromotionEmployeeInfoResponse(
                user.getFirstName() + " " + user.getLastName(),
                user.getDepartment().getName(),
                user.getPost().getName(),
                salary.getSalary()
        );
    }


    @Override
    public PromotionResponse promoteUser(CreatePromotionRequest request) {

        User user = findUser(request.getCin());

        Post currentPost = user.getPost();
        Post newPost = resolvePost(request.getNewPostId(), currentPost);

        SalaryHistory currentSalaryHistory = salaryService.getCurrentSalary(user);

        BigDecimal previousSalary = currentSalaryHistory.getSalary();


        BigDecimal newSalary = calculateSalary(previousSalary, request.getBonus());
        salaryService.closeCurrentSalary(user);

        salaryService.createNewSalary(user, newPost, newSalary);

        Promotion promotion = createPromotion(
                user,
                currentPost,
                newPost,
                previousSalary,
                newSalary
        );

        user.setPost(newPost);

        return mapper.toResponse(promotion);
    }


    private User findUser(String cin){
        return userRepository.findByCIN(cin)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private BigDecimal calculateSalary(BigDecimal currentSalary, BigDecimal bonus) {

        if (bonus == null) {
            return currentSalary;
        }

        if (bonus.compareTo(BigDecimal.ZERO) < 0) {
            throw new PromotionException("Bonus cannot be negative");
        }

        return currentSalary.add(bonus);
    }

    @Override
    public PromotionResponse updatePromotion(UUID id, UpdatePromotionRequest request) {

        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new PromotionException("Promotion not found"));

        BigDecimal bonus = Optional.ofNullable(request.getBonus())
                .orElse(BigDecimal.ZERO);

        if (bonus.compareTo(BigDecimal.ZERO) < 0)
            throw new PromotionException("Bonus cannot be negative");

        SalaryHistory history = salaryService.getCurrentSalary(promotion.getUser());

        BigDecimal newSalary = history.getSalary().add(bonus);
        Post newPost = resolvePost(request.getNewPostId(), promotion.getUser().getPost());
        promotion.setNewSalary(newSalary);
        promotion.setNewPost(newPost.getName());

        if (request.getPromotionDate() != null) {
            promotion.setPromotionDate(request.getPromotionDate());
        }


        history.setSalary(newSalary);
        history.setPostName(newPost.getName());

        promotion.getUser().setPost(newPost);

        return mapper.toResponse(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> getAllPromotions() {

        return promotionRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    private Post resolvePost(UUID newPostId, Post currentPost) {

        if (newPostId == null) {
            return currentPost;
        }

        return postRepository.findById(newPostId)
                .orElseThrow(() -> new PromotionException("Post not found"));
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


    public List<SalaryHistoryResponse> getSalaryHistory(String cin) {

        User user = findUser(cin);
        return salaryService.getSalaryHistory(user)
                .stream()
                .map(h -> new SalaryHistoryResponse(
                        h.getId(),
                        user.getFirstName() + " " + user.getLastName(),
                        h.getPostName(),
                        h.getSalary(),
                        h.getValidFrom(),
                        h.getValidTo()
                ))
                .toList();
    }

}