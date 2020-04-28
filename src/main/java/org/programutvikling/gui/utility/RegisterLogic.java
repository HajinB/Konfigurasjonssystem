package org.programutvikling.gui.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RegisterLogic {

    public static boolean containsDuplicates(ObservableList<String> list) {
        ObservableList<String> result = (ObservableList<String>) makeListUnique(list);
        return result.size() == list.size();
    }

    public static ObservableList<String> getDuplicateFreeList(ObservableList<String> list) {
        ArrayList<String> resultAL = new ArrayList<> (makeListUnique(list));
        ObservableList<String> resultOL = FXCollections.observableArrayList();
        resultOL.addAll(resultAL);
        return resultOL;
    }

    public static ArrayList<String> getDuplicateFreeList(ArrayList<String> list) {
        ArrayList<String> result = (ArrayList<String>) makeListUnique(list);
        return result;
    }

    private static List<String> makeListUnique(List<String> list) {
        //fungerer denne??
        List<String> result = new ArrayList<>(list);
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
