package org.woridge.ms_internal_users_management.dto.promotiom;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record SalaryHistoryResponse(
        UUID id,
        String userFullName,
        String postName,
        BigDecimal salary,
        LocalDate validFrom,
        LocalDate validTo
) {}