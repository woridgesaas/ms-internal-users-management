package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.user.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.exception.user.InvalidRequestStatus;
import org.woridge.ms_internal_users_management.service.DeleteService;
import org.woridge.ms_internal_users_management.service.EmployeeProfileService;
import org.woridge.ms_internal_users_management.service.UserService;
import org.woridge.ms_internal_users_management.service.UserUpdateRequestService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmployeeProfileService profileService;
    private final DeleteService deleteService;
    private final UserUpdateRequestService updateRequestService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CEO','HR')")
    public ResponseEntity<SuccessResponse<CreateUserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "User created successfully",
                                userService.createUser(request)
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProfileResponse> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(profileService.getEmployeeProfile(id));
    }

    @GetMapping
    public ResponseEntity<List<GetAllUserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CEO','HR')")
    public ResponseEntity<SuccessResponse<Void>> softDeleteUser(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(deleteService.softDeleteUser(id));
    }

    @GetMapping("/search")
    public ResponseEntity<UserSearchResponse> searchByCin(@RequestParam String cin) {
        return ResponseEntity.ok(userService.searchByCin(cin));
    }

    @PostMapping("/update-request")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<SuccessResponse<Void>> submitRequest(@RequestBody CreateUserUpdateRequest request) {

        UUID userId = updateRequestService.getCurrentUserId();
        updateRequestService.submitRequest(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.createSuccessResponse(
                        "Update request submitted successfully",
                        null
                ));
    }

    @GetMapping("/update-requests")
    @PreAuthorize("hasAnyRole('HR','CEO')")
    public ResponseEntity<SuccessResponse<List<HRUserUpdateRequest>>> getAllRequests() {
        List<HRUserUpdateRequest> requests = updateRequestService.getAllRequests();
        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "Requests fetched successfully",
                        requests)
        );
    }


    @GetMapping("/update-requests/{id}")
    @PreAuthorize("hasAnyRole('HR','CEO')")
    public ResponseEntity<SuccessResponse<HRUserUpdateRequestDetails>> getRequestDetails(@PathVariable UUID id) {
        HRUserUpdateRequestDetails details = updateRequestService.getRequestDetails(id);
        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "Request details fetched successfully",
                        details)
        );
    }

    @PatchMapping("/{id}/{status}")
    @PreAuthorize("hasAnyRole('HR','CEO')")
    public ResponseEntity<SuccessResponse<Void>> updateRequestStatus(@PathVariable UUID id, @PathVariable String status) {

        UUID hrId = updateRequestService.getCurrentUserId();
        switch (status.toUpperCase()) {
            case "APPROVED" -> updateRequestService.approveRequest(id, hrId);
            case "REJECTED" -> updateRequestService.rejectRequest(id, hrId);
            default -> throw new InvalidRequestStatus("Invalid status: " + status);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Request details fetched successfully",
                                null)
                );
    }

}