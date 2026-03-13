package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.model.UserUpdateRequest;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserUpdateRequestRepository extends JpaRepository<UserUpdateRequest, UUID> {

    boolean existsByUserIdAndStatus(UUID userId, RequestStatus status);
}
