package org.woridge.ms_internal_users_management.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.woridge.ms_internal_users_management.exception.request.MissingCinInRequestHeaderException;
import org.woridge.ms_internal_users_management.exception.request.MissingRoleInRequestHeaderException;

public final class RequestUtils {

    private RequestUtils() {

        throw new UnsupportedOperationException("You can't instantiate request utils object");
    }

    public static String extractCINFromHeaderRequest(HttpServletRequest request) {

        String CIN = request.getHeader("X-User-CIN");

        if (CIN == null || CIN.isBlank()) {

            throw new MissingCinInRequestHeaderException("Missing CIN in request header");
        }

        return CIN;
    }

    public static String extractRoleFromHeaderRequest(HttpServletRequest request) {

        String role = request.getHeader("X-User-Role");

        if (role == null || role.isBlank()) {

            throw new MissingRoleInRequestHeaderException("Missing role in request header");
        }

        return role;
    }
}
