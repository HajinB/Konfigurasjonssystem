package org.programutvikling.component.io;


import java.io.IOException;

public class InvalidComponentFormatException extends IOException {
    public InvalidComponentFormatException(String message) {
        super(message);
    }
}
