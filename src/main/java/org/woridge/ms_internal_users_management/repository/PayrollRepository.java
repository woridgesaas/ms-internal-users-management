package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.enumeration.PayrollStatus;
import org.woridge.ms_internal_users_management.model.Payroll;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayrollRepository extends CrudRepository<Payroll , UUID> {
    Optional<Payroll> findByUserIdAndPeriodDate(UUID userId, LocalDate periodDate);
    // liste fiches d'un employé (du plus récent au plus ancien)
    List<Payroll> findByUserIdOrderByPeriodDateDesc(UUID userId);

    //  toutes les fiches d'un mois donné (pour le RH)
    List<Payroll> findByPeriodDate(LocalDate periodDate);

    //  fiches par statut (RH voit les EN_ATTENTE)
    List<Payroll> findByStatus(PayrollStatus status);

    //  fiches d'un employé par statut
    List<Payroll> findByUserIdAndStatus(UUID userId, PayrollStatus status);
}
