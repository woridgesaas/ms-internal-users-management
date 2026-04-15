package org.woridge.ms_internal_users_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.enumeration.SectionType;
import org.woridge.ms_internal_users_management.model.ProfileUpdateRequest;

import java.util.UUID;

@Repository
public interface ProfileUpdateRequestRepository extends JpaRepository<ProfileUpdateRequest, UUID> {

    boolean existsByUserIdAndSectionTypeAndStatus(
            UUID userId,
            SectionType sectionType,
            RequestStatus status
    );

    boolean existsByUserIdAndSectionTypeAndTargetEntityIdAndStatus(
            UUID userId,
            SectionType sectionType,
            UUID targetEntityId,
            RequestStatus status
    );
}
