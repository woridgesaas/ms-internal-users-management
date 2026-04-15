package org.woridge.ms_internal_users_management.dto.professional_experience;

import lombok.Builder;
import org.woridge.ms_internal_users_management.enumeration.EmploymentType;
import org.woridge.ms_internal_users_management.enumeration.ExperienceLevel;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ProfessionalExperienceSummary(
        UUID id,
        EmploymentType employmentType,
        ExperienceLevel experienceLevel,
        String companyName,
        String companyLocation,
        String jobTitle,
        LocalDate startDate,
        LocalDate endDate,
        Boolean isCurrent,
        String description
) {
}
