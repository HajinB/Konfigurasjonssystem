package org.programutvikling.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.programutvikling.gui.utility.RegisterUtility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import static java.util.stream.Collectors.toCollection;


public class ComponentRegister implements Serializable {
    private transient static final long serialVersionUID = 1;

    private transient ObservableList<Component> componentObservableList = FXCollections.observableArrayList();

    public static ArrayList<Component> getReadableList(List nonReadableList) {
        ArrayList<Component> nyListe = new ArrayList<>();
        nyListe.addAll(nonReadableList);
        return nyListe;
    }

    public boolean doesComponentAlreadyExist(Component newComponent) {
        return ComponentValidator.isComponentInRegisterThenReturnIt(newComponent, this) !=null;
    }

    public ArrayList<Object> objectArrayListAdapter() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(componentObservableList);
        return objects;
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
        String type = component.getProductType();
        double price = component.getProductPrice();

        for (int i = 0; i < componentObservableList.size(); i++) {
            if (isNameAndDescriptionInList(type, name, description, price, i)) {
                componentObservableList.remove(i);
            }
        }
    }

    private boolean isNameAndDescriptionInList(String type, String name, String description, double price, int i) {
        return componentObservableList.get(i).getProductName().equals(name) &&
                componentObservableList.get(i).getProductType().equals(type) &&
                componentObservableList.get(i).getProductDescription().equals(description) &&
                componentObservableList.get(i).getProductPrice() == price ;
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
                filter(component -> component.getProductName().toLowerCase().matches(String.format("%s%s%s", ".*",
                        name.toLowerCase(), ".*"))).
                collect(toCollection(FXCollections::observableArrayList));
    }

    public ObservableList<Component> filterByProductType(String type) {
        return componentObservableList.stream().
                filter(p -> p.getProductType().toLowerCase().
                        matches(String.format("%s%s%s", ".*", type.toLowerCase(), ".*"))).
                collect(toCollection(FXCollections::observableArrayList));
    }


    public void addComponent(Component component) {
        ComponentValidator.isComponentValid(component);
        componentObservableList.add(component);
    }

    public void attachTableView(TableView<Component> tv) {
        if (!componentObservableList.isEmpty())
            tv.setItems(componentObservableList);
    }


    public String toStringListView() {

        String melding = "";
        for(int i = 0; i<componentObservableList.size();i++) {
            melding =
                    componentObservableList.get(i).getProductName() +" "+ componentObservableList.get(i).getProductPrice() + ",-";
        }
        return melding;
    }

    @Override
    public String toString(){
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

    public void appendToList(ComponentRegister componentRegister) {
        ArrayList<Component> temp = new ArrayList<>();
        componentObservableList.addAll(componentRegister.getRegister());
    }

    public void removeDuplicates() {
        RegisterUtility.removeDuplicates(componentObservableList);
    }


    public int countByType(String s) {
        List<Component> list = filterByProductType(s);
        return list.size();
    }



/*
    public ObservableList<Component> getObservableRegisterForListView() {

        //wrap listen i en ny klasse - som har en egen tostring (?)

    }*/
}
/*
        // Clear the list
        this.getRegister().clear();
        // add the elements of set
        // with no duplicates to the list
        this.getRegister().addAll(seen);
        System.out.println(this.getRegister().size());
        System.out.println(newList.size());

        // return the list
    }
  */




/*
    public void addList(ArrayList<Object> openObjects) {
       for(Object c : openObjects.get(0).get ){
           addComponent((Component) object);
       }
    }*/


