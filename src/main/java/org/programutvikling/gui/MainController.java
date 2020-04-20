package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.gui.utility.FileUtility;

import java.io.IOException;

public class MainController {

/**
 *  https://stackoverflow.com/questions/32407666/javafx8-fxml-controller-injection
    https://stackoverflow.com/questions/32407666/javafx8-fxml-controller-injection
    https://stackoverflow.com/questions/32849277/javafx-controller-injection-does-not-work
 */


    FileHandling fileHandling = new FileHandling();
    Stage stage;
    // Inject controller
    @FXML private TabComponentsController componentsController;
/*
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TabComponentsController.fxml"));
    TabComponentsController tabComponentsController = fxmlLoader.<TabComponentsController>getController();
*/

    // Inject tab content.

    // Inject controller
    @FXML
    private TabUsersController usersController;


    @FXML
    void btnSaveToChosenPath(ActionEvent e) throws IOException {
        String chosenPath = FileUtility.getFilePathFromSaveJOBJDialog(this.stage);

        FileHandling.saveFileAs(chosenPath);
        //System.out.println(ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths().get(0));
        //
/*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("org/programutvikling/tabComponents.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        TabComponentsController tabComponentsController = loader.getController();
        tabComponentsController.updateRecentFiles();
        */

        //
    }

    @FXML
    void btnLogOut(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }
    @FXML
    public void btnOpenJobj(ActionEvent actionEvent) throws IOException {
        //super.btnOpenJobj(actionEvent);
    }

    @FXML
    void btnSetDirectory(ActionEvent event) {
        fileHandling.getUserPreferences().setPreference(stage);
        System.out.println("ny directory path: " + fileHandling.getUserPreferences().getStringPathToUser());
    }
}
