package org.woridge.ms_internal_users_management.service.implementation;

import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.user.UpdateUserPersonalInformationRequest;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.service.ProfileUpdateChangeDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PersonalInformationUpdateChangeDetectorImpl implements ProfileUpdateChangeDetector<User, UpdateUserPersonalInformationRequest> {

    @Override
    public List<RequestFieldChange> detectChanges(User currentUser, UpdateUserPersonalInformationRequest updateUserPersonalInformationRequest) {

        List<RequestFieldChange> changes = new ArrayList<>();

        addIfChanged(changes, "firstName", currentUser.getFirstName(), updateUserPersonalInformationRequest.firstName());
        addIfChanged(changes, "lastName", currentUser.getLastName(), updateUserPersonalInformationRequest.lastName());
        addIfChanged(changes, "cin", currentUser.getCIN(), updateUserPersonalInformationRequest.cin());
        addIfChanged(changes, "email", currentUser.getEmail(), updateUserPersonalInformationRequest.email());
        addIfChanged(changes, "phone", currentUser.getPhoneNumber(), updateUserPersonalInformationRequest.phone());
        addIfChanged(changes, "birthDate", currentUser.getBirthDate(), updateUserPersonalInformationRequest.birthDate());
        addIfChanged(changes, "address", currentUser.getAddress(), updateUserPersonalInformationRequest.address());
        addIfChanged(changes, "profileImage", currentUser.getPicturePath(), updateUserPersonalInformationRequest.profileImageUrl());

        return changes;
    }

    // Helper method
    private void addIfChanged(List<RequestFieldChange> changes, String field, Object oldValue, Object newValue) {

        if (!Objects.equals(oldValue, newValue)) {

            changes.add(RequestFieldChange.builder()
                    .fieldName(field)
                    .oldValue(String.valueOf(oldValue))
                    .newValue(String.valueOf(newValue))
                    .build());
        }
    }
}