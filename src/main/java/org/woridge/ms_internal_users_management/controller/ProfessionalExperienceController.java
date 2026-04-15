package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.dto.professional_experience.AddProfessionalExperienceResponse;
import org.woridge.ms_internal_users_management.dto.professional_experience.UpdateProfessionalExperienceRequest;
import org.woridge.ms_internal_users_management.service.ProfessionalExperienceService;
import org.woridge.ms_internal_users_management.service.ProfileUpdateRequestService;

import java.util.UUID;

@RestController
@RequestMapping("/professional-experiences")
@AllArgsConstructor
public class ProfessionalExperienceController {

    private final ProfessionalExperienceService professionalExperienceService;
    private final ProfileUpdateRequestService profileUpdateRequestService;

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse<AddProfessionalExperienceResponse>> addProfessionalExperience(
            @PathVariable UUID userId,
            @Valid @RequestBody AddProfessionalExperienceRequest addProfessionalExperienceRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Professional experience added successfully",
                                professionalExperienceService.addProfessionalExperience(userId, addProfessionalExperienceRequest)
                        )
                );
    }

    @PostMapping("/submit/{userId}/{professionalExperienceId}")
    public ResponseEntity<SuccessResponse<Void>> submitUpdateProfessionalExperienceRequest(
            @PathVariable UUID userId,
            @PathVariable UUID professionalExperienceId,
            @Valid @RequestBody UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest) {

        profileUpdateRequestService.submitProfessionalExperienceUpdateRequest(userId, professionalExperienceId, updateProfessionalExperienceRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Professional experience update request submitted successfully",
                                null
                        )
                );
    }

    @PutMapping("/{userId}/{professionalExperienceId}")
    public ResponseEntity<SuccessResponse<Void>> updateProfessionalExperience(
            @PathVariable UUID userId,
            @PathVariable UUID professionalExperienceId,
            @Valid @RequestBody UpdateProfessionalExperienceRequest updateProfessionalExperienceRequest) {

        professionalExperienceService.updateProfessionalExperience(userId, professionalExperienceId, updateProfessionalExperienceRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Professional experience updated successfully",
                                null
                        )
                );
    }

    @DeleteMapping("/{professionalExperienceId}")
    public ResponseEntity<SuccessResponse<Void>> deleteProfessionalExperience(@PathVariable UUID professionalExperienceId) {

        professionalExperienceService.deleteProfessionalExperience(professionalExperienceId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(SuccessResponse.createSuccessResponse(
                                "Professional experience deleted successfully",
                                null
                        )
                );
    }
}
