package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.*;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.user.UserPreferences;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//todo: må lage metode som lagrer path til ConfigMain i jobj - slik at den er brukervalgt (?)

public class FileHandling {
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");

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
        ArrayList<Object> objectsToSave = FileHandling.createObjectList(ContextModel.INSTANCE.getComponentRegister(),
                null);
        System.out.println("rett før saveAs"+objectsToSave);
        System.out.println(ContextModel.INSTANCE.getComponentRegister().toString());
        saveFile(objectsToSave, path);
    }

    static void saveFile(ArrayList<Object> register, Path directoryPath) throws IOException {
        if (directoryPath != null) {
            FileSaver saver = null;
            saver = getFileSaver(directoryPath.toString());
            //Dialog.showErrorDialog("Du kan bare lagre til enten txt eller jobj filer.");
            try {
                saver.save(register, directoryPath);
                Dialog.showSuccessDialog("Registeret ble lagret!");
            } catch (IOException e) {
                Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
            }
        }
    }

    static void saveFileAuto(ArrayList<Object> register, Path directoryPath) throws IOException{
        if (directoryPath != null) {
            FileSaver saver = null;
            saver = getFileSaver(directoryPath.toString());
            //Dialog.showErrorDialog("Du kan bare lagre til enten txt eller jobj filer.");
            try {
                saver.save(register, directoryPath);
            } catch (IOException e) {
                Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
            }
        }
    }

    //open file kan nå ta de fleste
    static void openFile(ArrayList<Object> objects, String selectedPath) {

        //factory - lag en versjon av den factorien du hadde der ista - med object som blir til componentregister eller
        openObjects(objects, selectedPath);
    }

    public static ArrayList<Object> openObjects(ArrayList<Object> register, String selectedPath) {
        //bruker getFileOpener for å få txt eller jobj opener.
        FileOpener opener = getFileOpener(selectedPath);
        ArrayList<Object> objectsLoaded = new ArrayList<>();
        if (opener != null && selectedPath != null) {
            try {
                Path path = Paths.get(selectedPath);
                objectsLoaded.addAll(opener.open(register, path)); //todo her kan man legge inn en thread
                System.out.println(objectsLoaded.size());
                // gjennom en metode istede
                System.out.println("etter opener" + objectsLoaded.size());


            } catch (IOException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                Dialog.showErrorDialog("Åpning av filen feilet. Grunn: " + e.getMessage());
            }
        } else {
            Dialog.showErrorDialog("opener eller path er null;");
        }
        return objectsLoaded;
    }

    private static Object createOpenableRegister(Object object) {
        if (getFileName(object.getClass().getName()).equals("ComponentRegister")) {
            return new ComponentRegister();
        } else if (getFileName(object.getClass().getName()).equals("ComputerRegister")) {
            return new ComputerRegister();
        }
        return object;
    }

    private static String getFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    //alt + cmd + B = go to implementation

    private static String getFileExt(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }
    static String getStringPathFromFile(File path) {
        System.out.println(path.getPath());
        return path.getPath();
    }

    private static FileSaver getFileSaver(String selectedPath){
        String fileExt = getFileName(selectedPath);
        SaverFactory saverFactory = new SaverFactory();
        return saverFactory.createOpener(fileExt);
    }

    private static FileOpener getFileOpener(String selectedPath) {
        String fileExt = getFileName(selectedPath);
        OpenerFactory openerFactory = new OpenerFactory();
        return openerFactory.createOpener(fileExt);
    }

    public static String getFilePathFromSaveDialog(Stage stage){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(stage);
        String pathToFile = selectedFile.getPath();

        return pathToFile;
    }

    public static String getFilePathFromOpenDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        String pathToFile = selectedFile.getPath();

        return pathToFile;
    }

    public static ArrayList<Object> openSelectedComputerTxtFiles(ArrayList<Object> objects, String path) {
        //String path = getFilePathFromFileChooser(stage);
        return openObjects(objects, path);
    }

    public void saveAll() throws IOException {

        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objects = createObjectList(ContextModel.getInstance().getComponentRegister(),
                ContextModel.getInstance().getComputerRegister());

//todo prøv å legg denne metoden i en annen klassse - den trenger ikke være her - userpreferences kan være en del av
// context model..
        FileHandling.saveFileAuto(objects,
                Paths.get(userPreferences.getPathToUser()));
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public String getPathToUser() {
        return userPreferences.getPathToUser();
    }

    static ArrayList<Object> createObjectList(ComponentRegister componentRegister,
                                              ComputerRegister computerRegister) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(componentRegister);
        objects.add(computerRegister);

        return objects;
    }

    public void populateComboBoxes() {

        File folder = new File("Directory");
        File[] listOfFiles = folder.listFiles();
        ObservableList<String> liste =
                FXCollections.observableArrayList();

        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                System.out.println("File " + listOfFile.getName());
                liste.add(listOfFile.getName());


            } else if (listOfFile.isDirectory()) {
                System.out.println("Directory " + listOfFile.getName());
            }
        }
        if (liste.size() > 0) {
            //todo her må vi ha metoder for å skille ut typene for å populere comboboxene til sluttbruker - skal typer
            // være combobox for superbruker?
            //openCombobox = new ComboBox<>(options);
            System.out.println(liste);
            //openCombobox.getItems().addAll(options.get(0));
            //openCombobox.setItems(options);
            System.out.println("added to combobox");
            //todo send denne arrayen et sted hvor den kan populere div ting
            //openComboBox = new ComboBox(options);
            //openCombobox.setItems(options);
        }
    }

    public void loadSelectedFile(ArrayList<Object> objects, String path) {
        openFile(objects, path);
    }

    public void loadAllFilesFromDirectory(ArrayList<Object> objects, Path path) throws IOException {
        File folder = new File("FileDirectory");  //kanskje selvvalg eller variabel her (?) som path
        File[] listOfFiles = folder.listFiles();
        FileOpenerJobj fileOpenerJobj = new FileOpenerJobj();
        assert listOfFiles != null;
        fileOpenerJobj.open(objects, Paths.get(listOfFiles[0].getPath()));
    }
}
