package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.enumeration.SectionType;
import org.woridge.ms_internal_users_management.exception.profile_update_request.PendingProfileUpdateRequestAlreadyExistsException;
import org.woridge.ms_internal_users_management.exception.profile_update_request.ProfileUpdateRequestNotPendingException;
import org.woridge.ms_internal_users_management.model.ProfileUpdateRequest;
import org.woridge.ms_internal_users_management.repository.ProfileUpdateRequestRepository;
import org.woridge.ms_internal_users_management.service.ProfileUpdateRequestValidator;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileUpdateRequestValidatorImpl implements ProfileUpdateRequestValidator {

    private final ProfileUpdateRequestRepository profileUpdateRequestRepository;

    @Override
    public void checkNoPendingProfileUpdateRequestExists(UUID userId, SectionType sectionType, UUID targetEntityId) {

        boolean exists = sectionType == SectionType.PERSONAL_INFORMATION
                ? profileUpdateRequestRepository.existsByUserIdAndSectionTypeAndStatus(userId, sectionType, RequestStatus.PENDING)
                : profileUpdateRequestRepository.existsByUserIdAndSectionTypeAndTargetEntityIdAndStatus(userId, sectionType, targetEntityId, RequestStatus.PENDING);

        if (exists) {

            throw new PendingProfileUpdateRequestAlreadyExistsException("A pending request already exists for this section");
        }
    }

    @Override
    public void checkProfileUpdateRequestIsPending(ProfileUpdateRequest profileUpdateRequest) {

        if (profileUpdateRequest.getStatus() != RequestStatus.PENDING) {

            throw new ProfileUpdateRequestNotPendingException("This request has already been processed");
        }
    }
}
