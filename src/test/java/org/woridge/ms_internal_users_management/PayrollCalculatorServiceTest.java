package org.woridge.ms_internal_users_management;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollInput;
import org.woridge.ms_internal_users_management.service.PayrolCalculatorService;
import org.woridge.ms_internal_users_management.service.implementation.PayrolCalculatorServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

@ActiveProfiles("dev")
public class PayrollCalculatorServiceTest {

    private PayrolCalculatorService payrollService=null;
    private PayrollInput input=null;
    private PayrollInput input2=null;
    private PayrollInput input3=null;
    @BeforeEach
    public void setUp(){
        this.payrollService=new PayrolCalculatorServiceImpl();
        this.input=new PayrollInput(UUID.randomUUID(), "Zakariyae El Amrani", "AB123456", 12000.00, 3, 26, "CNSS998877", 1500.00 , 6,2,2);
        this.input2 = new PayrollInput(UUID.randomUUID(), "Laila Benmoussa", "LB987654", 8000.00, 2, 22,  "CNSS112233", 1000.00 ,  0, 0,4);
        this.input3 = new PayrollInput(UUID.randomUUID(), "Yassine Mansouri", "C778899", 18000.00, 3, 26, "CNSS112233", 3000.00, 12.0, 8.0, 4.0
        );
    }
    @AfterEach
    public void tearDown(){
        this.payrollService=null;
        this.input=null;
        this.input2=null;
        this.input3=null;

    }

    @Test
    public void testCalculateForStandarEmploye(){
        var result = this.payrollService.calculate(this.input);
        assertEquals(12000.00 , result.getBaseSalary());
        assertEquals(14407.81, result.getGrossSalary());
        assertEquals(268.8, result.getCnssAmount() , "CNSS 4.8%");
        assertEquals(325.62, result.getAmoAmount(), 0.01, "AMO 2.26%");
        assertEquals(2500, result.getProfessionalExpenses(), "(25%) ProfesionnalExpenses not taxable");
        assertEquals(11313.39, result.getNetTaxableSalary(), 0.01, "SNI  10440.761");
        assertEquals(125.0 , result.getFamilyDeductions() , 0.01);
        assertEquals(2013.22 , result.getIgrBrut() ,0.02, "IGR raw = "+result.getIgrBrut());
        assertEquals(1888.22, result.getIgrNet(), 0.01);
        assertEquals(11925.17, result.getNetToPay(), 0.01, "Net: 4661.70");
    }
    @Test
    public void testIGRCalculation() {

        assertEquals(0.0, payrollService.calculateIGR(2000.0), 0.01);

        assertEquals(16.67, payrollService.calculateIGR(3500.0), 0.01);

        assertEquals(116.67, payrollService.calculateIGR(4500.0), 0.01);

        assertEquals(366.67, payrollService.calculateIGR(6000.0), 0.01);

        assertEquals(600.0, payrollService.calculateIGR(7000.0), 0.01);

        assertEquals(1566.67, payrollService.calculateIGR(10000.0), 0.01);

        assertEquals(5116.67, payrollService.calculateIGR(20000.0), 0.01);
    }
    @Test
    public void testCalculatorForWorkedDyasLessThan26(){
        var result = this.payrollService.calculate(this.input2);

        // Base salary
        assertEquals(8000.00, this.input2.getBaseSalary(), 0.01);

        // Total raw
        assertEquals(7779.23, result.getGrossSalary(), 0.01);

        // CNSS (plafond 6000 MAD, 4.48%)
        assertEquals(268.80, result.getCnssAmount(), 0.01, "CNSS 4.48%");

        // AMO (2.26%)
        assertEquals(175.81, result.getAmoAmount(), 0.01, "AMO 2.26%");

        // (25% of raw)
        assertEquals(1944.81, result.getProfessionalExpenses(), 0.01, "(25%) ProfesionnalExpenses not taxable");

        // (SNI = totalBrut - CNSS - AMO - FP)
        assertEquals(5389.81, result.getNetTaxableSalary(), 0.05, "SNI correct");

        assertEquals(83.34, result.getFamilyDeductions(), 0.03);

        assertEquals(244.63, result.getIgrBrut(), 0.03, "raw IGR correct");

        // IGR raw after deduction
        assertEquals(161.3, result.getIgrNet(), 0.05);

        // NetToPay= totalBrut - (CNSS + AMO + IGR net)
        assertEquals(7173.32, result.getNetToPay(), 0.01, "Total to pay correct");
    }
    @Test
    public void testCalculateForSeniorSeniorProfile() {

        var result = this.payrollService.calculate(this.input3);
        assertEquals(24286.85, result.getGrossSalary(), 0.5, "RAW total (Base + Primes + HS)");

        assertEquals(268.8, result.getCnssAmount(), 0.01, "Plafond");
        // AMO: 24286.85 * 2.26% = 548.88
        assertEquals(548.88, result.getAmoAmount(), 0.1, "AMO en raw");
        assertEquals(2500.00, result.getProfessionalExpenses(), "Plafon Pro 2500 MAD");

        // SNI (Net Imposable): 24286.85 - 290.30 - 548.88 - 2500 = 20947.67
        assertEquals(20969.22, result.getNetTaxableSalary(), 1.0, "Taxable Salary  (SNI)");

        assertEquals(5350.28, result.getIgrNet(), 2.0, "IGR after familly deductions");

        // --- 4. Raw final---
        assertEquals(18118.94, result.getNetToPay(), 5.0, "Salary to pay to profile 3");
    }
}
