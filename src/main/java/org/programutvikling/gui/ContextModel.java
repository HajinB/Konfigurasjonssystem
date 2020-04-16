package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.user.User;
import org.programutvikling.user.UserPreferences;
import org.programutvikling.user.UserRegister;

import java.util.ArrayList;
import java.util.List;

public enum ContextModel {

    INSTANCE;

    //"Singletons are useful to provide a unique source of data or functionality to other Java Objects."
    //https://stackoverflow.com/questions/6059778/store-data-in-singleton-classes
    //todo: ContextModel er en singleton som lagrer alle objekter, som skal være mulig å aksesse fra alle controllers
    // - altså det er innom denne classen (som er oprettet EN gang, og bare en gang) -
    SavedPathRegister savedPathRegister;
    private ComponentRegister componentRegister;
    private ComputerRegister computerRegister;
    private ArrayList<Object> objects = new ArrayList<>();
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");

    //https://myfolderapp.wordpress.com/2010/04/12/singleton-in-the-javafx/
    //https://en.wikipedia.org/wiki/Singleton_pattern
    //https://stackoverflow.com/questions/12166786/multiple-fxml-with-controllers-share-object

   // private final static ContextModel instance = new ContextModel();
        //https://dzone.com/articles/singleton-in-java
    private ContextModel(){
        FileHandling.openFile(objects, userPreferences.getPathToUser());
        loadObjectsIntoClasses();
    }

    public SavedPathRegister getSavedPathRegister() {
        return savedPathRegister;
    }
    /*
    public ObservableList<String> getObservableListOfSavedFilePaths(){
       ObservableList<Component> observableList = FXCollections.observableArrayList();
       observableList.add(listOfSavedFilePaths);

    }
*/

    public ArrayList<Object> getCurrentObjectList(){
        return objects;
    }

    public ArrayList<Object> getCleanObjectList(){
        if(objects.size()>0){
            objects.clear();
        }
        return objects;
    }

    public void loadComponentRegisterIntoModel(ComponentRegister c){
        componentRegister.appendToList((ComponentRegister) objects.get(0));
        /** SKal man appende til listen når bruker åpner fil?*/
        //componentRegister = (ComponentRegister) objects.get(0);
    }

    public void loadObjectsIntoClasses() {
       // System.out.println(objects.get(0));
       // loadComponentRegisterIntoClass((ComponentRegister) objects.get(0));
        if((objects.size()>0)) {
            componentRegister = (ComponentRegister) (objects.get(0));
            computerRegister = (ComputerRegister) objects.get(1);
        }
    }

    public static ContextModel getInstance() {
        return INSTANCE;
    }

    public ComponentRegister getComponentRegister() {
        return this.componentRegister;
    }

    public ComputerRegister getComputerRegister() {
        return this.computerRegister;
    }

    //her
}
