package org.woridge.ms_internal_users_management.dto.professional_experience;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.woridge.ms_internal_users_management.enumeration.EmploymentType;
import org.woridge.ms_internal_users_management.enumeration.ExperienceLevel;

import java.time.LocalDate;

public record AddProfessionalExperienceRequest(

        @NotNull(message = "Employment type is required")
        EmploymentType employmentType,

        @NotNull(message = "Experience level is required")
        ExperienceLevel experienceLevel,

        @NotBlank(message = "Company name is required")
        @Size(max = 50, message = "Company name must not exceed 50 characters")
        String companyName,

        @NotBlank(message = "Company location is required")
        @Size(max = 50, message = "company location must not exceed 50 characters")
        String companyLocation,

        @NotBlank(message = "Job title is required")
        @Size(max = 50, message = "Job title must not exceed 50 characters")
        String jobTitle,

        @NotNull(message = "Start date is required")
        @PastOrPresent(message = "Start date must be in the past or present")
        LocalDate startDate,

        LocalDate endDate,

        Boolean isCurrent,
        String description
) {
}
