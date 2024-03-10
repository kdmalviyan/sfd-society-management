package com.sfd.usermanagement.society;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author kuldeep
 */

@Data
@Document
public class Society {
    @Id
    private String id;
    private String name;
    private Address address;
    private boolean isRegistered;
    private String registrationNum;
    private String officePhone;
    private String officeEmail;
}
