package org.woridge.ms_internal_users_management.dto.promotiom;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdatePromotionRequest(

        @NotNull(message = "Promotion id is required")
        UUID promotionId,

        @NotNull(message = "New post id is required")
        UUID newPostId,

        @NotNull(message = "Promotion bonus is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Promotion bonus must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Promotion bonus must have max 10 digits and 2 decimals")
        BigDecimal bonus,

        @NotNull(message = "Promotion date is required")
        @PastOrPresent(message = "Promotion date can't be in the future")
        LocalDate promotionDate
) {
}