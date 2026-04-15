package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.model.ProfileUpdateRequest;

public interface ProfileUpdateApplierService {

    void applyChanges(ProfileUpdateRequest profileUpdateRequest);
}
