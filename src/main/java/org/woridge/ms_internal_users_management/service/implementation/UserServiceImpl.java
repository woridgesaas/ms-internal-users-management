package org.woridge.ms_internal_users_management.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.User.CreateUserRequest;
import org.woridge.ms_internal_users_management.dto.User.CreateUserResponse;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.exception.AuthFailedException;
import org.woridge.ms_internal_users_management.exception.DepartmentNotFoundException;
import org.woridge.ms_internal_users_management.exception.PostNotFoundException;
import org.woridge.ms_internal_users_management.model.Department;
import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.mapper.UserMapper;
import org.woridge.ms_internal_users_management.repository.DepartmentRepository;
import org.woridge.ms_internal_users_management.repository.PostRepository;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.AuthServiceClient;
import org.woridge.ms_internal_users_management.service.UserService;
import org.woridge.ms_internal_users_management.service.UserValidator;
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
    private final AuthServiceClient authServiceClient;


    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        // 1) unicité
        userValidator.checkUniqueEmail(request.getEmail());
        userValidator.checkUniqueCIN(request.getCIN());
        // 2) department
        Department department = departmentRepository.findByNameIgnoreCase(request.getDepartmentName()).orElseThrow(()->new DepartmentNotFoundException("department not found"));
        Post post =postRepository.findByNameIgnoreCase(request.getPostName()).orElseThrow(()->new PostNotFoundException("post not found"));

        // 3) generate temp password + encode
        String encoded =generatePassword();
        Role role = Role.valueOf(request.getRole()); // ou méthode safe

        // 4) map + save
        User user = userMapper.toEntity(request, department, post ,encoded, role);
        User saved = userRepository.save(user);

        CreateUserResponse dto = userMapper.toDto(saved);
        try {
            // 4️⃣ Appeler ms-auth
            authServiceClient.createUserInAuthService(saved, encoded);

        } catch (Exception ex) {
            // 5️⃣ Rollback si ms-auth échoue
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
}