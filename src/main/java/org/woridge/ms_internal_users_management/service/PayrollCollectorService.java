package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.payroll.PayrollInput;

import java.util.List;

public interface PayrollCollectorService {
    List<PayrollInput> getPayrollInputs();
}
