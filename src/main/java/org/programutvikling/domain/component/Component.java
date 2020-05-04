package org.programutvikling.domain.component;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.programutvikling.domain.component.io.InvalidComponentFormatException;
import org.programutvikling.domain.component.io.InvalidPriceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Component implements Serializable, ItemUsable, Comparable<Component> {
    private transient static final long serialVersionUID = 1;
    /*    private transient SimpleStringProperty productType;
    private transient SimpleStringProperty productName;
    private transient SimpleStringProperty productDescription;
    private transient SimpleDoubleProperty productPrice;*/
    transient ComponentValidator componentValidator = new ComponentValidator();
    private transient ComponentTypes componentTypes = new ComponentTypes();
    private transient SimpleStringProperty productType;
    private transient SimpleStringProperty productName;
    private transient SimpleStringProperty productDescription;
    private transient SimpleDoubleProperty productPrice;

    public Component(String type, String name, String description, double price) {
        if (!ComponentValidator.isProductNameValid(name)) {
            throw new IllegalArgumentException("Skriv inn produktnavn");
        }
        if (!ComponentValidator.isProductTypeValid(type)) {
            throw new IllegalArgumentException("Velg produkttype");
        }
        if (!ComponentValidator.isProductDescriptionValid(description)) {
            throw new IllegalArgumentException("Skriv inn produktbeskrivelse");
        }
        if (!ComponentValidator.isProductPriceValid(price)) {
            throw new InvalidPriceException();
        }

        this.productType = new SimpleStringProperty(type);
        this.productName = new SimpleStringProperty(name);
        this.productDescription = new SimpleStringProperty(description);
        this.productPrice = new SimpleDoubleProperty(price);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    // Get- og set-metoder

    public String getProductType() {
        return productType.getValue();
    }

    public void setProductType(String productTypeIn) {
        if (!ComponentValidator.isProductTypeValid(productTypeIn)) {
            throw new IllegalArgumentException("Produkttype er ugyldig");
        } else {
            this.productType.set(productTypeIn);
        }
    }

    /*public void editSetProductType(String productType) {
        if (!ComponentValidator.isProductTypeValid(productType)) {
            throw new IllegalArgumentException("Produkttype er ugyldig");
        } else {
            this.productType.set(productType);
        }
    }*/

    public String getProductName() {
        return productName.getValue();
    }

    public final void setProductName(String productName) {
        if (!ComponentValidator.isProductNameValid(productName)) {
            throw new IllegalArgumentException("Produktnavn er tom eller ugyldig");
        } else{

            this.productName.set(productName);
    }
    }

    public String getProductDescription() {
        return productDescription.getValue();
    }

    public final void setProductDescription(String productDescription) {
        if (!ComponentValidator.isProductDescriptionValid(productDescription)) {
            throw new IllegalArgumentException("Produktbeskrivelse kan ikke være tom");
        } else {
            this.productDescription.set(productDescription);
        }
    }

    public double getProductPrice() {
        return productPrice.getValue();
    }

    public final void setProductPrice(double productPrice) {
        if (!ComponentValidator.isProductPriceValid(productPrice)) {
            throw new InvalidPriceException();
        } else {
            this.productPrice.setValue(productPrice);
        }
    }

    public String toStringListView() {
        return productName.getValue() + " " +
                productPrice.getValue() + ",-";
    }

    // toString
    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s", productType.getValue(), productName.getValue(),
                productDescription.getValue(), productPrice.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        // TODO denne metoden er for å kunne fjerne duplikater med HashSet - må override equals og hashcode metodene
        //  til Object for å ha kontroll på de.
        if (obj instanceof Component) {
            Component temp = (Component) obj;
            return this.getProductName().equals(temp.getProductName())
                    && this.getProductType().equals(temp.getProductType())
                    && this.getProductDescription().equals(temp.getProductDescription());
            //bruker ikke pris her, for å sammenligne to komponenter i en liste.
        }
        return false;
    }

    @Override
    public int hashCode() {
        // returnerer summen av hashcoden til hvert enkelt felt - blir en unik kode for hvert objekt.
        return (this.getProductType().hashCode() + this.getProductName().hashCode() + this.getProductDescription().hashCode());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(getProductType());
        s.writeUTF(getProductName());
        s.writeUTF(getProductDescription());
        s.writeDouble(getProductPrice());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException, InvalidComponentFormatException {
        String type = s.readUTF();
        String name = s.readUTF();
        String description = s.readUTF();
        double price = s.readDouble();

        this.productType = new SimpleStringProperty();
        this.productName = new SimpleStringProperty();
        this.productDescription = new SimpleStringProperty();
        this.productPrice = new SimpleDoubleProperty();

        if (ComponentValidator.isComponentValid(type, name, description, price)) {
            setProductType(type);
            setProductName(name);
            setProductDescription(description);
            setProductPrice(price);
        } else {
            throw new InvalidComponentFormatException("Komponent er i ikke gyldig format");
        }
    }


    @Override
    public int compareTo(Component cIn) {
        String name1 = cIn.getProductName();
        String name2 = this.getProductName();
        if ((name1.compareTo(name2)) > 0
                && cIn.getProductType().compareTo(this.getProductType()) > 0
                && cIn.getProductDescription().compareTo(this.getProductDescription()) > 0) return 1;
        else return 0;
    }


}
