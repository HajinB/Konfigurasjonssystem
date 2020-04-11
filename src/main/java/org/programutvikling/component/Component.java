package org.programutvikling.component;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Component implements Serializable {

    private static final long serialVersionUID = 1;

    private transient SimpleStringProperty productType;
    private transient SimpleStringProperty productName;
    private transient SimpleStringProperty productDescription;
    private transient SimpleDoubleProperty productPrice;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Component(String productType, String productName, String productDescription, double productPrice) {
        // Validator av type, name og price(sjekke om den er 0?) her

        this.productType = new SimpleStringProperty(productType);
        this.productName = new SimpleStringProperty(productName);
        this.productDescription = new SimpleStringProperty(productDescription);
        this.productPrice = new SimpleDoubleProperty(productPrice);
    }
    // Get- og set-metoder

    public String getProductType() {
        return productType.getValue();
    }

    public final void setProductType(String productType) {
        // validator
        this.productType.set(productType);
    }

    public String getProductName() {
        return productName.getValue();
    }

    public final void setProductName(String productName) {
        //validator

        this.productName.set(productName);
    }

    public String getProductDescription() {
        return productDescription.getValue();
    }

    public final void setProductDescription(String productDescription) {
        this.productDescription.set(productDescription);
    }

    public double getProductPrice() {
        return productPrice.getValue();
    }

    public final void setProductPrice(double productPrice) {
        // evt validator
        this.productPrice.set(productPrice);
    }
    // toString
    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s",
                productType.getValue(), productName.getValue(), productDescription.getValue(), productPrice.getValue());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(getProductType()); //denne er null på andre gjennomkjøring . le
        s.writeUTF(getProductName());
        s.writeUTF(getProductDescription());
        s.writeDouble(getProductPrice());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        String productType = s.readUTF();
        String productName = s.readUTF();
        String productDescription = s.readUTF();
        double productPrice =  s.readDouble();

        this.productType = new SimpleStringProperty();
        this.productName = new SimpleStringProperty();
        this.productDescription = new SimpleStringProperty();
        this.productPrice = new SimpleDoubleProperty();

        setProductType(productType);
        setProductName(productName);
        setProductDescription(productDescription);
        setProductPrice(productPrice);
    }


}
