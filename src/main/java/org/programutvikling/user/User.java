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
        // if (usernameExists) {
        //   throw new IllegalArgumentException("Brukernavnet er allerede i bruk!");
        // }

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

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
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

}
