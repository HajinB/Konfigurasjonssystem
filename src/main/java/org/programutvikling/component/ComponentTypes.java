package org.programutvikling.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ComponentTypes {

    ObservableList<String> concreteTypeListName = FXCollections.observableArrayList();
    ObservableList<String> concreteTypeListFilterName = FXCollections.observableArrayList();
    static String[] preList = {"Kabinett", "Prosessor", "Hovedkort", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm", "Annet"};
    static String[] filterList = {"Ingen filter","Kabinett", "Prosessor", "Hovedkort", "Skjermkort", "Minne", "Harddisk", "Tastatur",
            "Mus",
            "Skjerm", "Annet"};

    public ComponentTypes(){
        concreteTypeListName.addAll(preList);
        concreteTypeListFilterName.addAll(filterList);
        }

        public static String[] getComponentTypesArray(){
            return preList;
        }

    public ObservableList<String> getObservableTypeListName() {
        return concreteTypeListName;
    }

    public ObservableList<String> getObservableTypeListNameForFilter() {
        return concreteTypeListFilterName;
    }
}
