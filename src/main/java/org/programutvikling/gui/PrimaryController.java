package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.programutvikling.App;
import org.programutvikling.domain.user.User;
import org.programutvikling.model.Model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
public class PrimaryController implements Initializable {

    FileHandling fileHandling = new FileHandling();
    @FXML
    Button btnLogin;
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;

    @FXML
    void btnGuest(ActionEvent event) throws IOException {
// here runs the JavaFX thread
// Boolean as generic parameter since you want to return it

        App.setRoot("secondary");
    }

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        loginAction();
    }

    private void loginAction() throws IOException {
        User loginUser = Model.INSTANCE.getUserRegister().loginCredentialsMatches(inputUsername.getText(), inputPassword.getText());
        if (loginUser != null) {
            if (loginUser.getAdmin()) {
                App.setRoot("secondary");
            } else {
                openUserView();
            }
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
        System.out.println(Model.INSTANCE);
        User user = new User(true, "admin", "admin", "ola",
                "admin@admin.com", "trondheimsvegen 1", "0909", "Trondheim");

        User user2 = new User(false, "user", "user", "ola",
                "user@user.com",  "trondheimsvegen 1", "0909", "Trondheim");

        Model.INSTANCE.getUserRegister().addBruker(user);
        Model.INSTANCE.getUserRegister().addBruker(user2);

        btnLogin.setDefaultButton(true);
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
