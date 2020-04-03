package org.programutvikling.komponent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KomponentRegister implements Serializable {

    private transient ObservableList<Komponent> komponentRegister = FXCollections.observableArrayList();

    public List<Komponent> getRegister() {
        return komponentRegister;
    }

    public void removeAll() {
        komponentRegister.clear();
    }

    public void addKomponent(Komponent komponent) {
        komponentRegister.add(komponent);
    }

    public void attachTableView(TableView tv) {
        tv.setItems(komponentRegister);
    }

    public String toString(){
        String melding = "";

        for(Komponent k:komponentRegister){
            melding = melding + k;
        }
        return melding;

    }


    public void log(){
        System.out.println(komponentRegister.toString());
    }

    private void writeList(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(komponentRegister));
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        List<Komponent> list = (List<Komponent>) s.readObject();
        komponentRegister = FXCollections.observableArrayList();
        komponentRegister.addAll(list);
    }


}
