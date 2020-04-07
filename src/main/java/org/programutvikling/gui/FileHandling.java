package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

//todo: må lage metode som lagrer path til ConfigMain i jobj - slik at den er brukervalgt (?)

public class FileHandling {

    private Path directoryPath;

    private static String getFileExt(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    static void saveFileTxt(ComponentRegister register, Path directoryPath) {
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

    static void saveFileJobj(ComponentRegister register, Path directoryPath) throws IOException {
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

        File folder = new File("Directory");  //kanskje selvvalg eller variabel her (?) som path
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

    static void loadAppFiles(ComponentRegister componentRegister, String directory) throws IOException {
        File file = new File("FileDirectory/Components/sadffsda.jobj");
        // loadJobjFromDirectory(componentRegister, Paths.get("FileDirectory/ConfigMain.jobj"));
        openFile(componentRegister, directory);
    }

    public void loadSelectedFile(ComponentRegister componentRegister, String path){
        openFile(componentRegister, path);
    }

    public void loadAllFilesFromDirectory(ComponentRegister componentRegister) throws IOException {
        File folder = new File("FileDirectory");  //kanskje selvvalg eller variabel her (?) som path
        File[] listOfFiles = folder.listFiles();
        FileOpenerJobj fileOpenerJobj = new FileOpenerJobj();
        assert listOfFiles != null;
        fileOpenerJobj.open(componentRegister, Paths.get(listOfFiles[0].getPath()));
        System.out.println(componentRegister.getRegister().get(0).getName());
    }
//todo: må ha en måte å lagre dette på, lokalt for brukeren slik at det loades inn (load configs metode)

    static void openFile(ComponentRegister register, String selectedPath) {

        FileOpener opener = getFileOpener(selectedPath);

        if (opener != null && selectedPath !=null){
            try {
                Path path = Paths.get(selectedPath);
                opener.open(register, path); //todo her kan man legge inn en thread gjennom en metode istede for å
                // calle fileopenerTxt/jobj direkte.
            } catch (IOException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                Dialog.showErrorDialog("Åpning av filen feilet. Grunn: " + e.getMessage());
            }
        }else{
            Dialog.showErrorDialog("opener eller path er null;");

        }
    }

    private static FileOpener getFileOpener(String selectedPath) {
        File file = new File(String.valueOf(Paths.get(String.valueOf(selectedPath))));
        String fileExt = getFileExt(file);
        OpenerFactory openerFactory = new OpenerFactory();
        return openerFactory.createOpener(fileExt);
    }

    public Path getCurrentDirectoryPath() {
        //initialiserer directory pathen, men vil la bruker endre dette i settings
        Path configPath = Paths.get("FileDirectory");
        // filbehandling.setCurrentDirectoryPath(configPath);
        this.directoryPath = Paths.get(configPath + "/ConfigMain.jobj");

        return directoryPath;
    }

    void setCurrentDirectoryPath(Path directoryPath) {
        //Path configPath = Paths.get("FileDirectory");
        if (directoryPath != null) {
            this.directoryPath = Paths.get(directoryPath + "/ConfigMain.jobj");
        } else {
            System.out.println("wth");
        }

    }


}
