package org.woridge.ms_internal_users_management.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollCalculationResult;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollInput;
import org.woridge.ms_internal_users_management.model.Payroll;
import org.woridge.ms_internal_users_management.model.PayrollDetail;
import org.woridge.ms_internal_users_management.model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@Component
public class PayrollFactory {
    @PersistenceContext
    private EntityManager entityManager; // to get reference

    public Payroll createPayroll(PayrollInput input, PayrollCalculationResult result, LocalDate periodDate) {
        Payroll payroll = Payroll.builder()
                .user(entityManager.getReference(User.class, input.getUserId()))
                .periodDate(periodDate)
                .baseSalaryAtDate(input.getBaseSalary())
                .totalOvertimeHours(input.overtimeHours())
                .totalAbsenceDays(26 - input.getWorkedDays())
                .grossSalary(result.getGrossSalary())
                .netSalary(result.getNetToPay())
                .generatedAt(Instant.now())
                .isValidated(false)
                .details(new ArrayList<>())
                .build();

        fillPayrollDetails(payroll, input, result);

        return payroll;
    }
    private void fillPayrollDetails(Payroll payroll, PayrollInput input, PayrollCalculationResult result) {
        // Salaire de base
        payroll.addDetail(buildDetail("Salaire de Base", "GAIN", input.getBaseSalary(), 100.0, result.getBaseSalary()));

        // Heures Supplémentaires (Ventilées)
        if (result.getOvertime25Amount() > 0)
            payroll.addDetail(buildDetail("Heures Sup 25%", "GAIN", input.getOvertime25(), 25.0, result.getOvertime25Amount()));

        if (result.getOvertime50Amount() > 0)
            payroll.addDetail(buildDetail("Heures Sup 50%", "GAIN", input.getOvertime50(), 50.0, result.getOvertime50Amount()));

        if (result.getOvertime100Amount() > 0)
            payroll.addDetail(buildDetail("Heures Sup 100%", "GAIN", input.getOvertime100(), 100.0, result.getOvertime100Amount()));

        // Primes & Fiscalité
        if (result.getBonusAmount() > 0)
            payroll.addDetail(buildDetail("Primes", "GAIN", null, null, result.getBonusAmount()));

        if (result.getFamilyDeductions() > 0)
            payroll.addDetail(buildDetail("Déductions Familiales", "FISCAL", null, null, result.getFamilyDeductions()));

        // Infos Hellpers(SBI / SNI)
        payroll.addDetail(buildDetail("SALAIRE BRUT IMPOSABLE (SBI)", "INFO", null, null, result.getGrossSalary()));
        payroll.addDetail(buildDetail("Net Imposable", "INFO", null, null, result.getNetTaxableSalary()));

        // Social infos
        double cnssBase = Math.min(result.getGrossSalary(), 6480.0); // Plafond 2026
        payroll.addDetail(buildDetail("CNSS", "RETENUE", cnssBase, 4.48, result.getCnssAmount()));
        payroll.addDetail(buildDetail("AMO", "RETENUE", result.getGrossSalary(), 2.26, result.getAmoAmount()));
        payroll.addDetail(buildDetail("Frais Professionnels", "FISCAL", result.getGrossSalary(), 25.0, result.getProfessionalExpenses()));

        if (result.getIgrNet() > 0)
            payroll.addDetail(buildDetail("IGR", "RETENUE", result.getNetTaxableSalary(), null, result.getIgrNet()));
    }

    private PayrollDetail buildDetail(String label, String type, Double base, Double rate, Double amount) {
        return PayrollDetail.builder()
                .label(label)
                .type(type)
                .baseValue(base)
                .rate(rate)
                .amount(amount)
                .build();
    }

}
