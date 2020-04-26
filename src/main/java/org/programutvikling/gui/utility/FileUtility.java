package org.programutvikling.gui.utility;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.FileOpener;
import org.programutvikling.component.io.FileSaver;
import org.programutvikling.component.io.OpenerFactory;
import org.programutvikling.component.io.SaverFactory;
import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.gui.ContextModel;
import org.programutvikling.gui.SavedPathRegister;

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
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        return saveDialog(stage, fileChooser);
    }

    public static String getFilePathFromSaveJOBJDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JOBJ files (*.jobj)", "*.jobj");

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

    public static String getFilePathFromOpenJobjDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JOBJ files (*.jobj)", "*.jobj");
        fileChooser.getExtensionFilters().add(extFilter);

        return openDialog(stage, fileChooser);
    }

    public static String getFilePathFromOpenTxtDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        return openDialog(stage, fileChooser);
    }

    private static String openDialog(Stage stage, FileChooser fileChooser) {
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            // handle cancellation properly
            return null;
        } else {
            String pathToFile = selectedFile.getPath();

            return pathToFile;
        }
    }

    public static ArrayList<Object> createObjectList(ComponentRegister componentRegister,
                                                     ComputerRegister computerRegister, SavedPathRegister savedPathRegister,
                                                     Computer computer) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(componentRegister);
        if (componentRegister != null)
            objects.add(computerRegister);
        if (savedPathRegister != null)
            objects.add(savedPathRegister);
        if (computer != null)
            objects.add(computer);
        return objects;
    }

    /**
     * Denne metoden leter gjennom userpath etter nylige lagrede filepaths - men hvis brukeren ikke endrer filsted,
     * finner den bare 1 fil
     */
    public static void populateRecentFiles() {
        //todo kanskje skriv getStringPathToMostRecentSavedFile altså slik at den søker gjennom der brukeren lagret sist
        String pathToUser = ContextModel.INSTANCE.getUserPreferences().getStringPathToUser();
        if (doesFileExist(pathToUser)) {
            try (Stream<Path> walk = Files.walk(Paths.get(pathToUser))) {
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
}
