package org.woridge.ms_internal_users_management.dto.user;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateUserPersonalInformationRequest(

        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name must not exceed 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name must not exceed 50 characters")
        String lastName,

        @NotBlank(message = "CIN is required")
        @Pattern(
                regexp = "^[A-Za-z]{1,2}[0-9]{4,8}$",
                message = "CIN invalide (ex: AB123456)"
        )
        String cin,

        @NotBlank(message = "Email is required")
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "Email format is invalid"
        )
        @Size(max = 50, message = "Email must not exceed 50 characters")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^(\\+212|0)[5-7][0-9]{8}$",
                message = "Numéro marocain invalide"
        )
        String phone,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @Size(max = 500, message = "Address must not exceed 500 characters")
        String address,

        @Size(max = 500, message = "Profile image path must not exceed 500 characters")
        String profileImageUrl

) {}