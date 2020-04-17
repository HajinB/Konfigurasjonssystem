package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.programutvikling.component.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SavedPathRegister implements Serializable {
    private transient static final long serialVersionUID = 1;

    private transient ObservableList<String> listOfSavedFilePaths = FXCollections.observableArrayList();

    public ObservableList<String> getListOfSavedFilePaths() {

        return removeDuplicates(listOfSavedFilePaths);
    }

    public void addPathToListOfSavedFilePaths(String s){
        listOfSavedFilePaths.add(s);
    }

    static ObservableList<String> removeDuplicates(ObservableList<String> list) {

        // Store unique items in result.
        ObservableList<String> result = FXCollections.observableArrayList();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<>();

        // Loop over argument list.
        for (String item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
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
