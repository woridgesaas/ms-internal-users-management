package org.woridge.ms_internal_users_management.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.SuccessResponse;
import org.woridge.ms_internal_users_management.service.DeleteService;
import org.woridge.ms_internal_users_management.strategy.DeleteStrategyFactory;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteServiceImpl implements DeleteService  {

    private final DeleteStrategyFactory factory;

    public SuccessResponse<Void> softDeleteUser(UUID userId) {
        return factory.getStrategy("userSoftDelete").delete(userId);
    }
}
