package org.woridge.ms_internal_users_management.strategy.payroll;

import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.enumeration.ExportFormat;
import org.woridge.ms_internal_users_management.repository.PayrollRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayrollExportStrategyFactory {

   private Map<ExportFormat , PayrollExportStrategy> strategies;
   private PayrollRepository payrollRepository;
   public PayrollExportStrategyFactory(List<PayrollExportStrategy> strategyList){
       //range each concrait strategy with the format (identifier)
       this.strategies = strategyList.stream()
               .collect(Collectors.toMap(PayrollExportStrategy::getFormat, s -> s));
   }
    public PayrollExportStrategy getStrategy(ExportFormat format) throws Exception {


        // --- 1. Search in Map for the corespondante strategy
        PayrollExportStrategy strategy = strategies.get(format);

        if (strategy == null) {
            throw new IllegalArgumentException("Invalid strategy");
        }

        // --- 2. Return the exact strategy ---
        return strategy;
    }
}
