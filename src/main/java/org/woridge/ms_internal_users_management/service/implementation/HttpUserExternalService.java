package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.woridge.ms_internal_users_management.dto.User.CreateUserInAuthRequest;
import org.woridge.ms_internal_users_management.mapper.AuthMapper;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.service.UserExternalService;

@Service
@AllArgsConstructor
public class HttpUserExternalService implements UserExternalService {

    private final WebClient webClient;
    private final String authServiceUrl = "http://localhost:5265/api/user";

    @Override
    public void createUser(User user, String hashedPassword, String jwtToken) {
        CreateUserInAuthRequest request = AuthMapper.toCreateUserInAuthRequest(user, hashedPassword);

        webClient.post()
                .uri(authServiceUrl)
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // bloque car méthode synchrone
    }
}
