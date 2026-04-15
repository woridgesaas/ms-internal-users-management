package org.woridge.ms_internal_users_management.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.profile_update_request.ProfileUpdateDetailsRequest;
import org.woridge.ms_internal_users_management.dto.profile_update_request.ProfileUpdateSummaryRequest;
import org.woridge.ms_internal_users_management.enumeration.RequestStatus;
import org.woridge.ms_internal_users_management.service.ProfileUpdateRequestService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/profile-update-requests")
@AllArgsConstructor
public class ProfileUpdateRequestController {

    private final ProfileUpdateRequestService profileUpdateRequestService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<ProfileUpdateSummaryRequest>>> getAllProfileUpdateRequests() {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "All profile update requests fetched successfully",
                        profileUpdateRequestService.getAllProfileUpdateRequests()
                )
        );
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<SuccessResponse<ProfileUpdateDetailsRequest>> getProfileUpdateRequestDetails(@PathVariable UUID requestId) {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "Profile update request details fetched successfully",
                        profileUpdateRequestService.getProfileUpdateRequestDetails(requestId)
                )
        );
    }

    @PatchMapping("/{requestId}")
    public ResponseEntity<SuccessResponse<Void>> processProfileUpdateRequest(
            @PathVariable UUID requestId,
            @RequestParam RequestStatus requestStatus) {

        profileUpdateRequestService.processProfileUpdateRequest(requestId, requestStatus);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Request status updated successfully",
                                null
                        )
                );
    }
}
