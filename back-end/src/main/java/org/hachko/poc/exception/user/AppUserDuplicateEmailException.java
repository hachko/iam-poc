package org.hachko.poc.exception.user;

public class AppUserDuplicateEmailException extends AppUserConflictException {
    public AppUserDuplicateEmailException(String message) {
        super(message);
    }

}
