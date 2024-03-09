package com.sfd.usermanagement.config.mongodb;

import com.mongodb.client.MongoDatabase;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;


/**
 * @author kuldeep
 */
public class MultiTenantMongoDbFactory extends SimpleMongoClientDatabaseFactory {

    public static final String DEFAULT_DB_INSTACE = "test";
    private final MultiTenantMongoConfig multiTenantMongoConfig;

    public MultiTenantMongoDbFactory(final MultiTenantMongoConfig multiTenantMongoConfig,
                                     final TenantMongoClient tenantMongoClient) {
        super(tenantMongoClient.getMongoClient(), tenantMongoClient.getDatabase());
        this.multiTenantMongoConfig = multiTenantMongoConfig;
    }

    @Override
    public MongoDatabase getMongoDatabase() throws DataAccessException {
        final String tenant = TenantContext.getTenantId();
        MongoDatabase database = null;
        if (tenant != null) {
            final TenantMongoClient tenantMongoClient = multiTenantMongoConfig.getMultiTenantConfig().get(tenant);
            if (tenantMongoClient == null) {
                throw new TenantNotFoundException("Tenant " + tenant + " is not configured");
            }
            database = tenantMongoClient.getMongoClient().getDatabase(tenantMongoClient.getDatabase());
        } else {
            database = getMongoClient().getDatabase(DEFAULT_DB_INSTACE);
        }
        return database;
    }

    @Override
    public void destroy() throws Exception {
        multiTenantMongoConfig.getMultiTenantConfig().values().forEach(mongo -> mongo.getMongoClient().close());
    }
}