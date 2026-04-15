package org.woridge.ms_internal_users_management.mapper;

import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.user.*;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.enumeration.Status;
import org.woridge.ms_internal_users_management.model.Department;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.utils.StringUtils;

import java.time.Instant;
import java.util.List;

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
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .picturePath(user.getPicturePath())
                .postName(user.getPost().getName())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public void updateUser(User currentUser, UpdateUserPersonalInformationRequest updateUserPersonalInformationRequest) {

        currentUser.setFirstName(updateUserPersonalInformationRequest.firstName());
        currentUser.setLastName(updateUserPersonalInformationRequest.lastName());
        currentUser.setCIN(updateUserPersonalInformationRequest.cin());
        currentUser.setEmail(updateUserPersonalInformationRequest.email());
        currentUser.setPhoneNumber(updateUserPersonalInformationRequest.phone());
        currentUser.setBirthDate(updateUserPersonalInformationRequest.birthDate());
        currentUser.setAddress(updateUserPersonalInformationRequest.address());
        currentUser.setPicturePath(updateUserPersonalInformationRequest.profileImageUrl());
    }

    public UserProfileResponse toUserProfileResponse(User user) {

        return UserProfileResponse
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .CIN(user.getCIN())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .picturePath(user.getPicturePath())
                .role(user.getRole().getRoleName())
                .departmentName(user.getDepartment() != null ? user.getDepartment().getName() : null)
                .postName(user.getPost() != null ? user.getPost().getName() : null)
                .createdAt(user.getCreatedAt())
                .birthDate(user.getBirthDate())
                .bankInformationSummary(user.getBankInformation() != null ? BankInformationMapper.toBankInformationSummary(user.getBankInformation()) : null)
                .educationSummaries(user.getEducations() != null ? EducationMapper.toEducationSummaries(user.getEducations()) : List.of())
                .experienceSummaries(user.getProfessionalExperiences() != null ? ProfessionalExperienceMapper.toProfessionalExperienceSummaries(user.getProfessionalExperiences()) : List.of())
                .build();
    }

    public CurrentUserProfileResponse toCurrentUserProfileResponse(User user) {

        return CurrentUserProfileResponse
                .builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .picturePath(user.getPicturePath())
                .build();
    }
}

