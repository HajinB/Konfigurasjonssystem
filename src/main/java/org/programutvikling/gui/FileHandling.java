package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ItemUsable;
import org.programutvikling.component.io.*;
import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.user.User;
import org.programutvikling.user.UserRegister;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//todo: må lage metode som lagrer path til ConfigMain i jobj - slik at den er brukervalgt (?)

public class FileHandling {

    private Path directoryPath;



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

    static void saveFileJobj(ArrayList<Object> register, Path directoryPath) throws IOException {
        // File selectedFile = getPath;

        //FileSaverJobj binSaver = null;
        //binSaver.save((componentRegister) register, Paths.get("HTMLDirectory/"));

        if (directoryPath != null) {
            FileSaver saver = null;

            saver = new FileSaverJobj();
            //Dialog.showErrorDialog("Du kan bare lagre til enten txt eller jobj filer.");


            try {
                saver.save(register, directoryPath);
                Dialog.showSuccessDialog("Registeret ble lagret!");
            } catch (IOException e) {
                Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
            }
        }

    }

    static String getStringPathFromFile(File path) {
        System.out.println(path.getPath());
        return path.getPath();
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

    //alt + cmd + B = go to implementation

    static void loadAppFiles(ArrayList<Object> objects, String directory) throws IOException {
        File file = new File("FileDirectory/Components/sadffsda.jobj");
        // loadJobjFromDirectory(componentRegister, Paths.get("FileDirectory/ConfigMain.jobj"));
        openFile(objects, directory);
    }

    public void loadSelectedFile(ArrayList<Object> objects, String path){
        openFile(objects, path);
    }

    public void loadAllFilesFromDirectory(ArrayList<Object> objects, Path path) throws IOException {
        File folder = new File("FileDirectory");  //kanskje selvvalg eller variabel her (?) som path
        File[] listOfFiles = folder.listFiles();
        FileOpenerJobj fileOpenerJobj = new FileOpenerJobj();
        assert listOfFiles != null;
        fileOpenerJobj.open(objects, Paths.get(listOfFiles[0].getPath()));

    }

        //open file kan nå ta de fleste
    static void openFile(ArrayList<Object> objects, String selectedPath) {

        //factory - lag en versjon av den factorien du hadde der ista - med object som blir til componentregister eller
        openObjects(objects, selectedPath);
    }


    private static void openObjects(ArrayList<Object> register, String selectedPath) {
        FileOpener opener = getFileOpener(selectedPath);

        if (opener != null && selectedPath !=null){
            try {
                Path path = Paths.get(selectedPath);
                opener.open(register, path); //todo her kan man legge inn en thread gjennom en metode istede
                System.out.println("etter opener");
                // calle fileopenerTxt/jobj direkte.
            } catch (IOException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                Dialog.showErrorDialog("Åpning av filen feilet. Grunn: " + e.getMessage());
            }
        }else{
            Dialog.showErrorDialog("opener eller path er null;");
        }
    }

    private static Object createOpenableRegister(Object object){
        if(getFileName(object.getClass().getName()).equals("ComponentRegister")) {
            return new ComponentRegister();
        }
        else if(getFileName(object.getClass().getName()).equals("ComputerRegister")) {
            return new ComputerRegister();
        }
        return object;
    }

    private static String getFileName(String fileName){
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    private static String getFileExt(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }


    private static FileOpener getFileOpener(String selectedPath) {
        File file = new File(String.valueOf(Paths.get(String.valueOf(selectedPath))));
        String fileExt = getFileExt(file);
        OpenerFactory openerFactory = new OpenerFactory();
        return openerFactory.createOpener(fileExt);
    }


    public void OpenSelectedComputerTxtFiles(ArrayList<Object> objects, String pathToUser) {
        openFile(objects, pathToUser);
    }
}
