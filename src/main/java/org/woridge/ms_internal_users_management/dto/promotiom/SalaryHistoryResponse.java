package org.woridge.ms_internal_users_management.dto.promotiom;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SalaryHistoryResponse(

        UUID id,
        String employeeName,
        String postName,
        BigDecimal salary,
        LocalDate validFrom,
        LocalDate validTo
) {}