package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.user.*;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.service.DeleteService;
import org.woridge.ms_internal_users_management.service.ProfileUpdateRequestService;
import org.woridge.ms_internal_users_management.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final DeleteService deleteService;
    private final ProfileUpdateRequestService profileUpdateRequestService;

    @PostMapping
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

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<UserProfileResponse>> getUserProfile(@PathVariable UUID userId) {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "User profile fetched successfully",
                        userService.getUserProfile(userId)
                )

        );
    }

    @GetMapping
    public ResponseEntity<List<GetAllUserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> softDeleteUser(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(deleteService.softDeleteUser(id));
    }

    @GetMapping("/search")
    public ResponseEntity<UserSearchResponse> searchByCin(@RequestParam String cin) {
        return ResponseEntity.ok(userService.searchByCin(cin));
    }

    @GetMapping("/profile/{CIN}")
    public ResponseEntity<SuccessResponse<CurrentUserProfileResponse>> getCurrentUserProfile(@PathVariable String CIN) {

        return ResponseEntity.ok(
                SuccessResponse.createSuccessResponse(
                        "User profile fetched successfully",
                        userService.getCurrentUserProfile(CIN)
                )
        );
    }

    @PostMapping("/submit/{userId}")
    public ResponseEntity<SuccessResponse<Void>> submitUpdateUserPersonalInformationRequest(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserPersonalInformationRequest updateUserPersonalInformationRequest) {

        profileUpdateRequestService.submitPersonalInformationUpdateRequest(userId, updateUserPersonalInformationRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Update request submitted successfully",
                                null
                        )
                );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SuccessResponse<Void>> updateUserPersonalInformation(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserPersonalInformationRequest updateUserPersonalInformationRequest) {

        userService.updateUserPersonalInformation(userId, updateUserPersonalInformationRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        SuccessResponse.createSuccessResponse(
                                "Personal information updated successfully",
                                null
                        )
                );
    }
}