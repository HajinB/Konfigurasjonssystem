package org.programutvikling.gui.controllers;

import javafx.stage.Stage;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.io.FileOpener;
import org.programutvikling.domain.io.FileOpenerTxt;
import org.programutvikling.domain.io.FileSaver;
import org.programutvikling.domain.io.FileSaverTxt;
import org.programutvikling.domain.utility.ComputerFactory;
import org.programutvikling.domain.utility.UserPreferences;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.model.Model;
import org.programutvikling.model.ModelEndUser;
import org.programutvikling.model.ModelUserRegister;
import org.programutvikling.model.TemporaryComponent;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//todo: må lage metode som lagrer path til ConfigMain i jobj - slik at den er brukervalgt (?)

public class FileHandling {
    private static UserPreferences userPreferences = new UserPreferences();

    public static void saveFileTxt(Computer computer, Path directoryPath) throws IOException {
        if (directoryPath != null) {
            FileSaverTxt saver = new FileSaverTxt();
            try {
                saver.save(computer, directoryPath);
            } catch (NoSuchFileException e) {
                Dialog.showErrorDialog("Noe galt skjedde under lagring av "+directoryPath.getFileName()+  "\n prøv et" +
                        " nytt navn");
                e.printStackTrace();
                throw new IOException("Noe galt skjedde under lagring av "+directoryPath.getFileName()+  "\n prøv et" +
                        " nytt navn");
            }
            Dialog.showSuccessDialog("Registeret ble lagret!");
        }
    }

