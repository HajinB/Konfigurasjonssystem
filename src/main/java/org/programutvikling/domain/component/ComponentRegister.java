package org.programutvikling.domain.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.utility.RegisterUtility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ComponentRegister implements Serializable, Clickable {
    private transient static final long serialVersionUID = 1;

    private transient ObservableList<Component> componentObservableList = FXCollections.observableArrayList();

    public ObservableList<Component> getObservableRegister() {
        return componentObservableList;
    }

    public List<Component> getRegister() {
        return componentObservableList;
    }

    public void removeAll() {
        componentObservableList.clear();
    }

    public ObservableList<Component> createListByType(String type){
        ObservableList<Component> componentTypeList = FXCollections.observableArrayList();
        for(Component c: componentObservableList){
            if(type.equalsIgnoreCase(c.getProductType())){
                componentTypeList.add(c);
            }
        }
        return componentTypeList;
    }

    public void addComponent(Component component) {
        ComponentValidator.isComponentValid(component);
        componentObservableList.add(component);
    }

    public void attachTableView(TableView<Component> tv) {
        if (!componentObservableList.isEmpty())
            tv.setItems(componentObservableList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Component c : componentObservableList) {
            sb.append(c.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(componentObservableList));
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        List<Component> list = (List<Component>) inputStream.readObject();
        componentObservableList = FXCollections.observableArrayList();
        componentObservableList.addAll(list);
    }

    public void removeDuplicates() {
        //skulle ønske det var enkelt å bruke Set istedenfor obsvablelist
        ArrayList<Component> al = (ArrayList<Component>) RegisterUtility.removeDuplicates(componentObservableList);
        this.getRegister().clear();
        this.getRegister().addAll(al);
    }

    public int countByType(String s) {
        List<Component> list = createListByType(s);
        return list.size();
    }
}


