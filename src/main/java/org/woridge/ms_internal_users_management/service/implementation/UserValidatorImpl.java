package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.exception.CINDuplicatedException;
import org.woridge.ms_internal_users_management.exception.EmailDuplicatedException;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.UserValidator;

@Service
@AllArgsConstructor
public class UserValidatorImpl implements UserValidator {

    private final UserRepository userRepository;
    @Override
    public void checkUniqueEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailDuplicatedException("Email déjà utilisé");
        }
    }
    @Override
    public void checkUniqueCIN(String cin) {
        if (userRepository.existsByCINIgnoreCase(cin)) {
            throw new CINDuplicatedException("CIN déjà utilisé");
        }
    }
}

