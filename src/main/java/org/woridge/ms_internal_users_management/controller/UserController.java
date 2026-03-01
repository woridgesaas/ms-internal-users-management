package org.woridge.ms_internal_users_management.controller;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.User.CreateUserRequest;
import org.woridge.ms_internal_users_management.dto.User.CreateUserResponse;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.dto.User.UserSearchResponse;
import org.woridge.ms_internal_users_management.exception.UserNotFoundException;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('HR')")
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


    @GetMapping("/search")
    public ResponseEntity<UserSearchResponse> searchByCin(@RequestParam String cin) {
        return ResponseEntity.ok(userService.searchByCin(cin));
    }



}