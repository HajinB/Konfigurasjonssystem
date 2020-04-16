package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainController {

/**
    https://stackoverflow.com/questions/32407666/javafx8-fxml-controller-injection
    https://stackoverflow.com/questions/32849277/javafx-controller-injection-does-not-work
 */

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
    public void btnSaveToChosenPath(ActionEvent actionEvent) throws IOException {
        //tabComponentsController.btnSaveToChosenPath(actionEvent);
    }
    @FXML
    public void btnLogOut(ActionEvent actionEvent) throws IOException {
       // super.btnLogOut(actionEvent);

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
