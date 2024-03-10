package com.sfd.usermanagement.common;

import lombok.Data;

/**
 * @author kuldeep
 */
@Data
public class InvalidDataException extends RuntimeException {
    private int statusCode;
    public InvalidDataException(String message, Throwable th, int statusCode) {
        super(message, th);
        this.statusCode = statusCode;
    }

    public InvalidDataException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
