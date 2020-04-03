package org.programutvikling.user;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.programutvikling.user.exceptions.InvalidPasswordException;
import org.programutvikling.user.exceptions.InvalidUsernameException;

public class User {
    private SimpleBooleanProperty admin;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty name;
    private SimpleStringProperty email;
    private SimpleStringProperty phone;
    private SimpleStringProperty address;
    private SimpleStringProperty zip;
    private SimpleStringProperty city;

    public User(boolean admin, String username, String password, String name, String email, String phone, String address, String zip, String city) {
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

        this.admin = new SimpleBooleanProperty(admin);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
        this.zip = new SimpleStringProperty(zip);
        this.city = new SimpleStringProperty(city);
    }

}
