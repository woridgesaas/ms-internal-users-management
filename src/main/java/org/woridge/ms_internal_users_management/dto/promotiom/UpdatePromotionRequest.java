package org.woridge.ms_internal_users_management.dto.promotiom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UpdatePromotionRequest {

    private UUID newPostId;

    @PositiveOrZero
    private BigDecimal bonus;


    private LocalDate promotionDate;
}