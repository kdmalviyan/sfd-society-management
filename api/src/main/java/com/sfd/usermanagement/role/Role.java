package com.sfd.usermanagement.role;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author kuldeep
 */

@Document
@Data
public class Role {
    @Id
    private String id;
    private String name;
}
