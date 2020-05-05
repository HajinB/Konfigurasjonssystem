package org.programutvikling.model.io;

public class InvalidPriceException extends NumberFormatException {
    public InvalidPriceException () {
        super("Pris må være større enn 0");
    }

}
