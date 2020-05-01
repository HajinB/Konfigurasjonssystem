package org.programutvikling.domain.user.exceptions;

import org.programutvikling.domain.user.UserValidator;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException() {
        super(String.format("The password needs to be at least %d letters!", UserValidator.PASSWORD_LENGTH));
    }
}
