package com.sfd.usermanagement.config.mongodb;

import com.sfd.usermanagement.aws.s3.S3DownloadService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author kuldeep
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.sfd"})
@EntityScan(basePackages = {"com.sfd"})
public class MultiTenantMongoAutoConfiguration {
    @Bean
    @Primary
    public MongoDatabaseFactory mongoDatabaseFactory(final MultiTenantMongoConfig multiTenantMongoConfig) {
        final TenantMongoClient tenantMongoClient = multiTenantMongoConfig.getMultiTenantConfig().firstEntry().getValue();
        return new MultiTenantMongoDbFactory(multiTenantMongoConfig, tenantMongoClient);
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate(final MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
