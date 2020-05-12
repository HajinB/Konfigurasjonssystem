package org.programutvikling.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.programutvikling.App;
import org.programutvikling.domain.user.User;
import org.programutvikling.model.Model;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public Label lblFeilPassword;
    @FXML
    Button btnLogin;
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;

    @FXML
    void btnGuest(ActionEvent event) throws IOException {
        openUserView();
    }

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        loginAction();
    }

    private void loginAction() throws IOException {
        User loginUser = Model.INSTANCE.getUserRegister().loginCredentialsMatches(inputUsername.getText(), inputPassword.getText());
        if (loginUser != null) {
            // Turns off the label when you get a successful login
            lblFeilPassword.setVisible(false);
            if (loginUser.getAdmin()) {
                App.setRoot("superUser");
            } else {
                openUserView();
            }
        } else {
            // Only one label for both wrong username
            lblFeilPassword.setVisible(true);
        }
    }

    private void openUserView() throws IOException {
        App.setRoot(("endUser"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnLogin.setDefaultButton(true);
        // Adds users if there is no users OR there is no admin users
        Model.INSTANCE.addDefaultUsers();
    }

}
