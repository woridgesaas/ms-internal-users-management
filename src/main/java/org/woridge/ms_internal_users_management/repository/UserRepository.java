package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.woridge.ms_internal_users_management.enumeration.Status;
import org.woridge.ms_internal_users_management.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,UUID> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByCINIgnoreCase(String cin);
    long countByDepartmentId(UUID departmentId);

    @Query("""
       SELECT u.department.id, COUNT(u)
        FROM User u
        WHERE u.department.id IN :departmentIds
        GROUP BY u.department.id""")
    List<Object[]> countEmployeesByDepartments(List<UUID> departmentIds);
    Optional<User> findWithFullProfileById(UUID id);
    Optional<User> findByCIN(String cin);
    Optional<User> findByIdAndStatus(UUID id, Status status);
    @Query("""
        SELECT u
        FROM User u
        LEFT JOIN FETCH u.department
        LEFT JOIN FETCH u.post
        WHERE u.CIN = :cin
    """)
    Optional<User> findByCINWithDepartmentAndPost(String cin);

}
