package org.woridge.ms_internal_users_management.strategy.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.enumeration.Status;
import org.woridge.ms_internal_users_management.exception.user.UserOperationException;
import org.woridge.ms_internal_users_management.model.User;
import org.woridge.ms_internal_users_management.repository.UserRepository;
import org.woridge.ms_internal_users_management.strategy.DeleteStrategy;

import java.time.Instant;
import java.util.UUID;

@Component("userSoftDelete")
@RequiredArgsConstructor
public class UserSoftDelete implements DeleteStrategy {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public SuccessResponse<Void> delete(UUID userId) {
        User user = userRepository.findByIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new UserOperationException("Utilisateur introuvable ou déjà désactivé."));

        user.setStatus(Status.INACTIVE);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);

        return SuccessResponse.createSuccessResponse("Utilisateur désactivé avec succès.", null);
    }
}
