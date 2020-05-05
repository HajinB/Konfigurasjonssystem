package org.programutvikling.domain.user.exceptions;

public class UsernameAlreadyExistsException extends IllegalArgumentException {
    public UsernameAlreadyExistsException() {
        super("The username already exists!");
    }
}