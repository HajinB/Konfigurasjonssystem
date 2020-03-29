package org.programutvikling.komponent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
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
}
