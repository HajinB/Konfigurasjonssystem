package org.programutvikling.komponent;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Komponent implements Serializable {

    private static final long serialVersionUID = 1;

    private transient SimpleStringProperty type;
    private transient SimpleStringProperty name;
    private transient SimpleStringProperty description;
    private transient SimpleDoubleProperty price;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Komponent(String type, String name, String description, double price) {
        // Validator av type, name og price(sjekke om den er 0?) her

        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
    }
    // Get- og set-metoder

    public String getType() {return type.getValue();}

    public final void setType(String type) {
        // validator

        this.name.set(type);
    }

    public String getName() {return name.getValue();}

    public final void setName(String name) {
        //validator

        this.name.set(name);
    }

    public String getDescription() {return description.getValue();}

    public final void setDescription(String description) {
        this.description.set(description);
    }

    public double getPrice() {return price.getValue();}

    public final void setPrice(double price) {
        // evt validator
        this.price.set(price);
    }
    // toString
    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;\n",
                type.getValue(), name.getValue(), description.getValue(), price.getValue());
    }
}
