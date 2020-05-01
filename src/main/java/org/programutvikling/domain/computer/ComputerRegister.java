package org.programutvikling.domain.computer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComputerRegister implements Serializable {
     //ObservableList<Component> componentObservableList = FXCollections.observableArrayList();

    private transient ObservableList<Computer> computerRegisterList = FXCollections.observableArrayList();

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(computerRegisterList));
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {

        List<Computer> list = (List<Computer>) inputStream.readObject();
        computerRegisterList = FXCollections.observableArrayList();
        computerRegisterList.addAll(list);
    }

    public void addComputer(Computer computer) {
            computerRegisterList.add(computer);
        }

    public ObservableList<Computer> getObservableRegister() {
        return computerRegisterList;
    }
}
