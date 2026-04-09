package org.woridge.ms_internal_users_management.service.implementation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.user.*;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.exception.auth.AuthFailedException;
import org.woridge.ms_internal_users_management.exception.department.DepartmentNotFoundException;
import org.woridge.ms_internal_users_management.exception.post.PostNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.integration.auth.AuthServiceClient;
import org.woridge.ms_internal_users_management.messaging.publisher.NotificationEventPublisher;
import org.woridge.ms_internal_users_management.model.Department;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.mapper.UserMapper;
import org.woridge.ms_internal_users_management.repository.DepartmentRepository;
import org.woridge.ms_internal_users_management.repository.PostRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.SalaryService;
import org.woridge.ms_internal_users_management.service.UserService;
import org.woridge.ms_internal_users_management.service.UserValidator;
import org.woridge.ms_internal_users_management.utils.PasswordGenerator;

import java.util.List;
import java.util.UUID;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final HttpServletRequest httpServletRequest;
    private final AuthServiceClient authServiceClient;
    private final NotificationEventPublisher notificationEventPublisher;
    private final SalaryService salaryService;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        // 1) unicité
        userValidator.checkUniqueEmail(request.getEmail());
        userValidator.checkUniqueCIN(request.getCIN());
        // 2) department
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new DepartmentNotFoundException("department not found"));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new PostNotFoundException("post not found"));

        // 3) generate temp password + encode
        String tempPassword = PasswordGenerator.generate();
        String hashedPassword = passwordEncoder.encode(tempPassword);
        Role role = Role.valueOf(request.getRole());

        // 4) map + save
        User user = userMapper.toUser(request, department, post, hashedPassword, role);
        User saved = userRepository.save(user);
        //  créer salaire initial automatiquement
        salaryService.createNewSalary(
                saved,
                post,
                post.getBaseSalary()
        );

        CreateUserResponse dto = userMapper.toCreateUserResponse(saved);

        try {
            // 5) Appel du service externe
            authServiceClient.createUser(saved, hashedPassword);

        } catch (Exception ex) {
            // 6) Rollback si échec ms-auth
            userRepository.delete(saved);
            throw new AuthFailedException("Auth service failed. User rollback executed.");
        }
        // notification email
        notificationEventPublisher.publishUserCreated(saved, tempPassword);
        notificationEventPublisher.publishUserSync(saved);
        log.error("🔥 BEFORE SYNC 🔥");
        notificationEventPublisher.publishUserSync(saved);
        return dto;
    }

    @Override
    public UserSearchResponse searchByCin(String cin) {
        User user = userRepository.findByCIN(cin)
                .orElseThrow(() -> new UserNotFoundException("User not found with CIN: " + cin));

        return new UserSearchResponse(
                user.getId(),
                user.getCIN(),
                user.getFirstName() + " " + user.getLastName(),
                user.getDepartment() != null ? user.getDepartment().getName() : ""
        );
    }
    public UUID findIdByCIN(String cin) {
        return userRepository.findByCIN(cin)
                .orElseThrow(() -> new UserNotFoundException("user not found"))
                .getId();
    }

    @Override
    public List<GetAllUserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> userMapper.toGetAllUserResponse(u))
                .toList();
    }
}