package org.programutvikling.component;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Component implements Serializable, ItemUsable {
    private transient ComponentTypes componentTypes = new ComponentTypes();
    private transient static final long serialVersionUID = 1;

    /*    private transient SimpleStringProperty productType;
    private transient SimpleStringProperty productName;
    private transient SimpleStringProperty productDescription;
    private transient SimpleDoubleProperty productPrice;*/
    transient ComponentValidator componentValidator = new ComponentValidator();
    private transient SimpleStringProperty productType;
    private transient SimpleStringProperty productName;
    private transient SimpleStringProperty productDescription;
    private transient SimpleDoubleProperty productPrice;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Component(String type, String name, String description, double price) {
        // Validator av type, name og price(sjekke om den er 0?) her
        this.productType = new SimpleStringProperty(type);
        this.productName = new SimpleStringProperty(name);
        this.productDescription = new SimpleStringProperty(description);
        this.productPrice = new SimpleDoubleProperty(price);
    }
    // Get- og set-metoder

    public String getProductType() {
        return productType.getValue();
    }

    public void setProductType(String productTypeIn) {
                this.productType.setValue(productTypeIn);
        }

    public void editSetProductType(String productType) {
        if (!ComponentValidator.isProductTypeOk(productType)) {
            throw new IllegalArgumentException("Produkttype er ugyldig");
        } else {
            this.productType.set(productType);
        }
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
        s.writeUTF(getProductType());
        s.writeUTF(getProductName());
        s.writeUTF(getProductDescription());
        s.writeDouble(getProductPrice());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        String type = s.readUTF();
        String name = s.readUTF();
        String description = s.readUTF();
        double price =  s.readDouble();

        this.productType = new SimpleStringProperty();
        this.productName = new SimpleStringProperty();
        this.productDescription = new SimpleStringProperty();
        this.productPrice = new SimpleDoubleProperty();

        setProductType(type);
        setProductName(name);
        setProductDescription(description);
        setProductPrice(price);
    }


}
