package org.programutvikling.domain.user.exceptions;

import org.programutvikling.domain.user.UserValidator;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException() {
        super("The password needs to be at least " + UserValidator.PASSWORD_LENGTH + " letters!");
    }
}
