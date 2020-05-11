package org.programutvikling.gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.programutvikling.App;
import org.programutvikling.model.Model;
import org.programutvikling.domain.user.User;

//https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
public class LoginController implements Initializable {

    public Label lblFeilPassword;
    FileHandling fileHandling = new FileHandling();

    @FXML
    private TextField inputUsername;

    @FXML
    private PasswordField inputPassword;

    @FXML
    Button btnLogin;

    @FXML
    void btnGuest(ActionEvent event) throws IOException {
// here runs the JavaFX thread
// Boolean as generic parameter since you want to return it
        openUserView();
    }

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        loginAction();
    }

    private void loginAction() throws IOException {
        User loginUser = Model.INSTANCE.getUserRegister().loginCredentialsMatches(inputUsername.getText(),inputPassword.getText());
        if(loginUser != null){
            // Turns off the label when you get a successful login
            lblFeilPassword.setVisible(false);
            if(loginUser.getAdmin()) {
                App.setRoot("secondary");
            } else{
                openUserView();
            }
        } else {
            // Only one label for both wrong username
            lblFeilPassword.setVisible(true);
        }
    }

    /*private void openUserView() throws IOException {
        if(isUser()){
            App.setRoot("sluttbruker");
        }
    }*/

    private void openUserView() throws IOException {
        App.setRoot(("endUser"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loadRegisterFromFile();
      //  Model.INSTANCE.getUserRegister();
    btnLogin.setDefaultButton(true);
    // Adds users if there is no users OR there is no admin users
        Model.INSTANCE.addDefaultUsers();
    }


    private void loadRegisterFromFile() throws IOException {
        File file = new File(String.valueOf(FileHandling.getPathToUser()));
        String path = file.getAbsolutePath();
        if (file.exists()) {
            FileHandling.openFile(Model.INSTANCE.getCleanObjectList(), FileHandling.getPathToUser());
            Model.INSTANCE.loadObjectsIntoClasses();
        }
    }


    //https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html


    }
