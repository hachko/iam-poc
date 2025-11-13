package org.hachko.poc.exception.user;

public class AppUserDuplicateUserNameException extends AppUserConflictException {
    public AppUserDuplicateUserNameException(String message) {
        super(message);
    }
}
