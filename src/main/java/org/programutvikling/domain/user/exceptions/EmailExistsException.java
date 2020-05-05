package org.programutvikling.domain.user.exceptions;

public class EmailExistsException extends IllegalArgumentException {
    public EmailExistsException() {
        super("The email already exists!");
    }
}
