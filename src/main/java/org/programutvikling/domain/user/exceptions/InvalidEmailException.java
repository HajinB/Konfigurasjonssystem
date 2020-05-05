package org.programutvikling.domain.user.exceptions;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException() {
        super("Invalid email!");
    }
}
