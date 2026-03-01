package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woridge.ms_internal_users_management.model.Resignation;
import org.woridge.ms_internal_users_management.model.User;

import java.util.Optional;
import java.util.UUID;

public interface ResignationRepository extends JpaRepository<Resignation, UUID> {

    Optional<Resignation> findByUser(User user);

    boolean existsByUser(User user);

}