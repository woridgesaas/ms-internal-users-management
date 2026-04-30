package org.woridge.ms_internal_users_management.config;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.woridge.ms_internal_users_management.dto.tenant.TenantContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Component
@Slf4j
public class TenantDatasourceConfig extends AbstractRoutingDataSource {

    @Value("${tenant.database.host}")
    private String tenantHost;

    @Value("${tenant.database.port}")
    private String tenantPort;

    @Value("${tenant.database.username}")
    private String tenantUsername;

    @Value("${tenant.database.password}")
    private String tenantPassword;

    @Value("${spring.datasource.url}")
    private String defaultUrl;

    @Value("${spring.datasource.username}")
    private String defaultUsername;

    @Value("${spring.datasource.password}")
    private String defaultPassword;

    private final Map<String, DataSource> tenantCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // Base par défaut
        DataSource defaultDs = DataSourceBuilder.create()
                .url(defaultUrl)
                .username(defaultUsername)
                .password(defaultPassword)
                .build();

        this.setDefaultTargetDataSource(defaultDs);
        this.setTargetDataSources(new HashMap<>());
    }

    @Override
    protected @Nullable Object determineCurrentLookupKey() {
        return TenantContext.getTenantId();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        String tenantId = TenantContext.getTenantId();
        log.info("Determining the tenant {} " , tenantId);
        if (tenantId != null) {
            return tenantCache.computeIfAbsent(tenantId,
                    id -> this.createTenantDataSource(id));
        }

        return super.determineTargetDataSource();
    }

    private DataSource createTenantDataSource(String tenantId) {
        String dbName = "woridge_" + tenantId + "_ms-user";
        String url = String.format("jdbc:postgresql://%s:%s/%s", tenantHost, tenantPort, dbName);

        log.info("Datasource for tenant {} " ,url);
        return DataSourceBuilder.create()
                .url(url)
                .username(tenantUsername)
                .password(tenantPassword)
                .build();
    }
}
