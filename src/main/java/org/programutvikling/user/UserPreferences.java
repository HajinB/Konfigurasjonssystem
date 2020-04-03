package org.programutvikling.user;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;


public class UserPreferences {

    private Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
    String pathToUserDefinedDir;
    private String pathToUser;
    //denne metoden lager en slags cache som persisterer - altså lagres lokalt.

    public UserPreferences(String pathToUser){
        this.pathToUser=pathToUser;
    }

    /* String pathToUserDefinedDir = prefs.get("userdirectory", "FileDirectory");
    Path pathtoUser = Paths.get(pathToUserDefinedDir+"/ComponentList.jobj");
    */

    public void setPreference(Stage stage){

        //pathToUser = pathToUserDefinedDir+"/Components/ComponentList.jobj";
        DirectoryChooser directoryChooser = new DirectoryChooser();

        File selectedDirectory = directoryChooser.showDialog(stage);

        //setter nøkkelverdi
        prefs.put("userdirectory", String.valueOf(selectedDirectory));
        //henter ut faktisk path og navn til configfilen.
        //String pathToUserDefinedDir = "";

        Path defaultPath = Paths.get(("FileDirectory/Components/ComponentList.jobj"));

        //henter denne cachen
        //pathToUser = prefs.get("userdirectory", "FileDirectory");

        this.pathToUser=prefs.get("userdirectory", "FileDirectory");

        //preferences lagrer par med verdier, en key og en verdi. - i get setter man en default, hvis man ikke finner
        // nøkkelen..

    }

    public String getPathToUser() {
        return pathToUser;
    }
}
