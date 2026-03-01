package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.woridge.ms_internal_users_management.enumeration.Status;
import org.woridge.ms_internal_users_management.model.User;

import java.util.Optional;
import java.util.UUID;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByCINIgnoreCase(String cin);
    long countByDepartmentId(UUID departmentId);
    @EntityGraph(attributePaths = {
            "department",
            "post",
            "bankInformation",
            "educations",
            "professionalExperiences"
    })
    Optional<User> findWithFullProfileById(UUID id);
    Optional<User> findByCIN(String cin);
    Optional<User> findByIdAndStatus(UUID id, Status status);
}
