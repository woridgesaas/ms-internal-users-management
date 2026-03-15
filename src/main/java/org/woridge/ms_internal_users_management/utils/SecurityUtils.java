package org.woridge.ms_internal_users_management.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.woridge.ms_internal_users_management.exception.auth.NoAuthoritiesFoundException;
import org.woridge.ms_internal_users_management.exception.auth.UnauthenticatedUserException;

public final class SecurityUtils {

    private SecurityUtils() {

        throw new UnsupportedOperationException("You can't instantiate security utils object");
    }

    public static String getCurrentUserCIN() {

        return getAuthentication().getName();
    }

    public static String getCurrentUserRole() {

        return getAuthentication()
                .getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new NoAuthoritiesFoundException("Authenticated user has no granted authorities"));
    }

    private static Authentication getAuthentication() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthenticatedUserException("No authenticated user found");
        }

        return authentication;
    }
}
