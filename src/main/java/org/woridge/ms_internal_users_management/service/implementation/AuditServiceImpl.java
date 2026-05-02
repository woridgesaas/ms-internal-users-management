package org.woridge.ms_internal_users_management.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.service.AuditService;

import java.util.Map;

@Service
@Slf4j
public class AuditServiceImpl implements AuditService {

    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT_LOGGER");

    public void log(String action, String target, String status) {

            try {

                Map<String, String> existingMdc = MDC.getCopyOfContextMap();


                MDC.clear();

                if (existingMdc != null) {
                    MDC.put("tenant", existingMdc.getOrDefault("tenant", "unknown"));
                    MDC.put("userMatricule", existingMdc.getOrDefault("userMatricule", "unknown"));
                    MDC.put("userRole", existingMdc.getOrDefault("userRole", "unknown"));
                    MDC.put("service", existingMdc.getOrDefault("service", "unknown"));
                }

                MDC.put("action", action);
                MDC.put("target", target);
                MDC.put("status", status);

                auditLogger.info("AUDIT"); //message attribute here :)

            } finally {
                MDC.remove("action");
                MDC.remove("target");
                MDC.remove("status");

            }
        }


}
