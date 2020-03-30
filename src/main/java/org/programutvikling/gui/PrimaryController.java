package org.programutvikling.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.programutvikling.App;
import org.programutvikling.bruker.Logikk;

//https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
public class PrimaryController {

    @FXML
    private TextField inputBrukernavn;

    @FXML
    private PasswordField inputPassord;

    @FXML
    void btnGjest(ActionEvent event) throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        //returnerer 1 hvis admin, 2 hvis sluttbruker, 0 hvis feil feks.
        if(Logikk.sjekkBrukernavn(inputBrukernavn.getText()) == 1 &&  Logikk.sjekkPassord(inputPassord.getText()) == 1) {
            App.setRoot("secondary");
        }

    }



}