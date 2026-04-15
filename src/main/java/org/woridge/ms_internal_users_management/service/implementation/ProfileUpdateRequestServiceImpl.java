package org.woridge.ms_internal_users_management.service.implementation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.dto.profile_update_request.ProfileUpdateDetailsRequest;
import org.woridge.ms_internal_users_management.dto.profile_update_request.ProfileUpdateSummaryRequest;
import org.woridge.ms_internal_users_management.dto.user.UpdateUserPersonalInformationRequest;
import org.woridge.ms_internal_users_management.enumeration.*;
import org.woridge.ms_internal_users_management.exception.bank_information.BankInformationNotFoundException;
import org.woridge.ms_internal_users_management.exception.education.EducationNotFoundException;
import org.woridge.ms_internal_users_management.exception.professional_experience.ProfessionalExperienceNotFoundException;
import org.woridge.ms_internal_users_management.exception.profile_update_request.ProfileUpdateRequestNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.mapper.ProfileUpdateRequestMapper;
import org.woridge.ms_internal_users_management.model.*;
import org.woridge.ms_internal_users_management.repository.*;
import org.woridge.ms_internal_users_management.service.*;
import org.woridge.ms_internal_users_management.utils.RequestUtils;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ProfileUpdateRequestServiceImpl implements ProfileUpdateRequestService {

    private final ProfileUpdateRequestValidator profileUpdateRequestValidator;
    private final ProfileUpdateRequestRepository profileUpdateRequestRepository;
    private final ProfileUpdateApplierService profileUpdateApplierService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PersonalInformationUpdateChangeDetectorImpl personalInformationUpdateChangeDetector;
    private final BankInformationUpdateChangeDetectorImpl bankInformationUpdateChangeDetector;
    private final EducationUpdateChangeDetectorImpl educationUpdateChangeDetector;
    private final ProfessionalExperienceUpdateChangeDetectorImpl professionalExperienceUpdateChangeDetector;
    private final BankInformationRepository bankInformationRepository;
    private final EducationRepository educationRepository;
    private final ProfessionalExperienceRepository professionalExperienceRepository;

    @Override
    public void submitPersonalInformationUpdateRequest(UUID userId, UpdateUserPersonalInformationRequest updateUserPersonalInformationRequest) {

        User currentUser = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id : " + userId + " not found"));

        submitUpdateRequest(userId, SectionType.PERSONAL_INFORMATION, null, currentUser, updateUserPersonalInformationRequest, personalInformationUpdateChangeDetector);
    }

    @Override
    public void submitBankInformationUpdateRequest(UUID userId, UUID bankInformationId, UpdateBankInformationRequest updateBankInformationRequest) {

        BankInformation currentBankInformation = bankInformationRepository.findById(bankInformationId).orElseThrow(() ->
                new BankInformationNotFoundException("Bank information with id : " + bankInformationId + " not found"));

        submitUpdateRequest(userId, SectionType.BANK_INFORMATION, bankInformationId, currentBankInformation, updateBankInformationRequest, bankInformationUpdateChangeDetector);
    }

    @Override
    public void submitEducationUpdateRequest(UUID userId, UUID educationId, UpdateEducationRequest updateEducationRequest) {

        Education currentEducation = educationRepository.findById(educationId).orElseThrow(() ->
                new EducationNotFoundException("Education with id : " + educationId + " not found"));

        submitUpdateRequest(userId, SectionType.EDUCATION, educationId, currentEducation, updateEducationRequest, educationUpdateChangeDetector);
    }

    @Override
    public void submitProfessionalExperienceUpdateRequest(UUID userId, UUID professionalExperienceId, UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest) {

        ProfessionalExperience currentProfessionalExperience = professionalExperienceRepository.findById(professionalExperienceId).orElseThrow(() ->
                new ProfessionalExperienceNotFoundException("Professional experience with id : " + professionalExperienceId + " not found"));

        submitUpdateRequest(userId, SectionType.PROFESSIONAL_EXPERIENCE, professionalExperienceId, currentProfessionalExperience, updateProfessionalExperienceRequest, professionalExperienceUpdateChangeDetector);
    }

    @Override
    public void processProfileUpdateRequest(UUID requestId, RequestStatus requestStatus, HttpServletRequest request) {

        UUID hrId = getCurrentUserId(request);

        ProfileUpdateRequest currentProfileUpdateRequest = getCurrentProfileUpdateRequest(requestId);

        profileUpdateRequestValidator.checkProfileUpdateRequestIsPending(currentProfileUpdateRequest);

        if (requestStatus == RequestStatus.APPROVED) {

            profileUpdateApplierService.applyChanges(currentProfileUpdateRequest);
        }

        ProfileUpdateRequestMapper.applyProcessingMetadata(currentProfileUpdateRequest, requestStatus, hrId);

        profileUpdateRequestRepository.save(currentProfileUpdateRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfileUpdateSummaryRequest> getAllProfileUpdateRequests() {

        return profileUpdateRequestRepository
                .findAll()
                .stream()
                .map(profileUpdateRequest ->
                        ProfileUpdateRequestMapper.toProfileUpdateSummaryRequest(profileUpdateRequest, getCurrentUser(profileUpdateRequest.getUserId())
                        ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileUpdateDetailsRequest getProfileUpdateRequestDetails(UUID requestId) {

        ProfileUpdateRequest profileUpdateRequest = getCurrentProfileUpdateRequest(requestId);

        User user = getCurrentUser(profileUpdateRequest.getUserId());

        return ProfileUpdateRequestMapper.toProfileUpdateDetailsRequest(profileUpdateRequest, user);
    }

    @Override
    public UUID getCurrentUserId(HttpServletRequest request) {

        String CIN = RequestUtils.extractCINFromHeaderRequest(request);

        return userService.findIdByCIN(CIN);
    }

    // Helper method
    private <E, R> void submitUpdateRequest(UUID userId, SectionType sectionType, UUID targetEntityId, E currentEntity, R updateRequest, ProfileUpdateChangeDetector<E, R> profileUpdateChangeDetector) {

        List<RequestFieldChange> requestFieldChanges = profileUpdateChangeDetector.detectChanges(currentEntity, updateRequest);

        profileUpdateRequestValidator.checkNoPendingProfileUpdateRequestExists(userId, sectionType, targetEntityId);

        ProfileUpdateRequest profileUpdateRequest = ProfileUpdateRequestMapper.toProfileUpdateRequest(userId, sectionType, targetEntityId, requestFieldChanges);

        profileUpdateRequestRepository.save(profileUpdateRequest);
    }

    private ProfileUpdateRequest getCurrentProfileUpdateRequest(UUID profileUpdateRequestId) {

        return profileUpdateRequestRepository.findById(profileUpdateRequestId).orElseThrow(() ->
                new ProfileUpdateRequestNotFoundException("Profile update request with id : " + profileUpdateRequestId + " not found"));
    }

    private User getCurrentUser(UUID userId) {

        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id : " + userId + " not found"));
    }
}
