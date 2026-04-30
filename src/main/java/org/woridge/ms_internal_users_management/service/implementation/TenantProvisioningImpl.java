package org.woridge.ms_internal_users_management.service.implementation;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.service.TenantProvisioningService;

@RequiredArgsConstructor
@Service
@Slf4j
public class TenantProvisioningImpl implements TenantProvisioningService {
    @Value("${spring.datasource.url}")
    private String dbBaseUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public void runMigrationsForTenant(String subdomain, String serviceKey) {
        String dbName = String.format("woridge_%s_%s", subdomain, serviceKey);

        String tenantDbUrl = this.getBaseUrl() + dbName;

        log.info("Running migrations for: {}", tenantDbUrl);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(tenantDbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
       log.info("Migrations completed for: {}", dbName);
    }

    private String getBaseUrl() {
        String url = dbBaseUrl;
        int lastSlash = url.lastIndexOf('/');
        return url.substring(0, lastSlash + 1);
    }
}
