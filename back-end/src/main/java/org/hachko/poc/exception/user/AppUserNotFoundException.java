package org.hachko.poc.exception.user;

public class AppUserNotFoundException extends RuntimeException{
    public AppUserNotFoundException(String message) {
        super(message);
    }

}
