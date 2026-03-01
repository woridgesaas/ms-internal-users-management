package org.woridge.ms_internal_users_management.dto.resignation;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ResignationResponse(
        UUID id,
        UUID userId,
        String userFullName,
        String departmentName,
        String reason,
        LocalDate noticeDate,
        LocalDate resignationDate,
        Instant createdAt
) {}