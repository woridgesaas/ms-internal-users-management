package org.woridge.ms_internal_users_management.dto.payroll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollCalculationResult {
    private double baseSalary;
    private double bonusAmount;
    private double grossSalary;      // SB

    private double overtime25Amount;  // MAD
    private double overtime50Amount;
    private double overtime100Amount;


    // --- PARTIE SOCIALES ---
    private double cnssAmount;       //  CNSS (Capped)
    private double amoAmount;        //  AMO (Not Capped)

    // ---FISCALE (taxes) ---
    private double professionalExpenses; // Frais professionnels deduits not taxabale
    private double netTaxableSalary;    // Salaire which can be taxaed
    private double igrBrut;            // Taxes on Revenue
    private double familyDeductions;    //  (ex: 30DH x nb persons)
    private double igrNet;             // IGR final after deductions

    // --- result FINAL ---
    private double netToPay;           // Total to pay to employe


}
