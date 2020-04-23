package org.programutvikling.gui.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;

public class RegisterLogic {

    public static boolean containsDuplicates(ObservableList<String> list) {
        ObservableList<String> result = makeUnique(list);
        return result.size() == list.size();
    }

    public static ObservableList<String> removeDuplicates(ObservableList<String> list) {
        ObservableList<String> result = makeUnique(list);

        return result;
    }

    private static ObservableList<String> makeUnique(ObservableList<String> list) {

        ObservableList<String> result = FXCollections.observableArrayList();
        HashSet<String> set = new HashSet<>();
        for (String item : list) {
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }
}
