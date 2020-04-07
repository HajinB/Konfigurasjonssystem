package org.programutvikling.computer;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Computer implements Serializable, Saveable {
        //mange computere skal opprettes - så vi har EN computer, som består av et array av components , basically
    // det samme som komponentregister ( computer er ikke en ordentlig datamodell som komponent er) -

    //skal man ha muligheten til å si hvilken plass i arrayet som er hvilken del? altså har rekkefølgen noe å si? man
    // skal jo endre på feltene via tableview - er på en måte lettere med felt, også en metode som har x antall
    // parametre "lagComputer(...)

    double priceTotal;
    private transient ObservableList<Component> componentsThatMakeComputer = FXCollections.observableArrayList();
    private ComponentRegister componentRegister;
    private transient static final long serialVersionUID = 1;
    String type;
    String name;
    String description;
    double price;

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Computer(String type, String name, String description, List components) {
        this.type = type;
        this.name = name;
        this.description = description;
        componentRegister.getRegister().addAll(components);
        this.price = calculatePrice(componentRegister);

    }


    private double calculatePrice(ComponentRegister componentRegister){
        if(componentRegister.getRegister().size()>0){
        for(int i= 0; i<componentRegister.getRegister().size();i++) {
            priceTotal = priceTotal + componentRegister.getRegister().get(3).getPrice();
        }
            //3 er indexen til pris.
    return priceTotal;
        }else{
            return 0;
        }
    }

    public List<Component> getRegister() {
        return componentRegister.getRegister();
    }

    public void removeAll() {
        componentRegister.getRegister().clear();
    }
    }





