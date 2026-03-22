package org.woridge.ms_internal_users_management.integration.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.woridge.ms_internal_users_management.dto.user.CreateUserInAuthRequest;
import org.woridge.ms_internal_users_management.mapper.AuthMapper;
import org.woridge.ms_internal_users_management.model.User;

@Service
public class AuthServiceClient {

    @Value("${internal.secret-key}")
    private String internalSecretKey;

    @Value("${services.auth-url}")
    private String authServiceUrl;

    private final WebClient webClient;

    public AuthServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void createUser(User user, String hashedPassword) {

        CreateUserInAuthRequest request =
                AuthMapper.toCreateUserInAuthRequest(user, hashedPassword);

        webClient.post()
                .uri(authServiceUrl)
                .header("X-Internal-Secret-Key", internalSecretKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}