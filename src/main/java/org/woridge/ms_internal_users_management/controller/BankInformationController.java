package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationRequest;
import org.woridge.ms_internal_users_management.dto.bank_information.AddBankInformationResponse;
import org.woridge.ms_internal_users_management.dto.bank_information.UpdateBankInformationRequest;
import org.woridge.ms_internal_users_management.service.BankInformationService;
import org.woridge.ms_internal_users_management.service.ProfileUpdateRequestService;

import java.util.UUID;

@RestController
@RequestMapping("/bank-informations")
@AllArgsConstructor
public class BankInformationController {

    private final BankInformationService bankInformationService;
    private final ProfileUpdateRequestService profileUpdateRequestService;

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse<AddBankInformationResponse>> addBankInformation(
            @PathVariable UUID userId,
            @Valid @RequestBody AddBankInformationRequest addBankInformationRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.createSuccessResponse(
                                "Bank information added successfully",
                                bankInformationService.AddBankInformation(userId, addBankInformationRequest
                                )
                        )
                );
    }

    @PostMapping("/submit/{userId}/{bankInformationId}")
    public ResponseEntity<SuccessResponse<Void>> submitUpdateBankInformationRequest(
            @PathVariable UUID userId,
            @PathVariable UUID bankInformationId,
            @Valid @RequestBody UpdateBankInformationRequest updateBankInformationRequest) {

        profileUpdateRequestService.submitBankInformationUpdateRequest(userId, bankInformationId, updateBankInformationRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Bank information update request submitted successfully",
                                null
                        )
                );
    }

    @PutMapping("/{userId}/{bankInformationId}")
    public ResponseEntity<SuccessResponse<Void>> updateBankInformation(
            @PathVariable UUID userId,
            @PathVariable UUID bankInformationId,
            @Valid @RequestBody UpdateBankInformationRequest updateBankInformationRequest) {

        bankInformationService.updateBankInformation(userId, bankInformationId, updateBankInformationRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Bank information updated successfully",
                                null
                        )
                );
    }
}
