package org.woridge.ms_internal_users_management.mapper;

import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.user.CreateUserRequest;
import org.woridge.ms_internal_users_management.dto.user.CreateUserResponse;
import org.woridge.ms_internal_users_management.dto.user.GetAllUserResponse;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.enumeration.Status;
import org.woridge.ms_internal_users_management.model.Department;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.utils.StringUtils;

import java.time.Instant;

@Component
public class UserMapper {

    public User toUser(CreateUserRequest req, Department dept, Post post_, String encodedPassword, Role role) {
        return User.builder()
                .firstName(req.getFirstName().trim())
                .lastName(req.getLastName().trim())
                .email(req.getEmail().trim().toLowerCase())
                .role(role)
                .phoneNumber(req.getPhoneNumber().trim())
                .CIN(req.getCIN().trim().toUpperCase())
                .password(encodedPassword)
                .status(Status.ACTIVE)
                .picturePath(StringUtils.trimToNull(req.getPicturePath()))
                .department(dept)
                .post(post_)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public CreateUserResponse toCreateUserResponse(User user) {

        return CreateUserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleName(user.getRole().getRoleName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .CIN(user.getCIN())
                .status(user.getStatus())
                .address(user.getAddress())
                .picturePath(user.getPicturePath())
                .departmentName(user.getDepartment().getName())
                .postName(user.getPost().getName())
                .build();

    }

    public GetAllUserResponse toGetAllUserResponse(User user) {
        return GetAllUserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .picturePath(user.getPicturePath())
                .postName(user.getPost().getName())
                .status(user.getStatus())
                .build();
    }
}

