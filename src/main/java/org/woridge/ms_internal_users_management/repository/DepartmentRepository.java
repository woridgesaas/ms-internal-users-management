package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.model.Department;
import org.woridge.ms_internal_users_management.enumeration.StatusDepartment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    boolean existsByNameIgnoreCase(String name);

    List<Department> findByStatus(StatusDepartment status);

    Optional<Department> findByNameIgnoreCase(String name);
    @Query("""
       SELECT d, COUNT(u)
       FROM Department d
       LEFT JOIN User u ON u.department.id = d.id
       GROUP BY d
       """)
    List<Object[]> findAllWithEmployeeCount();
}