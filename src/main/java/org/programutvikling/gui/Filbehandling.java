package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.programutvikling.komponent.Komponent;
import org.programutvikling.komponent.KomponentRegister;
import org.programutvikling.komponent.io.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//todo: må lage metode som lagrer path til ConfigMain i jobj - slik at den er brukervalgt (?)

public class Filbehandling {

    private Path directoryPath;
    public void populateComboBox(){

        File folder = new File("Directory");  //kanskje selvvalg eller variabel her (?) som path
        File[] listOfFiles = folder.listFiles();
        ObservableList<String> liste =
                FXCollections.observableArrayList();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                liste.add(listOfFiles[i].getName());


            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
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


     public static void loadAppConfigurationFile(KomponentRegister komponentRegister, String directory) throws IOException {
         File file = new File("FileDirectory/Components/sadffsda.jobj");
        // loadJobjFromDirectory(komponentRegister, Paths.get("FileDirectory/ConfigMain.jobj"));
         openFile(komponentRegister, directory);
    }

    public void loadAllFilesFromDirectory(KomponentRegister komponentRegister, Path directory) throws IOException {
        File folder = new File("FileDirectory");  //kanskje selvvalg eller variabel her (?) som path
        File[] listOfFiles = folder.listFiles();

        FileOpenerJobj fileOpenerJobj = new FileOpenerJobj();
        fileOpenerJobj.open(komponentRegister, Paths.get(listOfFiles[0].getPath()));
        System.out.println(komponentRegister.getRegister().get(0).getName());
    }

        //alt + cmd + B = go to implementation
    static void openFile(KomponentRegister register, String selectedPath) {
        File file = new File(String.valueOf(Paths.get(String.valueOf(selectedPath))));
        Path path = Paths.get(selectedPath);
        if (selectedPath != null) {
            String fileExt = getFileExt(file);
            FileOpener opener = null;

            switch (fileExt) {
                case ".txt" : opener = new FileOpenerTxt(); break;
                case ".jobj" : opener = new FileOpenerJobj(); break;
                default : Dialog.showErrorDialog("Du kan bare åpne txt eller jobj filer.");
            }



            if(opener != null) {
                try {
                    opener.open(register, path);
                } catch (IOException e) {
                    Dialog.showErrorDialog("Åpning av filen feilet. Grunn: " + e.getMessage());
                }
            }
        }
    }


    static void saveFileTxt(KomponentRegister register, Path directoryPath) {
        if (directoryPath != null) {
            FileSaver saver = new FileSaverTxt();
            if (saver != null) {
                try {
                    saver.save(register, directoryPath);
                    Dialog.showSuccessDialog("Registeret ble lagret!");
                } catch (IOException e) {
                    Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
                }

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
    public static List<?> convertListToReadable(List<Komponent> nonReadableList){

        List<Komponent> nyListe = new ArrayList<>();

        for (Komponent k : nonReadableList) {
            nyListe.add(k);
        }
        return nyListe;
    }

    static void saveFileJobj(KomponentRegister register, Path directoryPath) throws IOException {
       // File selectedFile = getPath;

        //FileSaverJobj binSaver = null;
        //binSaver.save((KomponentRegister) register, Paths.get("HTMLDirectory/"));

        if (directoryPath != null) {
            FileSaver saver = null;

               saver = new FileSaverJobj();
               //Dialog.showErrorDialog("Du kan bare lagre til enten txt eller jobj filer.");


            if(saver != null) {
                try {
                    saver.save(register, directoryPath);
                    Dialog.showSuccessDialog("Registeret ble lagret!");
                } catch (IOException e) {
                    Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
                }
            }
        }

    }

    private static String getFileExt(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