    public static void saveFileAs(String chosenPath) throws IOException {
        if (chosenPath == null) {
            return;
        }
        Path path = Paths.get(chosenPath);
        ArrayList<Object> objectsToSave = FileUtility.createObjectList(Model.INSTANCE.getComponentRegister(),
                Model.INSTANCE.getSavedPathRegister(), null);
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
            Model.INSTANCE.getSavedPathRegister().addPathToListOfSavedFilePaths(directoryPath.toString());
            tryToSave(objectsToSave, directoryPath, saver);
        }
    }

    public static void saveFileAuto(ArrayList<Object> register, Path directoryPath) throws IOException {
        if (directoryPath != null) {
            FileSaver saver = null;
            System.out.println(directoryPath.toString());
            saver = FileUtility.getFileSaver(directoryPath.toString());
            tryToSave(register, directoryPath, saver);
        }
    }

    private static void tryToSave(ArrayList<Object> register, Path directoryPath, FileSaver saver) {
        try {
            saver.save(register, directoryPath);
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            Dialog.showErrorDialog("Lagring til fil feilet. Grunn: " + e.getMessage());
        }
    }

    public static void openFile(ArrayList<Object> objects, String selectedPath) {
        File file = new File(selectedPath);
        if (file.exists()) {
            openObjects(objects, selectedPath);
        } else {
            File fileBackup = new File(Model.INSTANCE.getUserPreferences().getStringPathToBackupAppFiles());
            if (fileBackup.exists())
                openObjects(objects, Model.INSTANCE.getUserPreferences().getStringPathToBackupAppFiles());
        }
    }

    public static ArrayList<Object> openObjects(ArrayList<Object> register, String selectedPath) {
        FileOpener opener = FileUtility.getFileOpener(selectedPath);
        ArrayList<Object> objectsLoaded = new ArrayList<>();
        if (opener != null) {
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
        return openObjects(objects, path);
    }

    public static String getPathToUser() {
        return userPreferences.getStringPath();
    }

    public static void saveAllAdminFiles() throws IOException {
        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objects = FileUtility.createObjectList(Model.INSTANCE.getComponentRegister(),
                Model.INSTANCE.getSavedPathRegister(),
                ModelUserRegister.INSTANCE.getUserRegister());
        FileHandling.saveFileAuto(objects,
                Paths.get(getPathToUser()));
    }

    public static void saveAllEndUserFiles() {
        saveEndUserState();
    }

    private static void saveEndUserState() {
        Computer computer = getComputer();
        try {
            saveFileTxt(computer, Paths.get(userPreferences.getStringPathToUserComputer()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBackup() throws IOException {
        ArrayList<Object> objects = FileUtility.createObjectList(Model.INSTANCE.getComponentRegister(),
                Model.INSTANCE.getSavedPathRegister(),
                ModelUserRegister.INSTANCE.getUserRegister());
        FileHandling.saveFileAuto(objects,
                Paths.get("AppFiles/Database/Backup/AppDataBackup.jobj"));
    }

    static Computer getComputer() {
        return ModelEndUser.INSTANCE.getComputer();
    }

    public static boolean validateCartListToSave(List<String> whatsMissing, Stage stage) throws IOException {
        if (showDialogIfComponentsAreMissing(whatsMissing, stage)) return true;

        FileSaverTxt fileSaverTxt = new FileSaverTxt();
        String path = FileUtility.getFilePathFromSaveTXTDialog(stage);
        if (path == null) {
            return true;
        }
        fileSaverTxt.save(getComputer(), Paths.get(path));
        System.out.println(path);

        //lager en ny computer av filepath = name, current computer = componentregister
        File file = new File(path);
        ComputerFactory computerFactory = new ComputerFactory();
        String name = FileUtility.getNameFromFilePath(file);
        Computer computer = computerFactory.computerFactory(getComputer().getComponentRegister(), name);

        ModelEndUser.INSTANCE.getComputerRegister().addComputer(computer);
        saveAllAdminFiles();
        return false;
    }

    public static boolean showDialogIfComponentsAreMissing(List<String> whatsMissing, Stage stage) {
        if (whatsMissing.size() > 0) {
            /**Kan lage en bra tostring av whatsMissing - evt en utility method - FileUtility.toLabel(whatsMissing))*/
            Dialog.showErrorDialog("Legg til " + whatsMissing.toString() + " for å " +
                    "fortsette");
            return true;
        }
        return false;
    }

    public static ArrayList<Computer> findComputers() {
        ArrayList<Object> componentList = new ArrayList<>();
        File folder = new File(String.valueOf(userPreferences.getPathToComputers()));

        File[] listOfFiles = folder.listFiles();

        ArrayList<Computer> computers = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                Computer computer = new Computer(file.getName());
                System.out.println(file.toString());
                //FileHandling.openSelectedComputerTxtFiles(computerList, file.getPath());
                componentList.addAll(FileHandling.openSelectedComputerTxtFiles(componentList, file.getPath()));
                //
                //legg til computers her
                for (Object o : componentList) {
                    computer.getComponentRegister().addComponent((Component) o);
                }
                computers.add(computer);
                componentList.clear();
            }
            //computerList blir en liste med komponenter aka en computer.
        }
        return computers;
    }


    public static void openCart(Computer computer) {
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        try {
            fileOpenerTxt.open(computer, Paths.get(Model.INSTANCE.getUserPreferences().getPathPathToUserComputer().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TemporaryComponent.INSTANCE.errorList.size() > 0) {
            Dialog.showInformationDialog(TemporaryComponent.INSTANCE.errorListToString());
            TemporaryComponent.INSTANCE.errorList.clear();
        }
    }

    public static void openCartFromSelectedFile(Computer computer, Stage stage) {
        String chosenFile = FileUtility.getFilePathFromOpenTxtDialog(stage);
        Computer safetyComputer = new Computer("safety");
        safetyComputer.getComponentRegister().getRegister().addAll(computer.getComponentRegister().getRegister());
        if (chosenFile == null) {
            return;
        }
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        try {
            fileOpenerTxt.open(computer, Paths.get(chosenFile));
            if (computer.getComponentRegister().getRegister().size() == 0) {
                computer.getComponentRegister().getRegister().addAll(safetyComputer.getComponentRegister().getRegister());
                Dialog.showErrorDialog("Datamaskinen du prøvde å laste inn var ikke gyldig.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TemporaryComponent.INSTANCE.errorList.size() > 0) {
            Dialog.showSuccessDialog(TemporaryComponent.INSTANCE.errorListToString());
            TemporaryComponent.INSTANCE.errorList.clear();
        }
    }
}
