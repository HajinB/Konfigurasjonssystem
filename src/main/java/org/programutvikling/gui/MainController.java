package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.programutvikling.App;

import java.io.IOException;

public class MainController {

/**
    https://stackoverflow.com/questions/32407666/javafx8-fxml-controller-injection
    https://stackoverflow.com/questions/32849277/javafx-controller-injection-does-not-work
 */
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
        String chosenPath = FileHandling.getFilePathFromSaveDialog(this.stage);

        FileHandling.saveFileAs(chosenPath);
        System.out.println(ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths().get(0));
        //todo send SavedPathRegister til en metode som updater i tabCOmponentsController
/*
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("org/programutvikling/tabComponents.fxml"));
            Parent root = loader.load();

            //Get controller of scene2
            TabComponentsController componentsController = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            //scene2Controller.transferMessage(inputField.getText());
            componentsController.
*/

        //Get controller of scene2


        //https://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml

        //todo Is it possible to set the controller in the FXML file? Beause removing the line: loader.setController(this) and adding the controller in the FXML file crashes the application – Halfacht Oct 20 '18 at 10:15
        //
        //Not if the FXML is loaded from within the controller itself.
        // If you load the FXML from the Main class, for example,
        // you can define the controller in the FXML file and get a reference to it using loader.getController()
        //todo how to access another controllers methods?
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
    public void btnSetDirectory(ActionEvent actionEvent) {
        //super.btnSetDirectory(actionEvent);

    }
}
