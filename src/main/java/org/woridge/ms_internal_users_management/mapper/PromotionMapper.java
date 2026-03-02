package org.woridge.ms_internal_users_management.mapper;

import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.promotiom.PromotionResponse;
import org.woridge.ms_internal_users_management.model.Promotion;

@Component
public class PromotionMapper {

    public PromotionResponse toResponse(Promotion promotion) {
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