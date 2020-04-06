package org.programutvikling.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ComponentTypes {

    ObservableList<String> concreteTypeListName = FXCollections.observableArrayList();

    public ComponentTypes(){
        String[] preList = {"Processor", "Motherboard", "Ram", "SSD", "CPU", "Monitor", "Other"};
        concreteTypeListName.addAll(preList);
        }

    public ObservableList<String> getConcreteTypeListName() {
        return concreteTypeListName;
    }
}
