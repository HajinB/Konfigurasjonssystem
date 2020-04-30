package org.programutvikling.user;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


public class UserPreferences {

    //denne metoden lager en slags cache/node som persisterer - altså lagres lokalt i en pre-definert mappe - (type
    // OS default "c/user/programfiles. ish?
    private Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
    String pathToUserDefinedDir;
    private String pathToUser;

    //her setter man via konstruktøren en default path, denne går inn i put-metoden til Preferences prefs-objektet
    //altså hvis setpreference via en button blir kjørt, er default det som blir instansiert i konstruktøren
    public UserPreferences(String pathToUser){

        this.pathToUser=prefs.get("userdirectory", "FileDirectory/Database/AppData.jobj");
    }

    public void clearPreferences() throws BackingStoreException {
       // prefs.clear();
        prefs.remove("userdirectory");
    }

    public void setPreference(Stage stage){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory==null){
            return;
        }
        System.out.println(selectedDirectory.getAbsolutePath());
        //setter nøkkelverdien brukeren velger med choosedirectory
        prefs.put("userdirectory", String.valueOf(selectedDirectory.getAbsolutePath())+"/Database/AppData.jobj");

        //henter ut faktisk path og navn til appdata.jobj
        Path defaultPath = Paths.get(("FileDirectory/Database/AppData.jobj"));

        //henter denne cachen
        pathToUser = prefs.get("userdirectory", "FileDirectory");
        System.out.println(pathToUser);
       // this.pathToUser=prefs.get("userdirectory", "FileDirectory/Components/AppData.jobj");

        //preferences lagrer par med verdier, en key og en verdi. - i get setter man en default, hvis man ikke finner
        // nøkkelen..
    }

    public String getStringPathToUser() {
        return prefs.get("userdirectory", "FileDirectory/Database/AppData.jobj");

    }

    public Path getPathToUser() {
        return Paths.get(pathToUser);
    }

    private void setPrefs(){

    }

}
