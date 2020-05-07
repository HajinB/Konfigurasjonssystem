package org.programutvikling.model.io;


import org.programutvikling.gui.utility.Dialog;

import java.io.IOException;

public class InvalidComponentFormatException extends IOException {
    public InvalidComponentFormatException(String message) {
        super(message);
    }
}
