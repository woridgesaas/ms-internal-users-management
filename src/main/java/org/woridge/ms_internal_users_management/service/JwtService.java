package org.woridge.ms_internal_users_management.service;


import org.woridge.ms_internal_users_management.enumeration.Role;

public interface JwtService {

    String extractCIN(String token);

    Role extractRole(String token);

    Boolean isValidToken(String token);
}
