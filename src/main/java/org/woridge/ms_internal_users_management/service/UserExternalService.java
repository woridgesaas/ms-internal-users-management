package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.model.User;

public interface UserExternalService {
    /**
     * Crée un utilisateur dans un service externe (ex: ms-auth).
     *
     * @param user l'utilisateur à créer
     * @param hashedPassword le mot de passe encodé
     * @param jwtToken le token JWT de l'utilisateur courant
     */
    void createUser(User user, String hashedPassword, String jwtToken);
}
