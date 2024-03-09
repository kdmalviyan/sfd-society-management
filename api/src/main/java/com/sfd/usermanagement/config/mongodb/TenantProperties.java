package com.sfd.usermanagement.config.mongodb;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;

/**
 * @author kuldeep
 */
@Data
public class TenantProperties {
    private String tenant;
    private MongoProperties properties;
}
