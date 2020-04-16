package org.programutvikling.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ComponentRegister implements Serializable {
    private transient static final long serialVersionUID = 1;

    private transient ObservableList<Component> componentObservableList = FXCollections.observableArrayList();

    public ArrayList<Object> objectArrayListAdapter() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(componentObservableList);
        return objects;
    }

    public static ArrayList<Component> getReadableList(List nonReadableList) {
        ArrayList<Component> nyListe = new ArrayList<>();
        nyListe.addAll(nonReadableList);
        return nyListe;
    }

    public ObservableList<Component> getObservableRegister() {
        return componentObservableList;
    }

    public List<Component> getRegister() {
        return componentObservableList;
    }

    public void removeAll() {
        componentObservableList.clear();
    }

    public void removeComponent(Component component) {
        String name = component.getProductName();
        String description = component.getProductDescription();
        double price = component.getProductPrice();
        for(int i = 0; i<componentObservableList.size(); i++) {
            if (componentObservableList.get(i).getProductName().equals(component.getProductName())) {
                componentObservableList.remove(i);
            }
        }
    }
    /*
    private void isComponentInList(Component component){
        for(int i = 0; i<componentObservableList.size(); i++) {
        if(componentObservableList.get(i).getProductName().equals(component.getProductName())
                    && componentObservableList.get(i).getProductDescription().equals(component.getProductName() );
    }
    }*/

    private boolean doesNameExist(String name) {
        List<Component> filterByName = filterByProductName(name);
        return (filterByName.size() > 0);
    }


    public ObservableList<Component> filterByProductName(String name) {
        return componentObservableList.stream().
                filter(p -> p.getProductName().toLowerCase().
                        matches(String.format("%s%s%s", ".*", name.toLowerCase(), ".*"))).
                collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public ObservableList<Component> filterByProductType(String name) {
        return componentObservableList.stream().
                filter(p -> p.getProductType().toLowerCase().
                        matches(String.format("%s%s%s",".*", name.toLowerCase(), ".*"))).
                collect(Collectors.toCollection(FXCollections::observableArrayList));
    }


    public void addComponent(Component component) {
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

    public void log() {
        System.out.println(componentObservableList.toString());
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

    public void appendToList(ComponentRegister componentRegister){
        ArrayList<Component> temp = new ArrayList<>();
        componentObservableList.addAll(componentRegister.getRegister());
    }
/*
    public void addList(ArrayList<Object> openObjects) {
       for(Object c : openObjects.get(0).get ){
           addComponent((Component) object);
       }
    }*/
}

