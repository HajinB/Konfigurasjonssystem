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
        this.admin = new SimpleBooleanProperty(admin);

        if (UserValidator.username(username)) {
            this.username = new SimpleStringProperty(username.toLowerCase());
        }
        if (UserValidator.password(password)) {
            this.password = new SimpleStringProperty(password);
        }
        if (UserValidator.name(name)) {
            this.name = new SimpleStringProperty(name);
        }
        if (UserValidator.email(email)) {
            this.email = new SimpleStringProperty(email.toLowerCase());
        }
        if (UserValidator.address(address)) {
            this.address = new SimpleStringProperty(address);
        }
        if (UserValidator.zip(zip)) {
            this.zip = new SimpleStringProperty(zip);
        }
        if (UserValidator.city(city)) {
            this.city = new SimpleStringProperty(city);
        }
    }

    // Getter and setters

    public boolean getAdmin() {
        return admin.get();
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
        return username.get();
    }

    public void setUsername(String username) {
        if (!UserValidator.username(username)) {
            throw new IllegalArgumentException("Brukernavnet kan ikke være tomt!");
        }
        this.username.set(username);
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public SimpleStringProperty passwordProperty() {
        // replace the length of password with asterix, for "privacy" if you open the table
        String star = "*";
        int starLength = getPassword().length();
        StringBuilder starBuilder = new StringBuilder();

        for(int i = 0; i < starLength; i++) {
            starBuilder.append(star);
        }

        return new SimpleStringProperty(starBuilder.toString());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getZip() {
        return zip.get();
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public SimpleStringProperty zipProperty() {
        return zip;
    }

    public String getCity() {
        return city.get();
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
            //bruker ikke pris her, for å sammenligne to komponenter i en liste.
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
