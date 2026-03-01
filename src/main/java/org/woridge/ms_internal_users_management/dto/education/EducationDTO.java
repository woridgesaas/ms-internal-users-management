package org.woridge.ms_internal_users_management.dto.education;

public record EducationDTO(
        String university,
        String fieldOfStudy,
        String degree,
        String startFinalYear
) {}
