package org.programutvikling.gui.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.*;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.gui.ContextModel;
import org.programutvikling.gui.SavedPathRegister;
import org.programutvikling.user.UserPreferences;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtility {
UserPreferences userPreferences = ContextModel.INSTANCE.getUserPreferences();


    public static String getFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static FileSaver getFileSaver(String selectedPath) {
        String fileExt = getFileName(selectedPath);
        SaverFactory saverFactory = new SaverFactory();
        return saverFactory.createSaver(fileExt);
    }

    public static FileOpener getFileOpener(String selectedPath) {
        String fileExt = getFileName(selectedPath);
        OpenerFactory openerFactory = new OpenerFactory();
        return openerFactory.createOpener(fileExt);
    }

    public static String getFilePathFromSaveDialog(Stage stage) {
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

    public static ArrayList<Object> createObjectList(ComponentRegister componentRegister,
                                              ComputerRegister computerRegister, SavedPathRegister savedPathRegister) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(componentRegister);
        if(componentRegister!=null)
        objects.add(computerRegister);
        if(savedPathRegister!=null)
        objects.add(savedPathRegister);
        return objects;
    }

    public static void populateRecentFiles() {
        String pathToUser = ContextModel.INSTANCE.getUserPreferences().getStringPathToUser();
        if (doesFileExist(pathToUser)) {
            try (Stream<Path> walk = Files.walk(Paths.get(pathToUser))){
                List<String> result = walk.filter(Files::isRegularFile)
                        .map(x -> x.toString()).collect(Collectors.toList());
                addWalkedFolderPathsToCBList(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addWalkedFolderPathsToCBList(List<String> result) {
        System.out.println("result som scouuuring");
        result.forEach(System.out::println);
        ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths().addAll(result);
        ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths().addAll(result);
    }

    public static boolean doesFileExist(String toString) {
        File tmp = new File(toString);
        return tmp.exists();
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

    public static void deleteFile(String file) {
        File f1 = new File(file);
        boolean success = f1.delete();

        if (!success) {
            System.out.println("Deletion failed.");

            //System.exit(0);
        } else {
            System.out.println("File deleted.");
        }
    }


}

    /*
    private static Object createOpenableRegister(Object object) {
        if ((object.getClass().getName()).equals("ComponentRegister")) {
            return new ComponentRegister();
        } else if (FileHandling.getFileName(object.getClass().getName()).equals("ComputerRegister")) {
            return new ComputerRegister();
        }
        return object;
    }

    public void loadAllFilesFromDirectory(ArrayList<Object> objects, Path path) throws IOException {
        File folder = new File("FileDirectory");  //kanskje selvvalg eller variabel her (?) som path
        File[] listOfFiles = folder.listFiles();
        FileOpenerJobj fileOpenerJobj = new FileOpenerJobj();
        assert listOfFiles != null;
        fileOpenerJobj.open(objects, Paths.get(listOfFiles[0].getPath()));
    }

    private static String getFileExtString(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    static String getStringPathFromFile(File path) {
        System.out.println(path.getPath());
        return path.getPath();
    }
}
*/