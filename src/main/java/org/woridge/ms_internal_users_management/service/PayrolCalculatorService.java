package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.payroll.PayrollCalculationResult;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollInput;

public interface PayrolCalculatorService {
    public static final double CNSS_RATE = 0.0448; // 4.48%
    public static final double CNSS_CEILING = 6000;
    public static final double AMO_RATE = 0.0226; // 2.26%

    public static final double PROFESSIONAL_EXPENSES_RATE = 0.25; // 25% (Standard)
    public static final double PROFESSIONAL_EXPENSES_CEILING = 2500.0;
    // max by year
    public static final double FAMILY_DEDUCTION_PER_PERSON_ANNUAL = 500.0;
    public static final double FAMILY_DEDUCTION_MAX_ANNUAL = 3000.0; // MAD par an max (6 personnes)

    // Conversion mensuelle
    public static final double FAMILY_DEDUCTION_PER_PERSON_MONTHLY = FAMILY_DEDUCTION_PER_PERSON_ANNUAL / 12.0;
    public static final double FAMILY_DEDUCTION_MAX_MONTHLY = FAMILY_DEDUCTION_MAX_ANNUAL / 12.0;

    public static final double OVERTIME_RATE_DAY =1.25;
    public static final double OVERTIME_RATE_NIGHT= 1.50;
    public static final double OVERTIME_RATE_WEEKEND= 2.00;

    PayrollCalculationResult calculate(PayrollInput input);
    double calculateIGR(double sni);
}
