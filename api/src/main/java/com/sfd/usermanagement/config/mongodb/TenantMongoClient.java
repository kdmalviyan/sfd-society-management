package com.sfd.usermanagement.config.mongodb;

import com.mongodb.client.MongoClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author kuldeep
 */
@Getter
@AllArgsConstructor
@ToString
public class TenantMongoClient {
    private MongoClient mongoClient;
    private String database;
}
