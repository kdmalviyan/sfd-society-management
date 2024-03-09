package com.sfd.usermanagement.auth;

import lombok.Data;

/**
 * @author kuldeep
 */

@Data
public class LoginRequest {
    private String username;
    private String password;
}
