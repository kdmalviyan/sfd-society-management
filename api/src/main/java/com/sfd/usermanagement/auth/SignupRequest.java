package com.sfd.usermanagement.auth;

import lombok.Data;

/**
 * @author kuldeep
 */
@Data
public class SignupRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String password;
}
