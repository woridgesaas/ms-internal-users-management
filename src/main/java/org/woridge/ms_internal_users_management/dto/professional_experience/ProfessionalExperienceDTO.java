package org.woridge.ms_internal_users_management.dto.professional_experience;

import java.time.LocalDate;

public record ProfessionalExperienceDTO(
        String companyName,
        String jobTitle,
        String employmentType,
        String location,
        LocalDate startDate,
        LocalDate endDate,
        Boolean isCurrent,
        String description,
        String logoPath
) {}
