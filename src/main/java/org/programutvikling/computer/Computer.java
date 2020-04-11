package org.programutvikling.computer;

import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ItemUsable;

import java.io.Serializable;
import java.util.List;

public class Computer implements Serializable, ItemUsable {
        //mange computere skal opprettes - så vi har EN computer, som består av et array av components , basically
    // det samme som komponentregister ( computer er ikke en ordentlig datamodell som komponent er) -

    //skal man ha muligheten til å si hvilken plass i arrayet som er hvilken del? altså har rekkefølgen noe å si? man
    // skal jo endre på feltene via tableview - er på en måte lettere med felt, også en metode som har x antall
    // parametre "lagComputer(...)

    double priceTotal;
    //private transient ObservableList<Component> listOfComponents = FXCollections.observableArrayList();
    private ComponentRegister componentRegister;
    private transient static final long serialVersionUID = 1;
    private  String name;
    private  String description;
    private  double price;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Computer(String name, String description, ComponentRegister componentRegister) {
        this.name = name;
        this.description = description;
        assert componentRegister != null;
        this.componentRegister = componentRegister;
        this.price = calculatePrice(componentRegister);
    }

    List getComponentList(){
        return componentRegister.getRegister();
    }


    private double calculatePrice(ComponentRegister componentRegister){
        if(componentRegister.getRegister().size()>0){
        for(int i= 0; i<componentRegister.getRegister().size();i++) {
            priceTotal = priceTotal + componentRegister.getRegister().get(i).getProductPrice();
        }
    return priceTotal;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
            return String.format("%s;%s;%s;%s;%s",
                    getName(), getComponentList(), getDescription(), this.componentRegister.toString());
    }

    public List<Component> getRegister() {
        return componentRegister.getRegister();
    }

    public void removeAll() {
        componentRegister.getRegister().clear();
    }

    public void addComponent(Component parseComponent) {
        componentRegister.addComponent((parseComponent));
    }
}





