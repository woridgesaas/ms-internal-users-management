package org.woridge.ms_internal_users_management.dto.promotiom;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UserPromotionInfoResponse(
        String fullName,
        String departmentName,
        String currentPost,
        BigDecimal currentSalary
) {
}