package org.programutvikling;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SecondaryController {

    @FXML
    private MenuBar menyBar;

    @FXML
    private TableView<?> tblView;

    @FXML
    private TableColumn<?, ?> kolonneVNr;

    @FXML
    private TableColumn<?, ?> kolonneType;

    @FXML
    private TableColumn<?, ?> kolonneVNavn;

    @FXML
    private TableColumn<?, ?> kolonneBesk;

    @FXML
    private TableColumn<?, ?> kolonnePris;

    @FXML
    private TableColumn<?, ?> kolonneAnt;

    @FXML
    void btnLoggUt(ActionEvent event) {

    }

    @FXML
    void btnLukkMeny(ActionEvent event) {

    }

    @FXML
    void kolonneAntEdit(ActionEvent event) {

    }

    @FXML
    void kolonneBeskEdit(ActionEvent event) {

    }

    @FXML
    void kolonnePrisEdit(ActionEvent event) {

    }

    @FXML
    void kolonneTypeEdit(ActionEvent event) {

    }

    @FXML
    void kolonneVNavnEdit(ActionEvent event) {

    }

    @FXML
    void kolonneVNrEdit(ActionEvent event) {

    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

}
