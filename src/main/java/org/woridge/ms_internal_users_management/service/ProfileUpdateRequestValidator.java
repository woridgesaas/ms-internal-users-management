package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.enumeration.SectionType;
import org.woridge.ms_internal_users_management.model.ProfileUpdateRequest;

import java.util.UUID;

public interface ProfileUpdateRequestValidator {

    void checkNoPendingProfileUpdateRequestExists(UUID userId, SectionType sectionType, UUID targetEntityId);

    void checkProfileUpdateRequestIsPending(ProfileUpdateRequest profileUpdateRequest);
}
