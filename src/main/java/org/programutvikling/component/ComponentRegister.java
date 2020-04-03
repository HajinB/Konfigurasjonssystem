package org.programutvikling.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;

public class ComponentRegister implements Serializable {
    private transient ObservableList<Component> componentRegister = FXCollections.observableArrayList();

    public List<Component> getRegister() {
        return componentRegister;
    }

    public void removeAll() {
        componentRegister.clear();
    }

    public void addComponent(Component component) {
        componentRegister.add(component);
    }
}
