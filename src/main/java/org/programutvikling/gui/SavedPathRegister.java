package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SavedPathRegister {

    private transient ObservableList<String> listOfSavedFilePaths = FXCollections.observableArrayList();

    public ObservableList<String> getListOfSavedFilePaths() {
        return listOfSavedFilePaths;
    }

    public void addPathToListOfSavedFilePaths(String s){
        listOfSavedFilePaths.add(s);
    }
}
