package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.enumeration.ExportFormat;
import org.woridge.ms_internal_users_management.model.Payroll;
import org.woridge.ms_internal_users_management.repository.PayrollRepository;
import org.woridge.ms_internal_users_management.service.PayrollExportService;

import org.woridge.ms_internal_users_management.strategy.PayrollExportStrategy;
import org.woridge.ms_internal_users_management.strategy.PayrollExportStrategyFactory;

import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Service
public class PayrollExportServiceImpl implements PayrollExportService {
    @Autowired
    private PayrollExportStrategyFactory payrollFactory;
    @Autowired
    private PayrollRepository payrollRepository;

    @Override
    public byte[] exportPayroll(UUID userId, int month, int year, ExportFormat format) throws Exception {
        // 1. Get the payroll from db
        LocalDate periodDate = LocalDate.of(year, month, 1);
        Payroll payroll = payrollRepository.findByUserIdAndPeriodDate(userId, periodDate)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        // 2. Get the strategy from teh factory by format
        PayrollExportStrategy strategy = this.payrollFactory.getStrategy(format);

        // 3. Execute
        return strategy.export(payroll);
    }
}
