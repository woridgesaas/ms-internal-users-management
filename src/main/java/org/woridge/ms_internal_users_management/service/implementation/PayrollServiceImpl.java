package org.woridge.ms_internal_users_management
        .service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto
        .payroll.PayrollDetailResponseDTO;
import org.woridge.ms_internal_users_management.dto
        .payroll.PayrollSummaryDTO;
import org.woridge.ms_internal_users_management
        .enumeration.ExportFormat;
import org.woridge.ms_internal_users_management
        .enumeration.PayrollStatus;
import org.woridge.ms_internal_users_management.mapper
        .PayrollMapper;
import org.woridge.ms_internal_users_management.model
        .Payroll;
import org.woridge.ms_internal_users_management
        .messaging.publisher.NotificationEventPublisher;
import org.woridge.ms_internal_users_management
        .repository.PayrollRepository;
import org.woridge.ms_internal_users_management.service
        .PayrollExportService;
import org.woridge.ms_internal_users_management.service
        .PayrollGenerationService;
import org.woridge.ms_internal_users_management.service
        .PayrollService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PayrollServiceImpl
        implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final PayrollExportService
            payrollExportService;
    private final PayrollGenerationService
            payrollGenerationService;
    private final PayrollMapper payrollMapper;
    private final NotificationEventPublisher
            notificationEventPublisher; // ← ajouté

    @Override
    public List<PayrollSummaryDTO> getMyPayrolls(
            UUID userId) {
        return payrollRepository
                .findByUserIdOrderByPeriodDateDesc(userId)
                .stream()
                .map(payrollMapper::toSummaryDTO)
                .toList();
    }

    @Override
    public List<PayrollSummaryDTO> getAllPayrollsByMonth(
            int month, int year) {
        LocalDate periodDate =
                LocalDate.of(year, month, 1);
        return payrollRepository
                .findByPeriodDate(periodDate)
                .stream()
                .map(payrollMapper::toSummaryDTO)
                .toList();
    }

    @Override
    public PayrollDetailResponseDTO getPayrollDetail(
            UUID payrollId) {
        Payroll payroll = payrollRepository
                .findById(payrollId)
                .orElseThrow(() ->
                        new RuntimeException("Fiche introuvable")
                );
        return payrollMapper.toDetailDTO(payroll);
    }

    @Override
    public byte[] downloadPayroll(
            UUID payrollId,
            UUID userId,
            ExportFormat format) throws Exception {

        Payroll payroll = payrollRepository
                .findById(payrollId)
                .orElseThrow(() ->
                        new RuntimeException("Fiche introuvable")
                );

        if (!payroll.getUser().getId().equals(userId)) {
            throw new RuntimeException("Accès refusé");
        }

        if (!PayrollStatus.VALIDEE.equals(
                payroll.getStatus())) {
            throw new RuntimeException(
                    "Fiche non validée"
            );
        }

        return payrollExportService.exportPayroll(
                userId,
                payroll.getPeriodDate().getMonthValue(),
                payroll.getPeriodDate().getYear(),
                format
        );
    }

    @Override
    public void validatePayroll(
            UUID payrollId,
            String tenant) {

        Payroll payroll = payrollRepository
                .findById(payrollId)
                .orElseThrow(() ->
                        new RuntimeException("Fiche introuvable")
                );

        payroll.setStatus(PayrollStatus.VALIDEE);
        payrollRepository.save(payroll);

        // ── Publier event salaire pour ms-chatbot ──
        // Quand RH valide → snapshot_employe mis à jour
        String mois = payroll.getPeriodDate()
                .toString().substring(0, 7);

        notificationEventPublisher
                .publishSalaireChatbot(
                        payroll.getUser().getId().toString(),
                        BigDecimal.valueOf(
                                payroll.getNetSalary()
                        ),
                        mois,
                        tenant
                );
    }

    @Override
    public void rejectPayroll(UUID payrollId) {
        Payroll payroll = payrollRepository
                .findById(payrollId)
                .orElseThrow(() ->
                        new RuntimeException("Fiche introuvable")
                );
        payroll.setStatus(PayrollStatus.REJETEE);
        payrollRepository.save(payroll);
    }

    @Override
    public void generatePayrolls(int month, int year) {
        payrollGenerationService
                .generateMonthlyPayrolls(month, year);
    }
}