package org.programutvikling.user.exceptions;

import org.programutvikling.user.UserValidator;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException() {
        super(String.format("The password needs to be at least %d letters!", UserValidator.PASSWORD_LENGTH));
    }
}
