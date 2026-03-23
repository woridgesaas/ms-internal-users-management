package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.enumeration.ExportFormat;

import java.util.UUID;

public interface PayrollExportService {
    byte[] exportPayroll(UUID userId, int month, int year, ExportFormat format) throws Exception;
}
