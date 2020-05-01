package org.programutvikling.domain.user;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

        this.pathToUser=prefs.get("userdirectory", "AppFiles/Database/AppData.jobj");
    }

    public void clearPreferences() throws BackingStoreException {
        prefs.clear();
        prefs.remove("userdirectory");

    }

    public void setPreference(Stage stage) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        //selectedDirectory.getParentFile().mkdirs();
       /* Path tempPath = Files.createTempDirectory("AppFiles/Database/AppDataBackup.jobj");
        Path dirToCreate = tempPath.resolve("AppFiles/Database/AppDataBackup.jobj");
        System.out.println("dir to create: " + dirToCreate);
        System.out.println("dir exits: " + Files.exists(dirToCreate));
        //creating directory */
       /* Path directory = Files.createDirectory(dirToCreate);
        System.out.println("directory created: " + directory);
        System.out.println("dir created exits: " + Files.exists(directory));*/
        if(!selectedDirectory.exists()){
            try {
                clearPreferences();
                pathToUser="AppFiles/Database/AppData.jobj";
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
            return;
        }
        System.out.println(selectedDirectory.getAbsolutePath());
        //setter nøkkelverdien brukeren velger med choosedirectory
        prefs.put("userdirectory", selectedDirectory.getAbsolutePath() +"AppFiles/Database/AppData.jobj");

        //henter ut faktisk path og navn til appdata.jobj
        Path defaultPath = Paths.get(("AppFiles/Database/AppDataBackup.jobj"));
        //henter denne cachen
        pathToUser = prefs.get("userdirectory", "AppFiles/Database/AppData.jobj");
        System.out.println(pathToUser);
       // this.pathToUser=prefs.get("userdirectory", "AppFiles/Components/AppDataBackup.jobj");

        //preferences lagrer par med verdier, en key og en verdi. - i get setter man en default, hvis man ikke finner
        // nøkkelen..
    }
    public String getStringPathToUser() {
        return prefs.get("userdirectory", "AppFiles/Database/AppData.jobj");

    }

    public Path getPathToUser() {
        return Paths.get(pathToUser);
    }

    private void setPrefs(){

    }

}
