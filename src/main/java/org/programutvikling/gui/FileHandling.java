package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public void populateComboBox(){

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
        if(liste.size()>0) {

            //todo her må vi ha metoder for å skille ut typene for å populere comboboxene til sluttbruker - skal typer
            // være combobox for superbruker?
            //openCombobox = new ComboBox<>(options);
            System.out.println(liste);
            /*openCombobox.getItems().addAll(options.get(0));
            openCombobox.setItems(options);
            System.out.println("added to combobox");*/
            //todo send denne arrayen et sted hvor den kan populere div ting
            //openComboBox = new ComboBox(options);

            //openCombobox.setItems(options);
        }
    }


     static void loadAppConfigurationFile(ComponentRegister componentRegister, String directory) throws IOException {
         File file = new File("FileDirectory/Components/sadffsda.jobj");
        // loadJobjFromDirectory(componentRegister, Paths.get("FileDirectory/ConfigMain.jobj"));
         openFile(componentRegister, directory);
    }

    public void loadAllFilesFromDirectory(ComponentRegister componentRegister, Path directory) throws IOException {
        File folder = new File("FileDirectory");  //kanskje selvvalg eller variabel her (?) som path
        File[] listOfFiles = folder.listFiles();

        FileOpenerJobj fileOpenerJobj = new FileOpenerJobj();
        assert listOfFiles != null;
        fileOpenerJobj.open(componentRegister, Paths.get(listOfFiles[0].getPath()));
        System.out.println(componentRegister.getRegister().get(0).getProductName());
    }

        //alt + cmd + B = go to implementation
    static void openFile(ComponentRegister register, String selectedPath) {

        File file = new File(String.valueOf(Paths.get(String.valueOf(selectedPath))));
        Path path = Paths.get(selectedPath);
        String fileExt = getFileExt(file);
        FileOpener opener = null;

        //todo: skriv om denne switchen til polymorphisme PLIS
        switch (fileExt) {
            case ".txt" : opener = new FileOpenerTxt(); break;
            case ".jobj" : opener = new FileOpenerJobj(); break;
            default : Dialog.showErrorDialog("Du kan bare åpne txt eller jobj filer.");
        }

        System.out.println(fileExt);
        register.log();
        System.out.println(opener);
        System.out.println(path.toString());
        if(opener != null) {
            try {
                opener.open(register, path); //todo her kan man legge inn en thread gjennom en metode istede for å
                // calle fileopenerTxt/jobj direkte.
                System.out.println("ETTER OPENER HER");
            } catch (IOException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                Dialog.showErrorDialog("Åpning av filen feilet. Grunn: " + e.getMessage());
            }
        }
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
//todo: må ha en måte å lagre dette på, lokalt for brukeren slik at det loades inn (load configs metode)

    void setCurrentDirectoryPath(Path directoryPath){
        //Path configPath = Paths.get("FileDirectory");
        if(directoryPath != null) {
            this.directoryPath = Paths.get(directoryPath + "/ConfigMain.jobj");
        }else{
            System.out.println("wth");
        }

    }


    public Path getCurrentDirectoryPath(){
        //initialiserer directory pathen, men vil la bruker endre dette i settings
        Path configPath = Paths.get("FileDirectory");
       // filbehandling.setCurrentDirectoryPath(configPath);
        this.directoryPath = Paths.get(configPath + "/ConfigMain.jobj");

        return directoryPath;
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

    private static String getFileExt(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
