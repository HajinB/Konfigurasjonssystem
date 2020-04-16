package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainController extends TabComponentsController {

    // Inject controller
    @FXML private TabComponentsController componentsController;

    // Inject tab content.

    // Inject controller
    @FXML
    private TabUsersController usersController;

    public void btnSaveToChosenPath(ActionEvent actionEvent) throws IOException {
        super.btnSaveToChosenPath(actionEvent);
    }

    public void btnLogOut(ActionEvent actionEvent) throws IOException {
        super.btnLogOut(actionEvent);

    }

    public void btnOpenJobj(ActionEvent actionEvent) throws IOException {
        super.btnOpenJobj(actionEvent);

    }

    public void btnSetDirectory(ActionEvent actionEvent) {
        super.btnSetDirectory(actionEvent);

    }
}
