package org.programutvikling.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComponentRegister implements Serializable {
    private transient static final long serialVersionUID = 1;

    private transient ObservableList<Component> componentRegister = FXCollections.observableArrayList();

    public List<Component> getRegister() {
        return componentRegister;
    }

    public void removeAll() {
        componentRegister.clear();
    }

    public void addComponent(Component component) {
        componentRegister.add(component);
    }

    public void attachTableView(TableView tv) {
        if (!componentRegister.isEmpty())
        tv.setItems(componentRegister);
    }

    /*
    @Override
    public String toString(){
        String melding = "";

        for(int i = 0; i<componentRegister.size(); i++){
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
        for(Component c : componentRegister) {
            sb.append(c.toString());
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
    public void log(){
        System.out.println(componentRegister.toString());
    }

    public static ArrayList<Component> getReadableList(List nonReadableList){

        ArrayList<Component> nyListe = new ArrayList<>();

        nyListe.addAll(nonReadableList);
        return nyListe;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(componentRegister));
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        List<Component> list = (List<Component>) inputStream.readObject();
        componentRegister = FXCollections.observableArrayList();
        componentRegister.addAll(list);
    }
}

