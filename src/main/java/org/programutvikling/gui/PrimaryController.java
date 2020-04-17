package org.programutvikling.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import org.programutvikling.App;

//https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
public class PrimaryController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileHandling.populateRecentFiles();
        try {
            loadRegisterFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    FileHandling fileHandling = new FileHandling();
    private void loadRegisterFromFile() throws IOException {
        File file = new File(String.valueOf(fileHandling.getPathToUser()));
        String path = file.getAbsolutePath();
        if (file.exists()) {
            //currentContext.getComponentRegister().getRegister().addAll(
            FileHandling.openObjects(ContextModel.INSTANCE.getCleanObjectList(),
                    fileHandling.getPathToUser());
//            System.out.println(componentRegister.toString());
            //ContextModel.INSTANCE.loadObjectsIntoClasses();
            FileHandling.openFile(ContextModel.INSTANCE.getCleanObjectList(), fileHandling.getPathToUser());
            ContextModel.INSTANCE.loadObjectsIntoClasses();
        }
    }


    //https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html


    }
