package com.sfd.usermanagement.security;

import lombok.Data;

/**
 * @author kuldeep
 */
@Data
public class InvalidCredentialsException extends RuntimeException {
    private int statusCode;
    public InvalidCredentialsException(String message, Throwable th, int statusCode) {
        super(message, th);
        this.statusCode = statusCode;
    }

    public InvalidCredentialsException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
