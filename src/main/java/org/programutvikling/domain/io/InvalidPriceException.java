package org.programutvikling.domain.io;

public class InvalidPriceException extends NumberFormatException {
    public InvalidPriceException () {
        super("Pris må være større enn 0");
    }

}
