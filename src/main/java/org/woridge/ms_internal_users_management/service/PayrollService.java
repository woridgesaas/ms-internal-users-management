package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.payroll.PayrollDetailResponseDTO;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollSummaryDTO;
import org.woridge.ms_internal_users_management.enumeration.ExportFormat;

import java.util.List;
import java.util.UUID;

public interface PayrollService {

    // Employé — liste ses propres fiches
    List<PayrollSummaryDTO> getMyPayrolls(UUID userId);

    // Employé — télécharge une fiche en PDF
    byte[] downloadPayroll(UUID payrollId, UUID userId, ExportFormat format) throws Exception;

    // RH — liste toutes les fiches d'un mois
    List<PayrollSummaryDTO> getAllPayrollsByMonth(int month, int year);

    // RH — détail complet d'une fiche
    PayrollDetailResponseDTO getPayrollDetail(UUID payrollId);

    // RH — valider une fiche
    void validatePayroll(UUID payrollId);

    // RH — rejeter une fiche
    void rejectPayroll(UUID payrollId);

    // RH — générer manuellement
    void generatePayrolls(int month, int year);
}