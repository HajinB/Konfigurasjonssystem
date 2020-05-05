package org.programutvikling.domain.user;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.programutvikling.domain.component.io.InvalidComponentFormatException;
import org.programutvikling.domain.user.exceptions.*;
import org.programutvikling.model.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    private transient SimpleBooleanProperty admin;
    private transient SimpleStringProperty username;
    private transient SimpleStringProperty password;
    private transient SimpleStringProperty name;
    private transient SimpleStringProperty email;
    private transient SimpleStringProperty address;
    private transient SimpleStringProperty zip;
    private transient SimpleStringProperty city;

    public User(boolean admin, String username, String password, String name, String email, String address, String zip, String city) {
        // validering

        if(!UserValidator.username(username)) {
            throw new InvalidUsernameException();
        }
        if(!UserValidator.password(password)) {
            throw new InvalidPasswordException();
        }
        if(!UserValidator.email(email)) {
            throw new InvalidEmailException();
        }
        if(!UserValidator.zip(zip)) {
            throw new InvalidZipException();
        }
        this.admin = new SimpleBooleanProperty(admin);
        this.username = new SimpleStringProperty(username.toLowerCase());
        this.password = new SimpleStringProperty(password);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email.toLowerCase());
        this.address = new SimpleStringProperty(address);
        this.zip = new SimpleStringProperty(zip);
        this.city = new SimpleStringProperty(city);
    }
    // Getter and setters

    public boolean getAdmin() {
        return admin.get();
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        if(!UserValidator.username(username)) {
            throw new InvalidUsernameException();
        }
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getZip() {
        return zip.get();
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeBoolean(getAdmin());
        s.writeUTF(getUsername());
        s.writeUTF(getPassword());
        s.writeUTF(getName());
        s.writeUTF(getEmail());
        s.writeUTF(getAddress());
        s.writeUTF(getZip());
        s.writeUTF(getCity());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException, InvalidComponentFormatException {

        boolean admin = s.readBoolean();
        String username = s.readUTF();
        String password = s.readUTF();
        String name = s.readUTF();
        String email = s.readUTF();
        String address = s.readUTF();
        String zip = s.readUTF();
        String city = s.readUTF();

        this.admin = new SimpleBooleanProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.zip = new SimpleStringProperty();
        this.city = new SimpleStringProperty();


            setAdmin(admin);
            setUsername(username);
            setPassword(password);
            setName(name);
            setEmail(email);
            setAddress(address);
            setZip(zip);
            setCity(city);

    }

    public String toString() {
        return "name: " + getName() + ", username: " + getUsername() + ", password: " + getPassword();
    }

}
