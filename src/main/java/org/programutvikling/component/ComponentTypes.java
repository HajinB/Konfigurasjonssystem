package org.programutvikling.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ComponentTypes {

    ObservableList<String> concreteTypeListName = FXCollections.observableArrayList();
    String[] preList = {"Prosessor", "Skjermkort", "Minne", "Harddisk", "SSD", "Tastatur", "Mus", "Skjerm"};
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
        }

        public String[] getComponentTypesArray(){
            return preList;
        }

    public ObservableList<String> getConcreteTypeListName() {
        return concreteTypeListName;
    }
}
