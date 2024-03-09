package com.sfd.usermanagement.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfd.usermanagement.config.mongodb.TenantProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author kuldeep
 */
@Service
@RequiredArgsConstructor
public class S3DownloadService {
    private final AmazonS3 amazonS3;
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Value("${tenant.db-config.location.s3-bucket}")
    private String s3BucketName;

    public List<TenantProperties> downloadByActiveProfile(String filename) {

        S3Object s3object = amazonS3.getObject(s3BucketName, filename);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        try {
            List<LinkedHashMap<String, Object>> databaseConfigDtos = OBJECT_MAPPER.readValue(inputStream, List.class);
            return databaseConfigDtos.stream().map(linkedHashMap -> {
                TenantProperties tp = new TenantProperties();
                tp.setTenant(Objects.toString(linkedHashMap.get("tenant")));
                MongoProperties mongoProperties = new MongoProperties();
                mongoProperties.setUri(Objects.toString(linkedHashMap.get("uri")));
                mongoProperties.setDatabase(Objects.toString(linkedHashMap.get("database")));
                mongoProperties.setPort(Integer.parseInt(Objects.toString(linkedHashMap.get("port"))));
                mongoProperties.setUsername(Objects.toString(linkedHashMap.get("username")));
                mongoProperties.setPassword(Objects.toString(linkedHashMap.get("password")).toCharArray());
                tp.setProperties(mongoProperties);
                return tp;
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
