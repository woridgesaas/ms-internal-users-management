package org.woridge.ms_internal_users_management.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.woridge.ms_internal_users_management.dto.ErrorResponse;
import org.woridge.ms_internal_users_management.dto.tenant.TenantContext;
import org.woridge.ms_internal_users_management.enumeration.ErrorCode;

import java.io.IOException;

@Component
public class InternalRequestFilter extends OncePerRequestFilter {

    @Value("${internal.secret-key}")
    private String internalSecretKey;
    private final ObjectMapper objectMapper;

    public InternalRequestFilter(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Verify gateway secret key
        String internalSecretKey = request.getHeader("X-Internal-Secret-Key");

        if (internalSecretKey == null || !internalSecretKey.equals(this.internalSecretKey)) {

            writeErrorResponse(
                    response,
                    HttpStatus.FORBIDDEN,
                    "Request did not come through the gateway",
                    ErrorCode.REQUEST_NOT_ROUTED_THROUGH_GATEWAY
            );
            return;
        }

        // Verify CIN header
        String CIN = request.getHeader("X-User-CIN");

        if (CIN == null || CIN.isBlank()) {

            writeErrorResponse(
                    response,
                    HttpStatus.BAD_GATEWAY,
                    "CIN header is missing",
                    ErrorCode.MISSING_GATEWAY_FORWARDED_HEADER
            );
            return;
        }

        // Verify Role header
        String role = request.getHeader("X-User-Role");

        if (role == null || role.isBlank()) {

            writeErrorResponse(
                    response,
                    HttpStatus.BAD_GATEWAY,
                    "Role header is missing",
                    ErrorCode.MISSING_GATEWAY_FORWARDED_HEADER
            );

            return;
        }
        try {
            String subdomain = request.getHeader("X-Subdomain");
            if (subdomain != null && !subdomain.isBlank()) {
                TenantContext.setTenantId(subdomain);
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }

    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message, ErrorCode errorCode) throws IOException {

        ErrorResponse<String> error = ErrorResponse.createErrorResponse(
                message,
                errorCode
        );

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
