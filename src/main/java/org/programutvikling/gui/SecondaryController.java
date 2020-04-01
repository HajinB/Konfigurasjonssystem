package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.programutvikling.App;

import java.io.IOException;

public class SecondaryController {

        @FXML
        private MenuBar menyBar;

        @FXML
        private Label tblOverskrift;

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
        private TextField inputVaretype;

        @FXML
        private TextField inputVarenavn;

        @FXML
        private TextArea inputBeskrivelse;

        @FXML
        private TextField inputPris;

        @FXML
        private Label lblBekreftelse;

        @FXML
        private TextField inputSok;

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
        void btnLoggUt(ActionEvent event) throws IOException {
            App.setRoot("primary");

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

    }