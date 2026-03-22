package org.woridge.ms_internal_users_management.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.service.PayrollGenerationService;

import java.time.LocalDate;

import static reactor.netty.http.HttpConnectionLiveness.log;
@Component
@Slf4j
@AllArgsConstructor
public class PayrollScheduler {

    @Autowired
    private PayrollGenerationService payrollGenerationService;
    // on 00:00:00,  day 26 each month
    @Scheduled(cron = "0 0 0 26 * ?")
    public void scheduleMonthlyPayrollGeneration() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();

        log.info("starting generation of payroll {}-{}", month, year);

        try {
            payrollGenerationService.generateMonthlyPayrolls(month, year);
            log.info("Succes for all employes.");
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
        }
    }
}
