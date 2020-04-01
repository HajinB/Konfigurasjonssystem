package org.programutvikling.bruker;

public class BrukerValidator {
    // ingen tall
    private static final int MAX_LENGTH = 100;
    private static final String NAVN_VERIFICATION = "^\\D+$";
    private static final int PASSORD_LENGTH = 6;

    static boolean brukernavn(String brukernavn) {
        return (!brukernavn.isBlank() && !brukernavn.matches(NAVN_VERIFICATION)) && brukernavn.length() < MAX_LENGTH;
    }
    static boolean passord(String passord) {
        return passord.length() > PASSORD_LENGTH && passord.length() < MAX_LENGTH;
    }
}


