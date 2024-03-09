package com.sfd.usermanagement.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kuldeep
 */

@Configuration
public class S3BeanConfig {
    private final String region;
    public S3BeanConfig(@Value("${tenant.db-config.location.region}") String region) {
        this.region = region;
    }
    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .build();
    }
}
