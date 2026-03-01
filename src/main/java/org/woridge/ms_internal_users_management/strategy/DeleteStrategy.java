package org.woridge.ms_internal_users_management.strategy;

import org.woridge.ms_internal_users_management.dto.SuccessResponse;

import java.util.UUID;

public interface DeleteStrategy {
    /**
     * Supprime une entité par son ID.
     *
     * @param id ID de l'entité
     */
    SuccessResponse<Void> delete(UUID id);
}
