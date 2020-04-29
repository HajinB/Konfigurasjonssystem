package org.programutvikling.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import org.programutvikling.App;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.User;
import org.programutvikling.user.UserRegister;

//https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
public class PrimaryController implements Initializable {

    FileHandling fileHandling = new FileHandling();

    @FXML
    private TextField inputUsername;

    @FXML
    private PasswordField inputPassword;

    @FXML
    void btnGuest(ActionEvent event) throws IOException {
// here runs the JavaFX thread
// Boolean as generic parameter since you want to return it
       /* Task<Boolean> task = getTask();
        loadInThread(task);*/
        App.setRoot("secondary");
    }

    private void loadInThread(Task<Boolean> task) {
        Alert alert = Dialog.getLoadingDialog("Laster inn...");
        task.setOnRunning((e) -> alert.showAndWait());
        task.setOnSucceeded((e) -> {
            alert.close();
            try {
                Boolean returnValue = task.get();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        });
        task.setOnFailed((e) -> {
            // eventual error handling by catching exceptions from task.get()
        });
        new Thread(task).start();
    }

    private Task<Boolean> getTask() {
        return new Task<Boolean>() {
                @Override public Boolean call() throws IOException, InterruptedException {
                   //her skjer actionen
                    Thread.sleep(2000);
                    App.setRoot("secondary");
                    return true;
                }
                @Override
                protected void succeeded() {
                    // one hook - overriding
                    super.succeeded();
                    System.out.println("Succeded");
                }
            };
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

    UserRegister getUserRegister(){
        return ContextModel.INSTANCE.getUserRegister();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loadRegisterFromFile();
        User user = new User(true, "admin", "admin123", "ola",
                "hhhh@gmail.com", "999999", "trondheimsvegen 1", "0909", "Trondheim");

        User user2 = new User(false, "user", "user123", "ola",
                "hhhh@gmail.com", "999999", "trondheimsvegen 1", "0909", "Trondheim");

        getUserRegister().addBruker(user);
        getUserRegister().addBruker(user2);

        System.out.println("usersss"+ContextModel.INSTANCE.getUserRegister().getRegister().get(1).getUsername());
        System.out.println(ContextModel.INSTANCE.getUserRegister().getRegister().get(1));
    }


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
