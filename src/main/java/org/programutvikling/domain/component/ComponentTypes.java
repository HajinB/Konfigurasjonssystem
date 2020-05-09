package org.programutvikling.domain.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ComponentTypes {

    ObservableList<String> concreteTypeListName = FXCollections.observableArrayList();
    ObservableList<String> concreteTypeListFilterName = FXCollections.observableArrayList();
    final static String[] preList = {"Kabinett", "Prosessor", "Hovedkort", "Skjermkort", "Minne", "Harddisk",
            "Tastatur", "Mus", "Skjerm", "Annet"};
   final static String[] filterList = {"Ingen filter","Kabinett", "Prosessor", "Hovedkort", "Skjermkort", "Minne",
            "Harddisk", "Tastatur",
            "Mus",
            "Skjerm", "Annet"};

    public ComponentTypes(){
        concreteTypeListName.addAll(preList);
        concreteTypeListFilterName.addAll(filterList);
        }

        public static String[] getComponentTypesArray(){
            return preList;
        }

    public static ArrayList<String> getNeededComponentTypeNames() {
        ArrayList<String> needed = new ArrayList<>();
        needed.add(preList[0]);
        needed.add(preList[1]);
        needed.add(preList[2]);
        needed.add(preList[3]);
        needed.add(preList[4]);
        needed.add(preList[5]);
        return needed;
    }

    public ObservableList<String> getObservableTypeListName() {
        return concreteTypeListName;
    }

    public ObservableList<String> getObservableTypeListNameForFilter() {
        return concreteTypeListFilterName;
    }
}
