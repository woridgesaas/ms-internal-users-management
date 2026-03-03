package org.woridge.ms_internal_users_management.controller;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.user.CreateUserRequest;
import org.woridge.ms_internal_users_management.dto.user.CreateUserResponse;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.user.UserSearchResponse;
import org.woridge.ms_internal_users_management.dto.user.GetAllUserResponse;
import org.woridge.ms_internal_users_management.dto.user.EmployeeProfileResponse;
import org.woridge.ms_internal_users_management.service.DeleteService;
import org.woridge.ms_internal_users_management.service.EmployeeProfileService;
import org.woridge.ms_internal_users_management.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmployeeProfileService profileService;
    private final DeleteService deleteService;

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
    public ResponseEntity<List<GetAllUserResponse>>getAllUsers()
    {
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



}