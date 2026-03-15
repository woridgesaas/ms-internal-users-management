package org.woridge.ms_internal_users_management.dto.post;

import jakarta.validation.constraints.*;
import org.woridge.ms_internal_users_management.enumeration.Status;

import java.math.BigDecimal;

public record AddPostRequest(

        @NotBlank(message = "Post name is required")
        @Size(max = 50, message = "Post name must not exceed 50 characters")
        String name,

        @NotNull(message = "Base salary is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Base salary must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Base salary must have max 10 digits and 2 decimals")
        BigDecimal baseSalary,

        @NotNull(message = "Post status is required")
        Status status
) {
}
