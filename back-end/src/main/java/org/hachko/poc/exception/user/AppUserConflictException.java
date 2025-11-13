package org.hachko.poc.exception.user;

public class AppUserConflictException extends RuntimeException {
    public AppUserConflictException(String message) {
        super(message);
    }    
}
