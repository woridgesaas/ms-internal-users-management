package org.woridge.ms_internal_users_management.dto.payroll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woridge.ms_internal_users_management.enumeration.PayrollStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollSummaryDTO {
    private UUID id;
    private LocalDate periodDate;   // 2026-03-01
    private int month;              // 3
    private int year;               // 2026
    private Double grossSalary;     // salaire brut
    private Double netSalary;       // net à payer
    private PayrollStatus status;   // EN_ATTENTE / VALIDEE / REJETEE

    private String employeeFullName;
    private String employeeCIN;
}