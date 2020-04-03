package org.programutvikling.komponent.io;


import java.io.IOException;

public class InvalidComponentFormatException extends IOException {
    InvalidComponentFormatException(String message) {
        super(message);
    }
}
