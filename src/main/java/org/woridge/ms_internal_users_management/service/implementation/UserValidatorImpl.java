package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.exception.user.CinDuplicatedException;
import org.woridge.ms_internal_users_management.exception.user.EmailDuplicatedException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.UserValidator;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserValidatorImpl implements UserValidator {

    private final UserRepository userRepository;

    @Override
    public void validateUser(UUID userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with id : " + userId + " not found");
        }
    }

    @Override
    public void validateUserForCreation(String email, String CIN) {

        if (userRepository.existsByEmailIgnoreCase(email)) {

            throw new EmailDuplicatedException("Email duplicated");
        }

        if (userRepository.existsByCINIgnoreCase(CIN)) {

            throw new CinDuplicatedException("CIN duplicated");
        }
    }

    @Override
    public void validateUserForUpdate(UUID userId, String email, String CIN) {

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(email, userId)) {

            throw new EmailDuplicatedException("Email duplicated");
        }

        if (userRepository.existsByCINIgnoreCaseAndIdNot(CIN, userId)) {

            throw new CinDuplicatedException("CIN duplicated");
        }
    }
}

