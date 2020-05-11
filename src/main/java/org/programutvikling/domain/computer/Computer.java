package org.programutvikling.domain.computer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.utility.Item;
import org.programutvikling.gui.logic.RegisterLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Computer implements Serializable, Item {
    private transient static final long serialVersionUID = 1;
    private transient ObservableList<Component> listOfComponents = FXCollections.observableArrayList();
    private ComponentRegister componentRegister = new ComponentRegister();
    private transient SimpleStringProperty productName;

    public Computer(String name) {
        // this.productPrice = calculatePrice();
        this.productName = new SimpleStringProperty(name);
    }

    public double getProductPrice() {
        return calculatePrice();
    }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public ComponentRegister getComponentRegister() {
        return componentRegister;
    }

    List getComponentList() {
        return componentRegister.getRegister();
    }

    public void removeAll() {
        componentRegister.getRegister().clear();
    }

    public void addComponent(Component parseComponent) {
        componentRegister.addComponent((parseComponent));
    }

    public double calculatePrice() {
        if (this.componentRegister.getRegister().size() > 0) {
            Double priceTotal = 0.00;
            for (int i = 0; i < this.componentRegister.getRegister().size(); i++) {
                priceTotal = priceTotal + componentRegister.getRegister().get(i).getProductPrice();
            }
            return priceTotal;
        } else {
            return 0.00;
        }
    }

    @Override
    public String toString() {
        //String melding =
        String melding = "";
        for (int i = 0; i < componentRegister.getRegister().size(); i++) {
            melding =
                    componentRegister.toString();
        }
        return melding;
    }
/*
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeUTF(getProductName());
        s.writeObject(new ArrayList<Component>(componentRegister.getRegister()));
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        if (s != null) {
            String name = s.readUTF();
            this.productName = new SimpleStringProperty();
            setProductName(name);
            ArrayList<Component> list = (ArrayList<Component>) s.readObject();
            //componentRegister.getRegister().clear();
            componentRegister = new ComponentRegister();
            componentRegister.getRegister().addAll(list);
        }
    }*/

    public ArrayList<String> createSortedUniqueComponentTypeList() {
        ArrayList<String> namesOfComponentsInList = new ArrayList<>();

        for (Component c : this.getComponentRegister().getRegister()) {
            namesOfComponentsInList.add(c.getProductType());
        }
        Collections.sort(namesOfComponentsInList);

        return (ArrayList<String>) RegisterLogic.getDuplicateFreeList(namesOfComponentsInList);
    }
}





