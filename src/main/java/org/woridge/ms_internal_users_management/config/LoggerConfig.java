package org.woridge.ms_internal_users_management.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.woridge.ms_internal_users_management.dto.tenant.TenantContext;
import org.woridge.ms_internal_users_management.enumeration.LogType;

import java.io.IOException;

@Component
@Slf4j
public class LoggerConfig extends OncePerRequestFilter {
    @Value("${spring.application.name}")
    private String serviceName;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        try {
            // MDC automatique
            MDC.put("service", serviceName);
            MDC.put("method", request.getMethod());
            MDC.put("uri", request.getRequestURI());
            MDC.put("clientIp", request.getRemoteAddr());

            String tenantId = TenantContext.getTenantId();
            if (tenantId != null) {
                MDC.put("tenant", tenantId);
            }else{
                MDC.put("tenant", "local-testing");
            }

            if (MDC.get("LogType") == null) {
                MDC.put("LogType", LogType.DEV.getValue());
            }

            filterChain.doFilter(request, response);

        } finally {
            long duration = System.currentTimeMillis() - startTime;
            MDC.put("duration", String.valueOf(duration));
            MDC.put("status", String.valueOf(response.getStatus()));


            log.info("Request completed");

            MDC.clear();
        }
    }
}
