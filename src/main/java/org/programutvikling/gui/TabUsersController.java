package org.programutvikling.gui;

//todo sett opp nested fxml controllers? altså sånn at user-tabben får en egen fxml? man kan dele opp controlleren
// veldig lett da (?)   ((main controlleren blir så full)) - how to avoid - beste er å enten hente input fra en annen
// class eller sende input fra controller til andre steder på en bra måte.

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.programutvikling.model.Model;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserRegister;

import java.net.URL;
import java.util.ResourceBundle;

public class TabUsersController implements Initializable {
    @FXML
    private GridPane userReg;
    @FXML
    private Label lblUserMsg;
    @FXML
    private TextField userSearch;
    @FXML
    private TableView<User> tblViewUser;

    public void btnAddUser(ActionEvent actionEvent) {
        ///

    }

    public void btnDeleteUser(ActionEvent actionEvent) {

    }


    public void userName(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userMail(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userAdress(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userZipCode(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userCity(TableColumn.CellEditEvent cellEditEvent) {

    }

    public UserRegister getUserRegister(){
        return Model.INSTANCE.getUserRegister();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //user lagd av inputfields
        //getUserRegister().addBruker();



    }
}
