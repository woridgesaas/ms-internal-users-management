package org.woridge.ms_internal_users_management.service.implementation;

import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollCalculationResult;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollInput;
import org.woridge.ms_internal_users_management.service.PayrolCalculatorService;

@Service
public class PayrolCalculatorServiceImpl implements PayrolCalculatorService {
    public PayrollCalculationResult calculate(PayrollInput input) {
        // --- 1. Raw Salary ---
        double effectiveBaseSalary = round2((input.getBaseSalary() / 26) * input.getWorkedDays());
        double hourlyWage = input.getBaseSalary() / 191.67;

        double amount25 = input.getOvertime25() * (hourlyWage * OVERTIME_RATE_DAY);
        double amount50 = input.getOvertime50() * (hourlyWage * OVERTIME_RATE_NIGHT);
        double amount100 = input.getOvertime100() * (hourlyWage * OVERTIME_RATE_WEEKEND);

        double totalOvertimeMAD = amount25 + amount50 + amount100;
        double totalBrut = round2(effectiveBaseSalary + totalOvertimeMAD + input.getBonus());

        // --- 2. Contributions sociales ---
        double cnssBase = Math.min(totalBrut, PayrolCalculatorService.CNSS_CEILING);
        double cnssAmount = round2(cnssBase * PayrolCalculatorService.CNSS_RATE); // 4.48%
        double amoAmount = round2(totalBrut * PayrolCalculatorService.AMO_RATE); // 2.26%

        // --- 3. Fair Profsional (FP) ---
        double professionalExpenses = Math.min(totalBrut * PayrolCalculatorService.PROFESSIONAL_EXPENSES_RATE,
                PayrolCalculatorService.PROFESSIONAL_EXPENSES_CEILING);
        professionalExpenses = round2(professionalExpenses);

        // --- 4. Raw taxable (SNI) ---
        double taxableNet = round2(totalBrut - cnssAmount - amoAmount - professionalExpenses);

        // --- 5.l'IGR raw : tax en revenue ---
        double igrBrut = round2(calculateIGR(taxableNet));

        // --- 6. Familly deductions ---
        int numberOfDependants = Math.min(input.getFamilyDeductions(), 6); // max 6
        double familyDeduction = round2(numberOfDependants * PayrolCalculatorService.FAMILY_DEDUCTION_PER_PERSON_MONTHLY);

        // --- 7. IGR raw after deduction---
        double igrNet = round2(Math.max(0, igrBrut - familyDeduction));

        // --- 8. total Pay---
        double totalRetenues = round2(cnssAmount + amoAmount + igrNet);
        double netToPay = round2(totalBrut - totalRetenues);

        return new PayrollCalculationResult(
                effectiveBaseSalary,
                input.getBonus(),
                totalBrut,
                amount25,
                amount50,
                amount100,
                cnssAmount,
                amoAmount,
                professionalExpenses,
                taxableNet,
                igrBrut,
                familyDeduction,
                igrNet,
                netToPay
        );
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    public double calculateIGR(double sniMonthly) {

        if (sniMonthly <= 3333.33) return 0;                           // tranche 0%
        if (sniMonthly <= 5000.00) return (sniMonthly * 0.10) - 333.33;      // tranche 10%
        if (sniMonthly <= 6666.67) return (sniMonthly * 0.20) - 833.33;      // tranche 20%
        if (sniMonthly <= 8333.33) return (sniMonthly * 0.30) - 1500.00;     // tranche 30%
        if (sniMonthly <= 15000.00) return (sniMonthly * 0.34) - 1833.33;    // tranche 34%
        return (sniMonthly * 0.37) - 2283.33;                         // tranche 37%
    }

}
