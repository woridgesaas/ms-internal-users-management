package org.woridge.ms_internal_users_management.service;

public interface AuditService {
     void log(String action, String target, String status);
}
