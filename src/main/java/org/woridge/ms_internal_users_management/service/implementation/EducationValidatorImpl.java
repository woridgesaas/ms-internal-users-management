package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.education.AddEducationRequest;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.enumeration.EducationType;
import org.woridge.ms_internal_users_management.exception.education.EducationDoesNotBelongToUserException;
import org.woridge.ms_internal_users_management.exception.education.EducationDuplicatedException;
import org.woridge.ms_internal_users_management.exception.education.EducationEndDateMissingException;
import org.woridge.ms_internal_users_management.exception.education.InvalidEducationEndDateException;
import org.woridge.ms_internal_users_management.model.Education;
import org.woridge.ms_internal_users_management.repository.EducationRepository;
import org.woridge.ms_internal_users_management.service.EducationValidator;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EducationValidatorImpl implements EducationValidator {

    private final EducationRepository educationRepository;

    @Override
    public void validateEducationForCreation(UUID userId, AddEducationRequest addEducationRequest) {

        validateCommonRules(addEducationRequest.type(), addEducationRequest.startDate(), addEducationRequest.endDate());

        boolean exists = educationRepository.existsByUserIdAndTypeAndInstitutionAndTitleAndStartDate(
                userId,
                addEducationRequest.type(),
                addEducationRequest.institution(),
                addEducationRequest.title(),
                addEducationRequest.startDate()
        );

        if (exists) {

            throw new EducationDuplicatedException("Education already exists for this user with id : " + userId);
        }
    }

    @Override
    public void validateEducationForUpdate(UUID userId, Education currentEducation, UpdateEducationRequest updateEducationRequest) {

        if (!currentEducation.getUser().getId().equals(userId)) {

            throw new EducationDoesNotBelongToUserException("This education does not belong to the user with id : " + userId);
        }

        validateCommonRules(updateEducationRequest.type(), updateEducationRequest.startDate(), updateEducationRequest.endDate());

        boolean exists = educationRepository.existsByUserIdAndTypeAndInstitutionAndTitleAndStartDateAndIdNot(
                        userId,
                        updateEducationRequest.type(),
                        updateEducationRequest.institution(),
                        updateEducationRequest.title(),
                        updateEducationRequest.startDate(),
                        currentEducation.getId()
                );

        if (exists) {
            throw new EducationDuplicatedException("Education already exists for user with id : " + userId);
        }
    }

    // Helper method
    private void validateCommonRules(EducationType educationType, LocalDate startDate, LocalDate endDate) {

        if (endDate != null && !endDate.isAfter(startDate)) {

            throw new InvalidEducationEndDateException("End date must be after start date");
        }

        if (educationType == EducationType.EDUCATION && endDate == null) {

            throw new EducationEndDateMissingException("End date is required for education");
        }
    }
}
