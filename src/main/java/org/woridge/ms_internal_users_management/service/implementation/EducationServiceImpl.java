package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.education.AddEducationRequest;
import org.woridge.ms_internal_users_management.dto.education.AddEducationResponse;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.exception.education.EducationIdNotMatchedException;
import org.woridge.ms_internal_users_management.exception.education.EducationNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.mapper.EducationMapper;
import org.woridge.ms_internal_users_management.model.Education;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.EducationRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.EducationService;
import org.woridge.ms_internal_users_management.service.EducationValidator;
import org.woridge.ms_internal_users_management.service.UserValidator;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final EducationValidator educationValidator;
    private final UserValidator userValidator;

    @Override
    public AddEducationResponse addEducation(UUID userId, AddEducationRequest addEducationRequest) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id : " + userId + " not found"));

        educationValidator.validateEducationForCreation(userId, addEducationRequest);

        Education education = EducationMapper.toEducation(user, addEducationRequest);

        return EducationMapper.toAddEducationResponse(educationRepository.save(education));
    }

    @Override
    public void updateEducation(UUID userId, UUID educationId, UpdateEducationRequest updateEducationRequest) {

        if (!educationId.equals(updateEducationRequest.id())) {

            throw new EducationIdNotMatchedException("Education ids aren't matched");
        }

        userValidator.validateUser(userId);

        Education currentEducation = getCurrentEducation(educationId);

        educationValidator.validateEducationForUpdate(userId, currentEducation, updateEducationRequest);

        EducationMapper.updateEducation(currentEducation, updateEducationRequest);

        educationRepository.save(currentEducation);
    }

    @Override
    public void deleteEducation(UUID educationId) {

        Education education = getCurrentEducation(educationId);

        educationRepository.delete(education);
    }

    // Helper method
    private Education getCurrentEducation(UUID educationId) {

        return educationRepository.findById(educationId).orElseThrow(() ->
                new EducationNotFoundException("Education with id : " + educationId + " not found"));
    }
}

