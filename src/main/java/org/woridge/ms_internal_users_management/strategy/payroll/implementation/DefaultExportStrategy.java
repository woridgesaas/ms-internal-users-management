package org.woridge.ms_internal_users_management.strategy.payroll.implementation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.enumeration.ExportFormat;
import org.woridge.ms_internal_users_management.model.*;
import org.woridge.ms_internal_users_management.repository.PayrollRepository;
import org.woridge.ms_internal_users_management.strategy.payroll.PayrollExportStrategy;

import java.io.InputStream;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class DefaultExportStrategy implements PayrollExportStrategy {
    @Autowired
    private PayrollRepository payrollRepository;
    @Override
    public byte[] export(Payroll payroll) throws Exception {
        InputStream reportStream = getClass().getResourceAsStream("/reports/standard.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        Map<String, Object> parameters = new HashMap<>();
        User user = payroll.getUser();
        SocialInfo socialInfo = user.getSocialInfo();
        Post post = user.getPost();
        String statusFr = "Célibataire";
        if (user.getStatus().toString().equals("ACTIVE")) statusFr = "Marié(e)";

        // --- Informations de base ---
        parameters.put("companyName", "Entreprise SA");
        parameters.put("companyICE", "001234567890001");
        parameters.put("employeeName", user.getFirstName() + " " + user.getLastName());
        parameters.put("qualification", post != null ? post.getName() : "");
        parameters.put("matricule", user.getId().toString());
        parameters.put("cin", user.getCIN());
        parameters.put("hiringDate", Date.from(user.getCreatedAt()));
        parameters.put("cimrNumber", "");
        parameters.put("cnssNumber", socialInfo != null ? socialInfo.getCnssNumber() : "");
        parameters.put("birthdate", Date.from(user.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        parameters.put("maritalStatus", statusFr);
        parameters.put("nbDependants", socialInfo != null ? socialInfo.getFamilyDeductions() : 0);
        parameters.put("period", payroll.getPeriodDate().getMonthValue() + "/" + payroll.getPeriodDate().getYear());

        // ---Data of payroll in DB ---
        parameters.put("baseSalary", payroll.getBaseSalaryAtDate());
        parameters.put("grossSalary", payroll.getGrossSalary());
        parameters.put("netToPay", payroll.getNetSalary());
        parameters.put("overtimeHours", payroll.getTotalOvertimeHours());
        parameters.put("absenceDays", payroll.getTotalAbsenceDays());
        parameters.put("generationDate", Date.from(payroll.getGeneratedAt()));
        // --- Calcules not stores in the database : ---
        double workedDays = 26 - payroll.getTotalAbsenceDays();
        double hourlyWage = payroll.getBaseSalaryAtDate() /191;

        parameters.put("workedDays", (int) workedDays);
        parameters.put("hourlyWage", hourlyWage);

        double totalGains = 0;
        double totalRetenues = 0;

        for (PayrollDetail detail : payroll.getDetails()) {
            if ("GAIN".equals(detail.getType())) {
                totalGains += detail.getAmount();
            } else if ("RETENUE".equals(detail.getType())) {
                totalRetenues += detail.getAmount();
            }
        }


        parameters.put("totalGains", totalGains);
        parameters.put("totalRetenues", totalRetenues);
        parameters.put("grossSalary", payroll.getGrossSalary());
        parameters.put("netToPay", payroll.getNetSalary());
        parameters.put("baseSalary", payroll.getBaseSalaryAtDate());
        parameters.put("generationDate", Date.from(payroll.getGeneratedAt()));

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(payroll.getDetails());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    @Override
    public ExportFormat getFormat() {
        return ExportFormat.PDF;
    }
}
