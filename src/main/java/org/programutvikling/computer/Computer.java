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

    //private transient ObservableList<Component> listOfComponents = FXCollections.observableArrayList();
    private ComponentRegister componentRegister = new ComponentRegister();
    private transient static final long serialVersionUID = 1;
    private  String productNameProperty;
    private double productPriceProperty;

    public double getComputerPrice() {
        return calculatePrice();
    }

    public Computer(String name){
       // this.productPrice = calculatePrice();
        this.productNameProperty = name;
    }

    public void setProductNameProperty(String productNameProperty) {
        this.productNameProperty = productNameProperty;
    }

    public String getProductNameProperty() {
        return productNameProperty;
    }

    public ComponentRegister getComponentRegister() {
        return componentRegister;
    }

    List getComponentList(){
        return componentRegister.getRegister();
    }


    /**hvordan skal formatet på txtfilen til computer være? - tror ikke man bør lagre totalpris, da dette er noe man
     * kan regne ut lett (i excel) .*/
    public String toStringListView() {
        String melding = "";
        for(int i = 0; i<getComponentList().size();i++) {
            melding =
                    getComponentRegister().getRegister().get(i).getProductName() +" "+ getComponentRegister().getRegister().get(i).getProductPrice() + ",-";
        }
        return melding;
    }

    public void removeAll() {
        componentRegister.getRegister().clear();
    }

    public void addComponent(Component parseComponent) {
        componentRegister.addComponent((parseComponent));
    }

    public double calculatePrice(){
        if(this.componentRegister.getRegister().size()>0){
            Double priceTotal = 0.0;
            for(int i= 0; i<this.componentRegister.getRegister().size();i++) {
                priceTotal = priceTotal + componentRegister.getRegister().get(i).getProductPrice();
            }
            return priceTotal;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {

            return this.componentRegister.toString();
    }
}





