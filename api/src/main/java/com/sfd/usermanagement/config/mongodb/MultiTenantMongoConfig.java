package com.sfd.usermanagement.config.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.sfd.usermanagement.aws.s3.S3DownloadService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;


/**
 * @author kuldeep
 */
@Data
@Configuration
public class MultiTenantMongoConfig {
    private final List<TenantProperties> tenantProperties;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private TreeMap<String, TenantMongoClient> multiTenantConfig;

    public MultiTenantMongoConfig(final S3DownloadService s3DownloadService) {
        String filename = "mongodb-tenants-local.json";
        if (Objects.nonNull(activeProfile)
                && (Objects.equals("profile", "docker")
                || Objects.equals("profile", "prod"))) {
            filename = "mongodb-tenants-local.json";
        }
        this.tenantProperties = s3DownloadService.downloadByActiveProfile(filename);
    }

    public void refreshMultiTenantConfig(List<TenantProperties> tenantProperties) {
        for (final TenantProperties multiTenant : tenantProperties) {
            populateTenantConfig(multiTenant);
        }
    }

    @PostConstruct
    public void multiTenantMongoConfig() {
        multiTenantConfig = new TreeMap<>();
        for (final TenantProperties multiTenant : tenantProperties) {
            populateTenantConfig(multiTenant);
        }
    }

    private void populateTenantConfig(final TenantProperties multiTenant) {
        final String connectionUri = multiTenant.getProperties().getUri();
        final String host = multiTenant.getProperties().getHost();
        final Integer port = multiTenant.getProperties().getPort();
        MongoClient client;

        if (connectionUri != null) {
            client = MongoClients.create(connectionUri);
        } else if (host != null && port != null) {
            final String connection = "mongodb://" + host + ":" + port + "/";
            client = MongoClients.create(connection);
        } else {
            throw new RuntimeException("At-least one of the config properties is required [uri | host & port]");
        }
        final String database = multiTenant.getProperties().getDatabase();
        final TenantMongoClient tenantMongoClient = new TenantMongoClient(client, database);
        this.multiTenantConfig.put(multiTenant.getTenant(), tenantMongoClient);
    }

    @PreDestroy
    public void destroy() {
        multiTenantConfig.values().forEach(mongo -> mongo.getMongoClient().close());
    }
}