package org.programutvikling.component;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Component implements Serializable {

    private static final long serialVersionUID = 1;

    private transient SimpleStringProperty type;
    private transient SimpleStringProperty name;
    private transient SimpleStringProperty description;
    private transient SimpleDoubleProperty price;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Component(String type, String name, String description, double price) {
        // Validator av type, name og price(sjekke om den er 0?) her

        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
    }
    // Get- og set-metoder

    public String getType() {
        return type.getValue();
    }

    public final void setType(String type) {
        // validator
        this.name.set(type);
    }

    public String getName() {
        return name.getValue();
    }

    public final void setName(String name) {
        //validator

        this.name.set(name);
    }

    public String getDescription() {
        return description.getValue();
    }

    public final void setDescription(String description) {
        this.description.set(description);
    }

    public double getPrice() {
        return price.getValue();
    }

    public final void setPrice(double price) {
        // evt validator
        this.price.set(price);
    }
    // toString
    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s",
                type.getValue(), name.getValue(), description.getValue(), price.getValue());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(getType());
        s.writeUTF(getName());
        s.writeUTF(getDescription());
        s.writeDouble(getPrice());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        String type = s.readUTF();
        String name = s.readUTF();
        String description = s.readUTF();
        double price =  s.readDouble();

        this.type = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();

        setType(type);
        setName(name);
        setDescription(description);
        setPrice(price);
    }


}
