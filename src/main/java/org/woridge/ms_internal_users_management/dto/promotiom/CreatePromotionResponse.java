package org.woridge.ms_internal_users_management.dto.promotiom;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CreatePromotionResponse(
        UUID id,
        String previousPostName,
        String newPostName,
        LocalDate promotionDate,
        BigDecimal previousSalary,
        BigDecimal newSalary,
        UUID userID
) {
}
