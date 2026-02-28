package org.woridge.ms_internal_users_management.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.woridge.ms_internal_users_management.model.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByNameIgnoreCase(String name);
}

