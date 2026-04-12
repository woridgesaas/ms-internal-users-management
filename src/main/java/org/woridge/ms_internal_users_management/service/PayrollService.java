package org.woridge.ms_internal_users_management
        .service;

import org.woridge.ms_internal_users_management.dto
        .payroll.PayrollDetailResponseDTO;
import org.woridge.ms_internal_users_management.dto
        .payroll.PayrollSummaryDTO;
import org.woridge.ms_internal_users_management
        .enumeration.ExportFormat;

import java.util.List;
import java.util.UUID;

public interface PayrollService {

    List<PayrollSummaryDTO> getMyPayrolls(
            UUID userId
    );

    List<PayrollSummaryDTO> getAllPayrollsByMonth(
            int month, int year
    );

    PayrollDetailResponseDTO getPayrollDetail(
            UUID payrollId
    );

    byte[] downloadPayroll(
            UUID payrollId,
            UUID userId,
            ExportFormat format
    ) throws Exception;

    // ← tenant ajouté
    void validatePayroll(UUID payrollId, String tenant);

    void rejectPayroll(UUID payrollId);

    void generatePayrolls(int month, int year);
}