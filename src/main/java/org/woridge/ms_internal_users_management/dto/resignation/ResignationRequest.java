package org.woridge.ms_internal_users_management.dto.resignation;

import java.time.LocalDate;

public record ResignationRequest(
        String cin,
        String reason,
        LocalDate noticeDate,
        LocalDate resignationDate
) {}