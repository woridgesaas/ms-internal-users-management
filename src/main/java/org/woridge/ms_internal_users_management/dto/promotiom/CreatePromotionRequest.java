package org.woridge.ms_internal_users_management.dto.promotiom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreatePromotionRequest {

    @NotBlank
    private String cin;

    private UUID newPostId; // optionnel

    @PositiveOrZero(message = "Bonus must be positive or zero")
    private BigDecimal bonus;
}