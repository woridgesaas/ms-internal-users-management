package org.woridge.ms_internal_users_management.dto.education;

import lombok.Builder;
import org.woridge.ms_internal_users_management.enumeration.EducationType;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record EducationSummary(
        UUID id,
        EducationType type,
        String title,
        LocalDate startDate,
        LocalDate endDate,
        String description,
        String fieldOfStudy,
        String institution
) {
}
