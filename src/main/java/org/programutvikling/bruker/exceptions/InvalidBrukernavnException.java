package org.programutvikling.bruker.exceptions;

import org.programutvikling.bruker.BrukerValidator;

public class InvalidBrukernavnException extends IllegalArgumentException {
    public InvalidBrukernavnException() {
        super("Brukernavnet kan ikke være tomt!");
    }
}