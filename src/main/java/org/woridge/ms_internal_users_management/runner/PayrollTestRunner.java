package org.woridge.ms_internal_users_management.runner;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.woridge.ms_internal_users_management.repository.PayrollRepository;
import org.woridge.ms_internal_users_management.service.PayrollGenerationService;

//@Component
@Transactional
public class PayrollTestRunner implements CommandLineRunner {
    @Autowired
    private PayrollGenerationService payrollGenerationService;
    @Autowired
    private PayrollRepository payrollRepository;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Starting runner test ===");
        payrollGenerationService.generateMonthlyPayrolls(3, 2026);


        long count = payrollRepository.count();
        System.out.println("Number of documents generated: " + count);

        payrollRepository.findAll().forEach(p -> {
            System.out.println("--------------------------------------------------");
            System.out.println("Employé ID : " + p.getUser().getId());
            System.out.println("Période : " + p.getPeriodDate());
            System.out.println("Salaire Brut : " + p.getGrossSalary() + " DH");
            System.out.println("NET À PAYER : " + p.getNetSalary() + " DH");
            System.out.println("Nombre de lignes de détails : " + p.getDetails().size());
        });

        System.out.println("=== FIN ===");
    }
    }

