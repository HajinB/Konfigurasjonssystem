package org.programutvikling.computer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ItemUsable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Computer implements Serializable, ItemUsable {
    //mange computere skal opprettes - så vi har EN computer, som består av et array av components , basically
    // det samme som komponentregister ( computer er ikke en ordentlig datamodell som komponent er) -

    //skal man ha muligheten til å si hvilken plass i arrayet som er hvilken del? altså har rekkefølgen noe å si? man
    // skal jo endre på feltene via tableview - er på en måte lettere med felt, også en metode som har x antall
    // parametre "lagComputer(...)

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


    /**
     * hvordan skal formatet på txtfilen til computer være? - tror ikke man bør lagre totalpris, da dette er noe man
     * kan regne ut lett (i excel) .
     */
    public String toStringListView() {
        String melding = "";
        for (int i = 0; i < getComponentList().size(); i++) {
            melding =
                    getComponentRegister().getRegister().get(i).getProductName() + " " + getComponentRegister().getRegister().get(i).getProductPrice() + ",-";
        }
        return melding;
    }

    public void removeAll() {
        componentRegister.getRegister().clear();
    }

    public void addComponent(Component parseComponent) {
        componentRegister.addComponent((parseComponent));
    }

    public double calculatePrice() {
        if (this.componentRegister.getRegister().size() > 0) {
            Double priceTotal = 0.0;
            for (int i = 0; i < this.componentRegister.getRegister().size(); i++) {
                priceTotal = priceTotal + componentRegister.getRegister().get(i).getProductPrice();
            }
            return priceTotal;
        } else {
            return 0;
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
        return getProductName() + "\n"
                + getProductPrice() + melding;
        //return this.componentRegister.toString();
        //return Integer.toString(this.hashCode());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeUTF(getProductName());
        s.writeObject(new ArrayList<>(componentRegister.getRegister()));
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {

        String name = s.readUTF();
        this.productName = new SimpleStringProperty();
        setProductName(name);
        List<Component> list = (List<Component>) s.readObject();
        //componentRegister.getRegister().clear();
        componentRegister = new ComponentRegister();
        componentRegister.getRegister().addAll(list);


    }


}





