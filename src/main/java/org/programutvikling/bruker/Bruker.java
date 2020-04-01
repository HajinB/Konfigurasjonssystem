package org.programutvikling.bruker;

import java.io.Serializable;

public class Bruker {
    private String brukernavn;
    private String passord;

    public Bruker (String brukernavn, String passord) {
        // validering

        this.brukernavn = brukernavn;
        this.passord = passord;
    }
}
