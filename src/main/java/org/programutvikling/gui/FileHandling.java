package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.programutvikling.component.io.FileOpener;
import org.programutvikling.component.io.FileSaver;
import org.programutvikling.component.io.FileSaverTxt;
import org.programutvikling.component.io.InvalidComponentFormatException;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.UserPreferences;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//todo: må lage metode som lagrer path til ConfigMain i jobj - slik at den er brukervalgt (?)

public class FileHandling {
    //private static UserPreferences userPreferences;
    private static UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");

    static void saveFileTxt(ArrayList<Object> register, Path directoryPath) {
        if (directoryPath != null) {
            FileSaver saver = new FileSaverTxt();
            try {
                saver.save(register, directoryPath);
                Dialog.showSuccessDialog("Registeret ble lagret!");
            } catch (IOException e) {
                Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
            }
        }
    }

    public static void saveFileAs(String chosenPath) throws IOException {
        Path path = Paths.get(chosenPath);
        ArrayList<Object> objectsToSave = FileUtility.createObjectList(ContextModel.INSTANCE.getComponentRegister(),
                null, ContextModel.INSTANCE.getSavedPathRegister());//todo her er det kanskje muilgheter for STOR BUG - setter null in i
        // objectlisten...
        System.out.println("dette prøver man å lagre:"+objectsToSave);
        Path pathAppend = Paths.get(path + ".jobj");
        saveFile(objectsToSave, pathAppend);
    }

    static void saveFile(ArrayList<Object> objectsToSave, Path directoryPath) throws IOException {
        if (directoryPath != null) {
            FileSaver saver;
            saver = FileUtility.getFileSaver(directoryPath.toString());

            //kanskje man skal legge til savedpathregister et annet sted?
            ContextModel.INSTANCE.getSavedPathRegister().addPathToListOfSavedFilePaths(directoryPath.toString());
            tryToSave(objectsToSave, directoryPath, saver);
        }
    }

    public static void saveFileAuto(ArrayList<Object> register, Path directoryPath) throws IOException {
        if (directoryPath != null) {
            FileSaver saver = null;
            saver = FileUtility.getFileSaver(directoryPath.toString());
            tryToSave(register, directoryPath, saver);
        }
    }

    private static void tryToSave(ArrayList<Object> register, Path directoryPath, FileSaver saver) {
        try {
            saver.save(register, directoryPath);
        } catch (IOException e) {
            Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
        }
    }

    static void openFile(ArrayList<Object> objects, String selectedPath) {
        openObjects(objects, selectedPath);
    }

    public static ArrayList<Object> openObjects(ArrayList<Object> register, String selectedPath) {
        FileOpener opener = FileUtility.getFileOpener(selectedPath);
        ArrayList<Object> objectsLoaded = new ArrayList<>();
        if (opener != null && selectedPath != null) {
            try {
                Path path = Paths.get(selectedPath);
                objectsLoaded = opener.open(register, path);
                /**unngå addAll her, da kjøres opener mange ganger xD */
                //objectsLoaded.addAll(opener.open(register, path));
                //System.out.println(objectsLoaded.size() + " er størrelsen på lista inn");
                //System.out.println("etter opener" + objectsLoaded.size());
            } catch (IOException e) {

                //Here are some cases which result in IOException.
                //
                //Reading from a closed inputstream
                //https://stackoverflow.com/questions/13216148/java-what-throws-an-ioexception
                System.out.println(Arrays.toString(e.getStackTrace()));
                Dialog.showErrorDialog("Åpning av filen feilet. Grunn: " + e.getMessage());
            }
        } else {
            Dialog.showErrorDialog("opener eller path er null;");
            return null;
        }
        return objectsLoaded;
    }

    public static ArrayList<Object> openSelectedComputerTxtFiles(ArrayList<Object> objects, String path) {
        //String path = getFilePathFromFileChooser(stage);
        return openObjects(objects, path);
    }

    public void saveAll() throws IOException {
        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objects = FileUtility.createObjectList(ContextModel.INSTANCE.getComponentRegister(),
                ContextModel.INSTANCE.getComputerRegister(), ContextModel.INSTANCE.getSavedPathRegister());
        FileHandling.saveFileAuto(objects,
                Paths.get(userPreferences.getStringPathToUser()));
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public static String getPathToUser() {
        return userPreferences.getStringPathToUser();
    }




}
