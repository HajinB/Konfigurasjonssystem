package org.programutvikling.domain.io;


import java.io.IOException;

public class InvalidComponentFormatException extends IOException {
    public InvalidComponentFormatException(String message) {
        super(message);
    }
}
