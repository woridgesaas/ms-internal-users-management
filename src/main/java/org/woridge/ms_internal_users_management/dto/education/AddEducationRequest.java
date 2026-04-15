package org.woridge.ms_internal_users_management.dto.education;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.woridge.ms_internal_users_management.enumeration.EducationType;

import java.time.LocalDate;

public record AddEducationRequest(

        @NotNull(message = "Type is required")
        EducationType type,

        @NotBlank(message = "Title is required")
        @Size(max = 50, message = "Title must not exceed 50 characters")
        String title,

        @NotNull(message = "Start date is required")
        @PastOrPresent(message = "Start date must be in the past or present")
        LocalDate startDate,

        LocalDate endDate,

        String description,

        @NotBlank(message = "Field of study is required")
        @Size(max = 50, message = "Field of study must not exceed 50 characters")
        String fieldOfStudy,

        @NotBlank(message = "Institution is required")
        @Size(max = 50, message = "Institution must not exceed 50 characters")
        String institution
) {
}
