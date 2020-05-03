package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.programutvikling.gui.utility.RegisterLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SavedPathRegister implements Serializable {
    private transient static final long serialVersionUID = 1;

    private transient ObservableList<String> listOfSavedFilePaths = FXCollections.observableArrayList();

    public ObservableList<String> getListOfSavedFilePaths() {
        return RegisterLogic.getDuplicateFreeList(listOfSavedFilePaths);
    }

    public void addPathToListOfSavedFilePaths(String s){
        listOfSavedFilePaths.add(s);
    }

/*
    public void removeDuplicates(){
        listOfSavedFilePaths = listOfSavedFilePaths.stream().distinct().collect(Collectors.toList());

    }
*/
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(listOfSavedFilePaths));
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {

        List<String> list = (List<String>) inputStream.readObject();
        listOfSavedFilePaths = FXCollections.observableArrayList();
        listOfSavedFilePaths.addAll(list);
    }
}
