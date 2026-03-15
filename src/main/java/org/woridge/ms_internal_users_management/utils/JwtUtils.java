package org.woridge.ms_internal_users_management.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.woridge.ms_internal_users_management.exception.auth.JwtTokenMissingException;

public final class JwtUtils {

    private JwtUtils() {

        throw new UnsupportedOperationException("You can't instantiate Jwt utils object");
    }

    public static String extractJwt(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {

            throw new JwtTokenMissingException("Authorization header is missing");
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new JwtTokenMissingException("Invalid Authorization header format. Bearer token expected.");
        }

        return authHeader.substring(7);
    }
}






