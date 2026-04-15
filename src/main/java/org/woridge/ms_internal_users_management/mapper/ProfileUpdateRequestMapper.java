package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.profile_update_request.FieldChangeSummary;
import org.woridge.ms_internal_users_management.dto.profile_update_request.ProfileUpdateDetailsRequest;
import org.woridge.ms_internal_users_management.dto.profile_update_request.ProfileUpdateSummaryRequest;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.enumeration.SectionType;
import org.woridge.ms_internal_users_management.model.ProfileUpdateRequest;
import org.woridge.ms_internal_users_management.model.RequestFieldChange;
import org.woridge.ms_internal_users_management.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProfileUpdateRequestMapper {

    public static ProfileUpdateRequest toProfileUpdateRequest(UUID userId, SectionType sectionType, UUID targetEntityId, List<RequestFieldChange> fieldChanges) {

        ProfileUpdateRequest profileUpdateRequest = ProfileUpdateRequest
                .builder()
                .userId(userId)
                .sectionType(sectionType)
                .targetEntityId(targetEntityId)
                .fieldChanges(fieldChanges)
                .build();

        // Link each change back to this request
        fieldChanges.forEach(requestFieldChange ->
                requestFieldChange.setProfileUpdateRequest(profileUpdateRequest));

        return profileUpdateRequest;
    }

    public static ProfileUpdateSummaryRequest toProfileUpdateSummaryRequest(ProfileUpdateRequest profileUpdateRequest, User user) {

        return ProfileUpdateSummaryRequest
                .builder()
                .requestId(profileUpdateRequest.getId())
                .userId(profileUpdateRequest.getUserId())
                .employeeFullName(user.getFirstName() + " " + user.getLastName())
                .sectionType(profileUpdateRequest.getSectionType())
                .targetEntityId(profileUpdateRequest.getTargetEntityId())
                .numberOfChanges(profileUpdateRequest.getFieldChanges().size())
                .status(profileUpdateRequest.getStatus())
                .requestedAt(profileUpdateRequest.getRequestedAt())
                .build();
    }

    public static ProfileUpdateDetailsRequest toProfileUpdateDetailsRequest(ProfileUpdateRequest profileUpdateRequest, User user) {

        List<FieldChangeSummary> fieldChanges = profileUpdateRequest
                .getFieldChanges()
                .stream()
                .map(RequestFieldChangeMapper::toFieldChangeSummary)
                .toList();

        return ProfileUpdateDetailsRequest
                .builder()
                .requestId(profileUpdateRequest.getId())
                .userId(profileUpdateRequest.getUserId())
                .employeeFullName(user.getFirstName() + " " + user.getLastName())
                .sectionType(profileUpdateRequest.getSectionType())
                .targetEntityId(profileUpdateRequest.getTargetEntityId())
                .status(profileUpdateRequest.getStatus())
                .requestedAt(profileUpdateRequest.getRequestedAt())
                .processedAt(profileUpdateRequest.getProcessedAt())
                .processedBy(profileUpdateRequest.getProcessedBy())
                .fieldChanges(fieldChanges)
                .build();
    }

    public static void applyProcessingMetadata(ProfileUpdateRequest currentProfileUpdateRequest, RequestStatus requestStatus, UUID hrId) {

        currentProfileUpdateRequest.setStatus(requestStatus);
        currentProfileUpdateRequest.setProcessedBy(hrId);
        currentProfileUpdateRequest.setProcessedAt(LocalDateTime.now());
    }
}
