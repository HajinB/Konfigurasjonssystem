package org.programutvikling.gui.utility;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.io.FileOpener;
import org.programutvikling.domain.io.FileSaver;
import org.programutvikling.domain.user.SavedPathRegister;
import org.programutvikling.domain.user.UserRegister;
import org.programutvikling.domain.utility.OpenerFactory;
import org.programutvikling.domain.utility.SaverFactory;
import org.programutvikling.model.Model;

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
    private static String getFileName(String fileName) {
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

    public static String getFilePathFromSaveTXTDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT org.programutvikling.files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        return saveDialog(stage, fileChooser);
    }

    public static String getFilePathFromSaveJOBJDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("AppFiles/Database/Admin/ComponentRegisters"));
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JOBJ org.programutvikling.files (*.jobj)", "*.jobj");

        fileChooser.getExtensionFilters().add(extFilter);

        return saveDialog(stage, fileChooser);
    }

    private static String saveDialog(Stage stage, FileChooser fileChooser) {
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile == null) {
            return null;

        }
        String pathToFile = selectedFile.getPath();
        return pathToFile;
    }

    public static String getStringPathFromOpenJobjDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("AppFiles/Database/Admin/ComponentRegisters"));

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JOBJ org.programutvikling.files (*.jobj)", "*.jobj");
        fileChooser.getExtensionFilters().add(extFilter);

        return openDialog(stage, fileChooser);
    }

    public static String getFilePathFromOpenTxtDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("AppFiles/Database/User/Computers"));
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT org.programutvikling.files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        return openDialog(stage, fileChooser);
    }

    private static String openDialog(Stage stage, FileChooser fileChooser) {
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            return null;
        } else {
            String pathToFile = selectedFile.getPath();

            return pathToFile;
        }
    }

    //lager liste for saving - lagrer denne lista.
    public static ArrayList<Object> createObjectList(ComponentRegister componentRegister,
                                                     SavedPathRegister savedPathRegister,
                                                     UserRegister userRegister) {
        ArrayList<Object> objects = new ArrayList<>();

        /**Legger alt inn i listen her - kan heller ta det på opening - altså plassene er dedikert til et objekt, de
         * kan være null - da vil de bare ikke bli lasta inn.*/
        objects.add(0, componentRegister);
        //if (componentRegister != null)
        objects.add(1, savedPathRegister);
        // if (savedPathRegister != null)
        objects.add(2, userRegister);

        return objects;
    }

    /**
     * Denne metoden leter gjennom userpath etter nylige lagrede filepaths - men hvis brukeren ikke endrer filsted,
     * finner den bare 1 fil
     */
    public static void populateRecentFiles(String pathToUser) {
        //todo kanskje skriv getStringPathToMostRecentSavedFile altså slik at den søker gjennom der brukeren lagret sist
        if (doesFileExist(pathToUser)) {
            try (Stream<Path> walk = Files.walk(Paths.get(pathToUser))) {
                List<String> result = walk.filter(Files::isRegularFile)
                        .map(Path::toString).collect(Collectors.toList());
                addWalkedFolderPathsToCBList(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList populateRecentFilesVoid() {
        File folder = new File("AppFiles/Database/Admin/ComponentRegisters");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> al = new ArrayList<>();

        for (File file : listOfFiles) {
            al.add(file.getAbsolutePath());
        }
        addWalkedFolderPathsToCBList(al);
        return al;
    }

    private static void addWalkedFolderPathsToCBList(List<String> result) {
        System.out.println("result som scouuuring");
        result.forEach(System.out::println);
        List<String> unique =
                RegisterUtility.removeDuplicates(getListOfSavedFilePaths());
        getListOfSavedFilePaths().clear();
        getListOfSavedFilePaths().addAll(result);
    }

    private static ObservableList<String> getListOfSavedFilePaths() {
        return Model.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths();
    }

    public static boolean doesFileExist(String toString) {
        File tmp = new File(toString);
        return tmp.exists();
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

    public static String getNameFromFilePath(File file) {
        String namePath = file.getName();

        String r = namePath.split(".txt")[0].trim();
        return r;
    }
}
