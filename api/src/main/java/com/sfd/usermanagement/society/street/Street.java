package com.sfd.usermanagement.society.street;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author kuldeep
 */
@Document
@Data
public class Street {
    @Id
    private String id;
    private String streetNum;
    private String description;
    private String blockId;
    private String societyId;
}
