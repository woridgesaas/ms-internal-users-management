package org.woridge.ms_internal_users_management.service.implementation;

import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.user.CreateUserUpdateRequest;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.service.UserUpdateChangeDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserUpdateChangeDetectorImpl implements UserUpdateChangeDetector {

    @Override
    public List<RequestFieldChange> detect(User user, CreateUserUpdateRequest request) {

        List<RequestFieldChange> changes = new ArrayList<>();

        addIfChanged(changes, "firstName", user.getFirstName(), request.firstName());
        addIfChanged(changes, "lastName", user.getLastName(), request.lastName());
        addIfChanged(changes, "email", user.getEmail(), request.email());
        addIfChanged(changes, "phone", user.getPhoneNumber(), request.phone());
        addIfChanged(changes, "address", user.getAddress(), request.address());
        addIfChanged(changes, "cin", user.getCIN(), request.cin());
        addIfChanged(changes, "profileImage", user.getPicturePath(), request.profileImageUrl());
        addIfChanged(changes, "birthDate", user.getBirthDate(), request.birthDate());

        return changes;
    }

    private void addIfChanged(
            List<RequestFieldChange> changes,
            String field,
            Object oldValue,
            Object newValue
    ) {

        if (!Objects.equals(oldValue, newValue)) {
            changes.add(RequestFieldChange.builder()
                    .fieldName(field)
                    .oldValue(String.valueOf(oldValue))
                    .newValue(String.valueOf(newValue))
                    .build());
        }
    }
}