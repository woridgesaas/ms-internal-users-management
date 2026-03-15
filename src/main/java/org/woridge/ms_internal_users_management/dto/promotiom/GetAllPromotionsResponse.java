package org.woridge.ms_internal_users_management.dto.promotiom;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record GetAllPromotionsResponse(
        UUID id,
        BasicUserInfo basicUserInfo,
        String departmentName,
        String previousPostName,
        String newPostName,
        BigDecimal newSalary,
        LocalDate promotionDate
) {
}
