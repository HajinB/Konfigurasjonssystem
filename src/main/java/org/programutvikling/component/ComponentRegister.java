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
        tv.setItems(componentRegister);
    }

    public String toString(){
        StringBuilder melding = new StringBuilder();
        for(Component k:componentRegister){
            melding.append(k);
        }
        return melding.toString();
    }

    public void log(){
        System.out.println(componentRegister.toString());
    }

    private void writeList(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(componentRegister));
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        List<Component> list = (List<Component>) s.readObject();
        componentRegister = FXCollections.observableArrayList();
        componentRegister.addAll(list);
    }


}

