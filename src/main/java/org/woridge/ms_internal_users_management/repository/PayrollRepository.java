package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.Payroll;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayrollRepository extends CrudRepository<Payroll , UUID> {
    Optional<Payroll> findByUserIdAndPeriodDate(UUID userId, LocalDate periodDate);
}
