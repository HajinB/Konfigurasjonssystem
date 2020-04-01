package org.programutvikling.bruker.exceptions;

import org.programutvikling.bruker.BrukerValidator;

public class InvalidPassordException extends IllegalArgumentException {
    public InvalidPassordException() {
        super(String.format("Passordet må være %d bokstaver eller lengre!", BrukerValidator.PASSORD_LENGTH));
    }
}
