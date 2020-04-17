package org.programutvikling.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ComponentTypes {

    ObservableList<String> concreteTypeListName = FXCollections.observableArrayList();
    ObservableList<String> concreteTypeListFilterName = FXCollections.observableArrayList();
    static String[] preList = {"Prosessor", "Skjermkort", "Minne", "Harddisk", "SSD", "Tastatur", "Mus", "Skjerm", "Annet"};
    static String[] filterList = {"Ingen filter","Prosessor", "Skjermkort", "Minne", "Harddisk", "SSD", "Tastatur",
            "Mus",
            "Skjerm", "Annet"};
    /*
                                                   <String fx:value="" />
                                                   <String fx:value="" />
                                                   <String fx:value="Minne" />
                                                   <String fx:value="" />
                                                   <String fx:value="Tastatur" />
                                                   <String fx:value="Mus" />
                                                   <String fx:value="Skjerm" />
                                                </FXCollections>
                                             </items>
    */
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
