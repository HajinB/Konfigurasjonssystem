package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.programutvikling.App;

import java.io.IOException;

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
    private TextField inputVarenummer;

    @FXML
    private TextField inputVarenavn;

    @FXML
    private TextField inputPris;

    @FXML
    private TextField inputVaretype;

    @FXML
    private TextArea inputBeskrivelse;

    @FXML
    void btnFjern(ActionEvent event) {

    }

    @FXML
    void btnFraFil(ActionEvent event) {

    }

    @FXML
    void btnLeggTil(ActionEvent event) {

    }

    @FXML
    void btnLoggUt(ActionEvent event) {

    }

    @FXML
    void btnLukkMeny(ActionEvent event) {

    }

    @FXML
    void inputSok(KeyEvent event) {

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
    private void btnLoggUt() throws IOException {
        App.setRoot("primary");
    }
}
