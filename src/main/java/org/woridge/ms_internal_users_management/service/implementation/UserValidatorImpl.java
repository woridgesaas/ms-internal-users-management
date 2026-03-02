package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.exception.user.*;
import org.woridge.ms_internal_users_management.model.User;
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

    @Override
    public void validateUserProfile(User user) {
        if (user.getEducations() == null || user.getEducations().isEmpty()) {
            throw new EducationNotFoundException("No educations found for user");
        }

        if (user.getProfessionalExperiences() == null || user.getProfessionalExperiences().isEmpty()) {
            throw new ProfessionalExperienceNotFoundException("No professional experiences found for user: ");
        }

        if (user.getBankInformation() == null) {
            throw new BankInformationMissingException("Bank information is missing for user: ");
        }
    }
}

