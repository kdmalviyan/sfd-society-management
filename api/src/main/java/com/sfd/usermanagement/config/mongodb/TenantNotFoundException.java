package com.sfd.usermanagement.config.mongodb;

/**
 * @author kuldeep
 */
public class TenantNotFoundException extends RuntimeException{
    public TenantNotFoundException(String message) {
        super(message);
    }
}
