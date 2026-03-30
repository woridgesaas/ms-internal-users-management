package org.woridge.ms_internal_users_management.dto.payroll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woridge.ms_internal_users_management.enumeration.PayrollStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDetailResponseDTO {
    private UUID id;
    private LocalDate periodDate;
    private Double baseSalary;
    private Double grossSalary;
    private Double netSalary;
    private Double totalOvertimeHours;
    private Double totalAbsenceDays;
    private PayrollStatus status;
    private Instant generatedAt;
    // Lignes de détail (CNSS, AMO, IGR...)
    private List<PayrollLineDTO> details;
}