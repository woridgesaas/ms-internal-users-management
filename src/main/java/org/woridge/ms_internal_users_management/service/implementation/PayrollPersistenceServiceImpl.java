package org.woridge.ms_internal_users_management.service.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollCalculationResult;
import org.woridge.ms_internal_users_management.dto.payroll.PayrollInput;
import org.woridge.ms_internal_users_management.factory.PayrollFactory;
import org.woridge.ms_internal_users_management.model.Payroll;
import org.woridge.ms_internal_users_management.model.PayrollDetail;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.PayrollRepository;
import org.woridge.ms_internal_users_management.service.PayrolCalculatorService;
import org.woridge.ms_internal_users_management.service.PayrollCollectorService;
import org.woridge.ms_internal_users_management.service.PayrollPersistenceService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
@Transactional
public class PayrollPersistenceServiceImpl implements PayrollPersistenceService {

    @Autowired
    private PayrollRepository payrollRepository;
    @Autowired
    private PayrolCalculatorService payrolCalculatorService;
    @Autowired
    private PayrollCollectorService payrollCollectorService;
    @Autowired
    private PayrollFactory payrollFactory;

    @Override
    public void generateMonthlyPayrolls(int month, int year) {
        List<PayrollInput> inputs = this.payrollCollectorService.getPayrollInputs();
        LocalDate periodDate = LocalDate.of(year, month, 1);

        for (PayrollInput input : inputs) {
            PayrollCalculationResult result = payrolCalculatorService.calculate(input);
            Payroll payroll = payrollFactory.createPayroll(input,result,periodDate);
            //  save  details also
            payrollRepository.save(payroll);
        }
    }

}
