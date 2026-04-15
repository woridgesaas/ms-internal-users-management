package org.woridge.ms_internal_users_management.service;

import jakarta.servlet.http.HttpServletRequest;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.dto.profile_update_request.ProfileUpdateDetailsRequest;
import org.woridge.ms_internal_users_management.dto.profile_update_request.ProfileUpdateSummaryRequest;
import org.woridge.ms_internal_users_management.dto.user.UpdateUserPersonalInformationRequest;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;

import java.util.List;
import java.util.UUID;

public interface ProfileUpdateRequestService {

    void submitPersonalInformationUpdateRequest(UUID userId, UpdateUserPersonalInformationRequest request);

    void submitBankInformationUpdateRequest(UUID userId, UUID bankInformationId, UpdateBankInformationRequest request);

    void submitEducationUpdateRequest(UUID userId, UUID educationId, UpdateEducationRequest request);

    void submitProfessionalExperienceUpdateRequest(UUID userId, UUID professionalExperienceId, UpdateProfessionalExperienceRequest request);

    void processProfileUpdateRequest(UUID requestId, RequestStatus status, HttpServletRequest request);

    List<ProfileUpdateSummaryRequest> getAllProfileUpdateRequests();

    ProfileUpdateDetailsRequest getProfileUpdateRequestDetails(UUID requestId);

    UUID getCurrentUserId(HttpServletRequest request);
}