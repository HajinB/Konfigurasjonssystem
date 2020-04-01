package org.programutvikling.bruker;

import javafx.beans.property.SimpleStringProperty;
import org.programutvikling.bruker.exceptions.InvalidBrukernavnException;
import org.programutvikling.bruker.exceptions.InvalidPassordException;

import java.io.Serializable;

public class Bruker {
    private SimpleStringProperty brukernavn;
    private SimpleStringProperty passord;

    public Bruker (String brukernavn, String passord) {
        // validering
        if(!BrukerValidator.brukernavn(brukernavn)) {
            throw new InvalidBrukernavnException();
        }
        //hvis brukernavn tatt
        /* if() {
        throw new IllegalArgumentException("Brukernavnet er allerede i bruk!");
        }
        *
        */
        if(!BrukerValidator.passord(passord)){
            throw new InvalidPassordException();
        }

        this.brukernavn = new SimpleStringProperty(brukernavn);
        this.passord = new SimpleStringProperty(passord);
    }
}
