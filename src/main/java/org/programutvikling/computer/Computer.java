package org.programutvikling.computer;

import org.programutvikling.component.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Computer implements Serializable {
        //mange computere skal opprettes - så vi har EN computer, som består av et array av components , basically
    // det samme som komponentregister ( computer er ikke en ordentlig datamodell som komponent er) -

    //skal man ha muligheten til å si hvilken plass i arrayet som er hvilken del? altså har rekkefølgen noe å si? man
    // skal jo endre på feltene via tableview - er på en måte lettere med felt, også en metode som har x antall
    // parametre "lagComputer(...)

    double priceTotal;
    private transient ArrayList<Component> computerRegister = new ArrayList<>();
    private transient static final long serialVersionUID = 1;

    Computer(){

    }

    double calculatePrice(){
        for(int i= 0; i<computerRegister.size();i++) {
            priceTotal = priceTotal + computerRegister.get(3).getProductPrice();
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





