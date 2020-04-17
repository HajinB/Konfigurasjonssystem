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
import org.programutvikling.gui.utility.FileUtility;

//https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
public class PrimaryController implements Initializable {

    ContextModel model = ContextModel.INSTANCE;

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
    }

    /*private void openUserView() throws IOException {
        if(isUser()){
            App.setRoot("sluttbruker");
        }
    }*/

    private void openUserView() throws IOException {
        App.setRoot(("endUser"));
    }



    private boolean isUser() {
        return inputPassword.getText().equals("bruker");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            loadRegisterFromFile();
            FileUtility.populateRecentFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    FileHandling fileHandling = new FileHandling();

    private void loadRegisterFromFile() throws IOException {
        File file = new File(String.valueOf(fileHandling.getPathToUser()));
        String path = file.getAbsolutePath();
        if (file.exists()) {
            FileHandling.openFile(ContextModel.INSTANCE.getCleanObjectList(), fileHandling.getPathToUser());
            ContextModel.INSTANCE.loadObjectsIntoClasses();
        }
    }


    //https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html


    }
