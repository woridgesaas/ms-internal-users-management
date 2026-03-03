package org.woridge.ms_internal_users_management.service.implementation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.user.CreateUserRequest;
import org.woridge.ms_internal_users_management.dto.user.CreateUserResponse;

import org.woridge.ms_internal_users_management.dto.User.UserSearchResponse;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.exception.auth.AuthFailedException;
import org.woridge.ms_internal_users_management.exception.department.DepartmentNotFoundException;
import org.woridge.ms_internal_users_management.exception.post.PostNotFoundException;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.model.Department;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.mapper.UserMapper;
import org.woridge.ms_internal_users_management.repository.DepartmentRepository;
import org.woridge.ms_internal_users_management.repository.PostRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.UserExternalService;
import org.woridge.ms_internal_users_management.service.UserService;
import org.woridge.ms_internal_users_management.service.UserValidator;
import org.woridge.ms_internal_users_management.utils.JwtUtils;
import org.woridge.ms_internal_users_management.utils.PasswordGenerator;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final UserExternalService userExternalService;
    private final HttpServletRequest httpServletRequest;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        // 1) unicité
        userValidator.checkUniqueEmail(request.getEmail());
        userValidator.checkUniqueCIN(request.getCIN());
        // 2) department
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()->new DepartmentNotFoundException("department not found"));
        Post post =postRepository.findById(request.getPostId()).orElseThrow(()->new PostNotFoundException("post not found"));

        // 3) generate temp password + encode
        String Hashed =generatePassword();
        Role role = Role.valueOf(request.getRole());

        // 4) map + save
        User user = userMapper.toEntity(request, department, post ,Hashed, role);
        User saved = userRepository.save(user);

        CreateUserResponse dto = userMapper.toDto(saved);
        // 5️⃣ Récupérer JWT du contexte pour l'envoyer à ms-auth
        String jwtToken = JwtUtils.extractJwt(httpServletRequest);
        try {
            // 6️⃣ Appel du service externe
            userExternalService.createUser(saved, Hashed, jwtToken);
        } catch (Exception ex) {
            // 7️⃣ Rollback si échec ms-auth
            userRepository.delete(saved);
            throw new AuthFailedException("Auth service failed. User rollback executed.");
        }
        return dto;
    }
    private String generatePassword()
    {
        String tempPassword = PasswordGenerator.generate();
        return passwordEncoder.encode(tempPassword);
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
}