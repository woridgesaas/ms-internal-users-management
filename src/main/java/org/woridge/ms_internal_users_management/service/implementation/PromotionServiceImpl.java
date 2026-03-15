package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.promotiom.*;
import org.woridge.ms_internal_users_management.exception.post.PostNotFoundException;
import org.woridge.ms_internal_users_management.exception.promotion.UserHasNoAssignedPostException;
import org.woridge.ms_internal_users_management.exception.promotion.PromotionIdNotMatchedException;
import org.woridge.ms_internal_users_management.exception.promotion.PromotionNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.mapper.PromotionMapper;
import org.woridge.ms_internal_users_management.model.*;
import org.woridge.ms_internal_users_management.repository.*;
import org.woridge.ms_internal_users_management.service.PromotionService;
import org.woridge.ms_internal_users_management.service.SalaryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class PromotionServiceImpl implements PromotionService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PromotionRepository promotionRepository;
    private final SalaryService salaryService;

    @Override
    public CreatePromotionResponse promoteUser(CreatePromotionRequest createPromotionRequest) {

        User user = findUser(createPromotionRequest.CIN());

        Post currentPost = user.getPost();

        Post newPost = getNewPost(createPromotionRequest.newPostId(), currentPost);

        SalaryHistory currentSalaryHistory = salaryService.getCurrentSalary(user);

        BigDecimal currentSalary = currentSalaryHistory.getSalary();

        BigDecimal newSalary = currentSalary.add(createPromotionRequest.bonus());

        salaryService.closeCurrentSalary(user);

        salaryService.createNewSalary(user, newPost, newSalary);

        Promotion promotion = PromotionMapper.toPromotion(user, currentPost, newPost, currentSalary, newSalary, createPromotionRequest.promotionDate());

        user.setPost(newPost);

        promotionRepository.save(promotion);

        return PromotionMapper.toCreatePromotionResponse(promotion);
    }

    @Override
    public void updatePromotion(UUID id, UpdatePromotionRequest updatePromotionRequest) {

        if (!id.equals(updatePromotionRequest.promotionId())) {

            throw new PromotionIdNotMatchedException("Promotion id aren't matched");
        }

        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new PromotionNotFoundException("Promotion with the following id : " + id + " not found"));

        SalaryHistory salaryHistory = salaryService.getCurrentSalary(promotion.getUser());

        BigDecimal newSalary = salaryHistory.getSalary().add(updatePromotionRequest.bonus());

        Post newPost = getNewPost(updatePromotionRequest.newPostId(), promotion.getUser().getPost());

        if (!hasPromotionChanged(promotion, newSalary, newPost, updatePromotionRequest)) {

            return;
        }

        PromotionMapper.updatePromotion(promotion, salaryHistory, newPost, newSalary, updatePromotionRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetAllPromotionsResponse> getAllPromotions() {

        return promotionRepository.findAll()
                .stream()
                .map(PromotionMapper::toGetAllPromotionsResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserPromotionInfoResponse getUserPromotionInfo(String CIN) {

        User user = findUser(CIN);

        if (user.getPost() == null) {
            throw new UserHasNoAssignedPostException("User with the following CIN : " + CIN + " has no assigned post");
        }

        SalaryHistory salaryHistory = salaryService.getCurrentSalary(user);

        return PromotionMapper.toUserPromotionInfoResponse(user, salaryHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalaryHistoryResponse> getSalaryHistory(String CIN) {

        User user = findUser(CIN);

        return salaryService.getSalaryHistory(user)
                .stream()
                .map(salaryHistory -> PromotionMapper.toSalaryHistoryResponse(salaryHistory, user))
                .toList();
    }

    private User findUser(String CIN) {

        return userRepository.findByCIN(CIN).orElseThrow(() ->
                new UserNotFoundException("User with the following CIN : " + CIN + " not found"));
    }

    private Post getNewPost(UUID newPostId, Post currentPost) {

        return newPostId == null ? currentPost : postRepository.findById(newPostId).orElseThrow(() ->
                new PostNotFoundException("Post with the following id : " + newPostId + "not found"));
    }

    private boolean hasPromotionChanged(Promotion promotion, BigDecimal newSalary, Post newPost, UpdatePromotionRequest updatePromotionRequest) {

        return promotion.getNewSalary().compareTo(newSalary) != 0
                || !promotion.getNewPost().getId().equals(newPost.getId())
                || !promotion.getPromotionDate().equals(updatePromotionRequest.promotionDate());
    }
}