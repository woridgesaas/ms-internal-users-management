package org.woridge.ms_internal_users_management.dto.payroll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollLineDTO {
    private String label;     // "CNSS", "Salaire de Base"...
    private String type;      // "GAIN", "RETENUE", "INFO", "FISCAL"
    private Double baseValue; // base de calcul
    private Double rate;      // taux en %
    private Double amount;    // montant en DH
}