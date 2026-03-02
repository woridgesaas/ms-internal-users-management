package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woridge.ms_internal_users_management.model.SalaryHistory;
import org.woridge.ms_internal_users_management.model.User;

import java.util.Optional;
import java.util.UUID;

public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, UUID> {

    Optional<SalaryHistory> findTopByUserOrderByValidFromDesc(User user);
}