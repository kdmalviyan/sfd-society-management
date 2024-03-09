package com.sfd.usermanagement.auth;

import lombok.Builder;
import lombok.Data;

/**
 * @author kuldeep
 */
@Data
@Builder
public class SignupResponse {
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String message;
}
