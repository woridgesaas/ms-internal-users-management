package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woridge.ms_internal_users_management.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByCINIgnoreCase(String cin);
}
