package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.woridge.ms_internal_users_management.dto.User.CreateUserInAuthRequest;
import org.woridge.ms_internal_users_management.mapper.AuthMapper;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.service.AuthServiceClient;

@Service
@AllArgsConstructor
public class AuthServiceClientImpl implements AuthServiceClient {

    private final RestTemplate restTemplate;
    private final String authServiceUrl = "http://localhost:5265/api/auth/users";

    public void createUserInAuthService(User user, String hashedPassword) {
        CreateUserInAuthRequest request = AuthMapper.toCreateUserInAuthRequest(user, hashedPassword);
        restTemplate.postForEntity(authServiceUrl, request, Void.class);
    }

}

