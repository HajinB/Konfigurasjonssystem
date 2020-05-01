package org.programutvikling.domain.user.exceptions;

public class InvalidUsernameException extends IllegalArgumentException {
    public InvalidUsernameException() {
        super("The username can't be empty!");
    }
}