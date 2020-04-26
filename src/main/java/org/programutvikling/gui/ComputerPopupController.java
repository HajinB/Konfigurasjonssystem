package org.programutvikling.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ComputerPopupController extends EnduserController implements Initializable {
    @FXML private Label lblComputerName;
    @FXML private ListView<String> listContent;
    @FXML private Label lblComputerPrice;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
