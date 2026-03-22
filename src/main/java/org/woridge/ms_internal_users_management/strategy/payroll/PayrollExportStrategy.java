package org.woridge.ms_internal_users_management.strategy.payroll;

import org.woridge.ms_internal_users_management.enumeration.ExportFormat;
import org.woridge.ms_internal_users_management.model.Payroll;

public interface PayrollExportStrategy {
    byte[] export(Payroll payroll) throws Exception;
    ExportFormat getFormat(); //To identify strategy
}
