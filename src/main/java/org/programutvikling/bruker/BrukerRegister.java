package org.programutvikling.bruker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class BrukerRegister {
    private ObservableList<Bruker> brukerRegister = FXCollections.observableArrayList();

    public List<Bruker> getRegister() {
        return brukerRegister;
    }

    public void removeAll() {
        brukerRegister.clear();
    }

    public void addBruker(Bruker bruker) {
        brukerRegister.add(bruker);
    }
}