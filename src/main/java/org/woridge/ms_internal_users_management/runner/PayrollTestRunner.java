package org.woridge.ms_internal_users_management.runner;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.repository.PayrollRepository;
import org.woridge.ms_internal_users_management.service.PayrollPersistenceService;

//@Component
@Transactional
public class PayrollTestRunner implements CommandLineRunner {
    @Autowired
    private PayrollPersistenceService payrollPersistenceService;
    @Autowired
    private PayrollRepository payrollRepository;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== DÉMARRAGE DE LA GÉNÉRATION DE LA PAIE TEST ===");
        payrollPersistenceService.generateMonthlyPayrolls(3, 2026);


        long count = payrollRepository.count();
        System.out.println("Nombre de fiches de paie générées : " + count);

        payrollRepository.findAll().forEach(p -> {
            System.out.println("--------------------------------------------------");
            System.out.println("Employé ID : " + p.getUser().getId());
            System.out.println("Période : " + p.getPeriodDate());
            System.out.println("Salaire Brut : " + p.getGrossSalary() + " DH");
            System.out.println("NET À PAYER : " + p.getNetSalary() + " DH");
            System.out.println("Nombre de lignes de détails : " + p.getDetails().size());
        });

        System.out.println("=== FIN DU TEST DE PERSISTANCE ===");
    }
    }

