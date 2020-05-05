package org.programutvikling.domain.user.exceptions;

import org.programutvikling.domain.user.UserValidator;

public class InvalidZipException extends IllegalArgumentException {
    public InvalidZipException() {
        super(String.format("The zip-code needs to be " + UserValidator.ZIP_LENGTH + " letters!"));
    }
}

