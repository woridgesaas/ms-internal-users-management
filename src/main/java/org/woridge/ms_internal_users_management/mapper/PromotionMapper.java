package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.promotiom.*;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.Promotion;
import org.woridge.ms_internal_users_management.model.SalaryHistory;
import org.woridge.ms_internal_users_management.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PromotionMapper {

    public static Promotion toPromotion(User user, Post previousPost, Post newPost, BigDecimal previousSalary, BigDecimal newSalary, LocalDate promotionDate) {

        return Promotion
                .builder()
                .previousPostName(previousPost.getName())
                .newPostName(newPost.getName())
                .promotionDate(promotionDate)
                .previousSalary(previousSalary)
                .newSalary(newSalary)
                .user(user)
                .previousPost(previousPost)
                .newPost(newPost)
                .build();
    }

    public static CreatePromotionResponse toCreatePromotionResponse(Promotion promotion) {

        return CreatePromotionResponse
                .builder()
                .id(promotion.getId())
                .previousPostName(promotion.getPreviousPostName())
                .newPostName(promotion.getNewPostName())
                .promotionDate(promotion.getPromotionDate())
                .previousSalary(promotion.getPreviousSalary())
                .newSalary(promotion.getNewSalary())
                .userID(promotion.getUser().getId())
                .build();
    }

    public static GetAllPromotionsResponse toGetAllPromotionsResponse(Promotion promotion) {

        BasicUserInfo basicUserInfo = new BasicUserInfo(
                promotion.getUser().getFirstName() + " " + promotion.getUser().getLastName(),
                promotion.getUser().getPicturePath()
        );

        return GetAllPromotionsResponse
                .builder()
                .id(promotion.getId())
                .basicUserInfo(basicUserInfo)
                .departmentName(promotion.getUser().getDepartment().getName())
                .previousPostName(promotion.getPreviousPostName())
                .newPostName(promotion.getNewPostName())
                .newSalary(promotion.getNewSalary())
                .promotionDate(promotion.getPromotionDate())
                .build();
    }

    public static UserPromotionInfoResponse toUserPromotionInfoResponse(User user, SalaryHistory salaryHistory) {

        return UserPromotionInfoResponse
                .builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .departmentName(user.getDepartment().getName())
                .currentPost(user.getPost().getName())
                .currentSalary(salaryHistory.getSalary())
                .build();
    }

    public static SalaryHistoryResponse toSalaryHistoryResponse(SalaryHistory salaryHistory, User user) {

        return SalaryHistoryResponse
                .builder()
                .id(salaryHistory.getId())
                .userFullName(user.getFirstName() + " " + user.getLastName())
                .postName(salaryHistory.getPostName())
                .salary(salaryHistory.getSalary())
                .validFrom(salaryHistory.getValidFrom())
                .validTo(salaryHistory.getValidTo())
                .build();
    }

    public static void updatePromotion(Promotion promotion, SalaryHistory salaryHistory, Post newPost, BigDecimal newSalary, UpdatePromotionRequest updatePromotionRequest) {

        promotion.setNewSalary(newSalary);
        promotion.setNewPost(newPost);
        promotion.setNewPostName(newPost.getName());
        promotion.setPromotionDate(updatePromotionRequest.promotionDate());

        salaryHistory.setSalary(newSalary);
        salaryHistory.setPostName(newPost.getName());

        promotion.getUser().setPost(newPost);
    }
}