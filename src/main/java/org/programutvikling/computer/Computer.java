package org.programutvikling.computer;

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

public class Computer extends Component implements Serializable {
        //mange computere skal opprettes - så vi har EN computer, som består av et array av components , basically
    // det samme som komponentregister ( computer er ikke en ordentlig datamodell som komponent er) -

    //skal man ha muligheten til å si hvilken plass i arrayet som er hvilken del? altså har rekkefølgen noe å si? man
    // skal jo endre på feltene via tableview - er på en måte lettere med felt, også en metode som har x antall
    // parametre "lagComputer(...)

    double priceTotal;
    private transient ObservableList<Component> computerRegister = FXCollections.observableArrayList();
    private transient static final long serialVersionUID = 1;

    public Computer(String type, String name, String description, double price) {
        super(type, name, description, price);
    }


    double calculatePrice(ComputerRegister computerRegister){
        for(int i= 0; i<computerRegister.getRegister().size();i++) {
            priceTotal = priceTotal + computerRegister.getRegister().get(3).getPrice();
        }
            //3 er indexen til pris.
    return priceTotal;
    }


    public List<Component> getRegister() {
        return computerRegister;
    }

    public void removeAll() {
        computerRegister.clear();
    }

    public void addComponent(Component component) {
        computerRegister.add(component);
    }


    /*
    @Override
    public String toString(){
        String melding = "";

        for(int i = 0; i<computerRegister.size(); i++){
            melding = "Type: "+getRegister().get(i).getType()+"\n" +
                        "Name: " +getRegister().get(i).getName()+"\n"+
                    "Description: " +getRegister().get(i).getName()+"\n"+
                    "Price: " + getRegister().get(i).getPrice()+"\n";
        }
        return melding;
    }
*/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Component c : computerRegister) {
            sb.append(c.toString());
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
    public void log(){
        System.out.println(computerRegister.toString());
    }



    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(computerRegister));
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        List<Component> list = (List<Component>) inputStream.readObject();
        computerRegister.addAll(list);
    }




    }





