package org.programutvikling.gui;

//todo sett opp nested fxml controllers? altså sånn at user-tabben får en egen fxml? man kan dele opp controlleren
// veldig lett da (?)   ((main controlleren blir så full)) - how to avoid - beste er å enten hente input fra en annen
// class eller sende input fra controller til andre steder på en bra måte.

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.programutvikling.gui.utility.UserSearch;
import org.programutvikling.model.Model;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserRegister;

import java.io.IOException;
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

    private RegistryUserLogic registryUserLogic;

    public void btnAddUser(ActionEvent actionEvent) {
    registryUserLogic.registerUser();
    updateView();
    }

    public void btnDeleteUser(ActionEvent actionEvent) throws IOException {
        registryUserLogic.askForDeletion(tblViewUser.getSelectionModel().getSelectedItem());
        updateView();
    }

    public void cbAdmin(ActionEvent event) {

    }

    public void userName(TableColumn.CellEditEvent cellEditEvent) {

    }
    public void userPassword(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userMail(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userAddress(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userZipCode(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userCity(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void updateView() {
        getUserRegister().attachTableView(tblViewUser);
    }

    public void registerUser() {

    }
    @FXML
    void search(KeyEvent event) {
            setSearchedList();
    }

    private void setSearchedList() {
        FilteredList<User> filteredData = UserSearch.getFilteredList(getUserRegister().getRegister(), userSearch.getText());
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblViewUser.comparatorProperty());
        tblViewUser.setItems(sortedData);
    }

    public UserRegister getUserRegister(){
        return Model.INSTANCE.getUserRegister();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //user lagd av inputfields
        //getUserRegister().addBruker();

    registryUserLogic = new RegistryUserLogic(userReg);
    updateView();
    }
}
