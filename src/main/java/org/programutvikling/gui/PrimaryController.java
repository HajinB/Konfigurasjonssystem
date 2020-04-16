package org.programutvikling.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import org.programutvikling.App;

//https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
public class PrimaryController {

    @FXML
    private TextField inputUsername;

    @FXML
    private PasswordField inputPassword;

    @FXML
    void btnGuest(ActionEvent event) throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        openUserView();
//        //returnerer 1 hvis admin, 2 hvis sluttbruker, 0 hvis feil feks.
//        if(Logikk.sjekkBrukernavn(inputBrukernavn.getText()) == 1 &&  Logikk.sjekkPassord(inputPassord.getText()) == 1) {
//            App.setRoot("secondary");
//        }
    }

    private void openUserView() throws IOException {
        if(isUser()){
            App.setRoot("sluttbruker");
        }
    }

    private boolean isUser() {
        return inputPassword.getText().equals("bruker");
    }




    //https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html


    }
