package org.programutvikling.gui;

import org.programutvikling.component.io.FileOpener;
import org.programutvikling.component.io.FileSaver;
import org.programutvikling.component.io.FileSaverTxt;
import org.programutvikling.computer.Computer;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.UserPreferences;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//todo: må lage metode som lagrer path til ConfigMain i jobj - slik at den er brukervalgt (?)

public class FileHandling {
    //private static UserPreferences userPreferences;
    private static UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/AppData.jobj");

    public static void saveFileTxt(Computer computer, Path directoryPath) {
        if (directoryPath != null) {
            FileSaverTxt saver = new FileSaverTxt();
            try {
                saver.save(computer, directoryPath);
                Dialog.showSuccessDialog("Registeret ble lagret!");
            } catch (IOException e) {
                Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
            }
        }
    }

    public static void saveFileAs(String chosenPath) throws IOException {
        if (chosenPath == null) {
            return;
        }
        Path path = Paths.get(chosenPath);
        ArrayList<Object> objectsToSave = FileUtility.createObjectList(ContextModel.INSTANCE.getComponentRegister(),
                null, ContextModel.INSTANCE.getSavedPathRegister(), null, null);
        //todo kanskje det er skummelt å sette null inn i objectlisten noen ganger (?)
        System.out.println("Dette prøver man å lagre:" + objectsToSave);
        Path pathAppend = Paths.get(String.valueOf(path));
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
                System.out.println(objectsLoaded);
            } catch (IOException e) {
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

    public static String getPathToUser() {
        return userPreferences.getStringPathToUser();
    }

    public void saveAll() throws IOException {
        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.

        ArrayList<Object> objects = FileUtility.createObjectList(ContextModel.INSTANCE.getComponentRegister(),
                ContextModel.INSTANCE.getComputerRegister(), ContextModel.INSTANCE.getSavedPathRegister(),
                ContextModel.INSTANCE.getComputer(), ContextModel.INSTANCE.getUserRegister());

        System.out.println("computer : " + ContextModel.INSTANCE.getComputer());
        System.out.println("user reg :" + ContextModel.INSTANCE.getUserRegister());

        System.out.println("lagrer alle disse objects:" + objects);
        FileHandling.saveFileAuto(objects,
                Paths.get(userPreferences.getStringPathToUser()));
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }


}
