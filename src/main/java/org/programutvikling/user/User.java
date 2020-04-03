package org.programutvikling.user;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.programutvikling.user.exceptions.InvalidUsernameException;
import org.programutvikling.user.exceptions.InvalidPasswordException;

public class User {
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleBooleanProperty admin;

    public User(String username, String password, boolean admin) {
        // validering
        if(!UserValidator.username(username)) {
            throw new InvalidUsernameException();
        }
        //hvis brukernavn tatt
        /* if() {
        throw new IllegalArgumentException("Brukernavnet er allerede i bruk!");
        }
        *
        */
        if(!UserValidator.password(password)) {
            throw new InvalidPasswordException();
        }

        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.admin = new SimpleBooleanProperty(admin);
    }

}
