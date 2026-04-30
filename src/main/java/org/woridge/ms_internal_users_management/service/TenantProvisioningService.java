package org.woridge.ms_internal_users_management.service;

public interface TenantProvisioningService {
    void runMigrationsForTenant(String sub , String serviceKey);
}
