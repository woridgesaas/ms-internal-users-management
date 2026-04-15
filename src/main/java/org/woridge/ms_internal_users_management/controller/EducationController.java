package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.education.AddEducationRequest;
import org.woridge.ms_internal_users_management.dto.education.AddEducationResponse;
import org.woridge.ms_internal_users_management.dto.education.UpdateEducationRequest;
import org.woridge.ms_internal_users_management.service.EducationService;
import org.woridge.ms_internal_users_management.service.ProfileUpdateRequestService;

import java.util.UUID;

@RestController
@RequestMapping("/educations")
@AllArgsConstructor
public class EducationController {

    private final EducationService educationService;
    private final ProfileUpdateRequestService profileUpdateRequestService;

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse<AddEducationResponse>> addEducation(
            @PathVariable UUID userId,
            @Valid @RequestBody AddEducationRequest addEducationRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Education added successfully",
                                educationService.addEducation(userId, addEducationRequest)
                        )
                );
    }

    @PostMapping("/submit/{userId}/{educationId}")
    public ResponseEntity<SuccessResponse<Void>> submitUpdateEducationRequest(
            @PathVariable UUID userId,
            @PathVariable UUID educationId,
            @Valid @RequestBody UpdateEducationRequest updateEducationRequest) {

        profileUpdateRequestService.submitEducationUpdateRequest(userId, educationId, updateEducationRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Education update request submitted successfully",
                                null
                        )
                );
    }

    @PutMapping("/{userId}/{educationId}")
    public ResponseEntity<SuccessResponse<Void>> updateEducation(
            @PathVariable UUID userId,
            @PathVariable UUID educationId,
            @Valid @RequestBody UpdateEducationRequest updateEducationRequest) {

        educationService.updateEducation(userId, educationId, updateEducationRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Education updated successfully",
                                null
                        )
                );
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<SuccessResponse<Void>> deleteEducation(@PathVariable UUID educationId) {

        educationService.deleteEducation(educationId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(SuccessResponse.createSuccessResponse(
                                "Education deleted successfully",
                                null
                        )
                );
    }
}
