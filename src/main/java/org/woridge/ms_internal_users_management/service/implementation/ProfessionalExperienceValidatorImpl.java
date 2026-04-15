package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.exception.professional_experience.*;
import org.woridge.ms_internal_users_management.model.ProfessionalExperience;
import org.woridge.ms_internal_users_management.repository.ProfessionalExperienceRepository;
import org.woridge.ms_internal_users_management.service.ProfessionalExperienceValidator;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfessionalExperienceValidatorImpl implements ProfessionalExperienceValidator {

    private final ProfessionalExperienceRepository professionalExperienceRepository;

    @Override
    public void validateProfessionalExperienceForCreation(UUID userId, AddProfessionalExperienceRequest addProfessionalExperienceRequest) {

        validateCommonRules(addProfessionalExperienceRequest.startDate(), addProfessionalExperienceRequest.endDate(), addProfessionalExperienceRequest.isCurrent());

        boolean exists = professionalExperienceRepository.existsByUserIdAndCompanyNameAndJobTitleAndStartDate(
                userId,
                addProfessionalExperienceRequest.companyName(),
                addProfessionalExperienceRequest.jobTitle(),
                addProfessionalExperienceRequest.startDate()
        );

        if (exists) {

            throw new ProfessionalExperienceDuplicatedException("Professional experience already exists for user with id : " + userId);
        }
    }

    @Override
    public void validateProfessionalExperienceForUpdate(UUID userId, ProfessionalExperience currentProfessionalExperience, UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest) {

        if (!currentProfessionalExperience.getUser().getId().equals(userId)) {

            throw new ProfessionalExperienceDoesNotBelongToUserException("This professional experience does not belong to the user with id : " + userId);
        }

        validateCommonRules(updateProfessionalExperienceRequest.startDate(), updateProfessionalExperienceRequest.endDate(), updateProfessionalExperienceRequest.isCurrent());

        boolean exists = professionalExperienceRepository
                .existsByUserIdAndCompanyNameAndJobTitleAndStartDateAndIdNot(
                        userId,
                        updateProfessionalExperienceRequest.companyName(),
                        updateProfessionalExperienceRequest.jobTitle(),
                        updateProfessionalExperienceRequest.startDate(),
                        currentProfessionalExperience.getId()
                );

        if (exists) {

            throw new ProfessionalExperienceDuplicatedException("Professional experience already exists for user with id : " + userId);
        }
    }

    // Helper method
    private void validateCommonRules(LocalDate startDate, LocalDate endDate, Boolean isCurrent) {

        boolean current = Boolean.TRUE.equals(isCurrent);

        if (endDate != null && !endDate.isAfter(startDate)) {

            throw new InvalidProfessionalExperienceEndDateException("End date must be after start date");
        }

        if (!current && endDate == null) {

            throw new ProfessionalExperienceEndDateMissingException("End date is required for professional experience that is not current");
        }

        if (current && endDate != null) {

            throw new CurrentProfessionalExperienceWithEndDateException("Professional experience that are current must not have an end date");
        }
    }
}
