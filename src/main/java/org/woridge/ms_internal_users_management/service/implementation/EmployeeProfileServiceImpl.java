package org.woridge.ms_internal_users_management.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.user.EmployeeProfileResponse;
import org.woridge.ms_internal_users_management.exception.user.UserNotFoundException;
import org.woridge.ms_internal_users_management.mapper.EmployeeProfileMapper;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.service.EmployeeProfileService;
import org.woridge.ms_internal_users_management.service.UserValidator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final UserRepository userRepository;
    private final EmployeeProfileMapper profileMapper;
    private final UserValidator validator;

    @Override
    public EmployeeProfileResponse getEmployeeProfile(UUID userId) {

        User user = userRepository.findWithFullProfileById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
        validator.validateUserProfile(user);
        return profileMapper.toResponse(user);
    }
}
