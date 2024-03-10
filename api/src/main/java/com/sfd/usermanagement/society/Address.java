package com.sfd.usermanagement.society;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author kuldeep
 */
@Document
@Data
public class Address {
    private String line1;
    private String line2;
    private String landmark;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
