package org.programutvikling.domain.user;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.programutvikling.domain.utility.Clickable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable, Clickable {
    private transient SimpleBooleanProperty admin;
    private transient SimpleStringProperty username;
    private transient SimpleStringProperty password;
    private transient SimpleStringProperty name;
    private transient SimpleStringProperty email;
    private transient SimpleStringProperty address;
    private transient SimpleStringProperty zip;
    private transient SimpleStringProperty city;

    public User(boolean admin, String username, String password, String name, String email, String address, String zip, String city) {

        // Validation
        if(!UserValidator.username(username)) {
            throw new IllegalArgumentException("Brukernavnet kan ikke inneholde mellomrom!");
        }
        if(!UserValidator.password(password)) {
            throw new IllegalArgumentException("Passordet må være minst " + UserValidator.PASSWORD_LENGTH + " tegn!");
        }
        if(!UserValidator.name(name)) {
            throw new IllegalArgumentException("Navnet kan ikke være tomt!");
        }
        if(!UserValidator.email(email)) {
            throw new IllegalArgumentException("Feil email!");
        }
        if(!UserValidator.address(address)) {
            throw new IllegalArgumentException("Adressen kan ikke være tom!");
        }
        if(!UserValidator.zip(zip)) {
            throw new IllegalArgumentException("Postnummeret må inneholde minst " + UserValidator.ZIP_LENGTH + " tall!");
        }
        if(!UserValidator.city(city)) {
            throw new IllegalArgumentException("Poststed kan ikke være tomt!");
        }
        // If no exception thrown, the constructor will set the properties

        this.admin = new SimpleBooleanProperty(admin);
        this.username = new SimpleStringProperty(username.toLowerCase());
        this.password = new SimpleStringProperty(password);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email.toLowerCase());
        this.address = new SimpleStringProperty(address);
        this.zip = new SimpleStringProperty(zip);
        this.city = new SimpleStringProperty(city);
        this.admin = new SimpleBooleanProperty(admin);
    }

    // Getter and setters

    public boolean getAdmin() {
        return admin.getValue();
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }

    public SimpleStringProperty adminProperty() {
        if (admin.get()) {
            return new SimpleStringProperty("Admin");
        } else {
            // admin = false, the User is a "Bruker"
            return new SimpleStringProperty("Bruker");
        }
    }

    public String getAdminString() {
        if (admin.get()) {
            return "Admin";
        } else {
            // admin = false, the User is a "Bruker"
            return "Bruker";
        }
    }

    public String getUsername() {
        return username.getValueSafe();
    }

    public void setUsername(String username) {
        if (UserValidator.username(username)) {
            this.username.set(username);
        }
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setPassword(String password) {
        if(UserValidator.password(password)) {
            this.password.set(password);
        }
    }

    public SimpleStringProperty passwordProperty() {
        // replace the length of password with asterix, for "privacy" if you open the table
        String star = "*";
        int starLength = this.getPassword().length();
        StringBuilder starBuilder = new StringBuilder();

        for(int i = 0; i < starLength; i++) {
            starBuilder.append(star);
        }

        return new SimpleStringProperty(starBuilder.toString());
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        if(UserValidator.name(name)) {
            this.name.set(name);
        }
    }


    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) {
        if(UserValidator.email(email)) {
            this.email.set(email);
        }
    }


    public String getAddress() {
        return address.getValue();
    }

    public void setAddress(String address) {
        if(UserValidator.address(address)) {
            this.address.set(address);
        }
    }


    public String getZip() {
        return zip.getValue();
    }

    public void setZip(String zip) {
        if (UserValidator.zip(zip)) {
            this.zip.set(zip);
        }
    }


    public String getCity() {
        return city.getValue();
    }

    public void setCity(String city) {
        if (UserValidator.city(city)) {
            this.city.set(city);
        }
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO denne metoden er for å kunne fjerne duplikater med HashSet - må override equals og hashcode metodene
        //  til Object for å ha kontroll på de.
        if (obj instanceof User) {
            User temp = (User) obj;
            return this.getName().equals(temp.getName())
                    && this.getAdminString().equals(temp.getAdminString())
                    && this.getUsername().equals(temp.getUsername())
                    && this.getEmail().equals(temp.getEmail())
                    && this.getAddress().equals(temp.getAddress())
                    && this.getCity().equals(temp.getCity())
                    && this.getZip().equals(temp.getZip())
                    && this.getPassword().equals(temp.getPassword());

        }
        return false;
    }

    @Override
    public int hashCode() {
        // returnerer summen av hashcoden til hvert enkelt felt - blir en unik kode for hvert objekt.
        return (this.getName().hashCode() +
                this.getAdminString().hashCode() +
                this.getUsername().hashCode() +
                this.getEmail().hashCode() +
                this.getAddress().hashCode() +
                this.getCity().hashCode()+
                this.getZip().hashCode()+
                this.getPassword().hashCode());
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

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {

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
        return "\n" + "admin: " + getAdmin() + ", username: " + getUsername() + ", name: " + getName() +
                ", password: " + getPassword() + ", email: " + getEmail() +
                ", address: " + getAddress() +
                ", zip: " + getZip() + ", city: " + getCity();
    }

}
