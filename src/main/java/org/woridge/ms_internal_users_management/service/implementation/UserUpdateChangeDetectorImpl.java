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
    public List<RequestFieldChange> detect(User user, CreateUserUpdateRequest dto) {

        List<RequestFieldChange> changes = new ArrayList<>();

        addIfChanged(changes, "firstName", user.getFirstName(), dto.firstName());
        addIfChanged(changes, "lastName", user.getLastName(), dto.lastName());
        addIfChanged(changes, "email", user.getEmail(), dto.email());
        addIfChanged(changes, "phone", user.getPhoneNumber(), dto.phone());
        addIfChanged(changes, "address", user.getAddress(), dto.address());
        addIfChanged(changes, "cin", user.getCIN(), dto.cin());
        addIfChanged(changes, "profileImage", user.getPicturePath(), dto.profileImageUrl());
        addIfChanged(changes, "birthDate", user.getBirthDate(), dto.birthDate());

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