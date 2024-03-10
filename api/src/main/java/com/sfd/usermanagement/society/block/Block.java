package com.sfd.usermanagement.society.block;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author kuldeep
 */

@Document
@Data
public class Block {
    @Id
    private String id;
    private String blockName;
    private String description;
    private String societyId;
}
