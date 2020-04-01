package org.programutvikling.bruker;

public class BrukerValidator {
    // ingen tall
    public static final int PASSORD_LENGTH = 6;

    static boolean brukernavn(String brukernavn) {
        return (!brukernavn.isBlank());
    }
    static boolean passord(String passord) {
        return passord.length() > PASSORD_LENGTH;
    }
}


