package org.programutvikling.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserRegister;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.utility.UserSearch;
import org.programutvikling.gui.utility.UserWindowHandler;
import org.programutvikling.model.ModelUserRegister;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TabUsersController implements Initializable, Clickable {

    final Tooltip tooltip = new Tooltip("Dobbeltklikk en celle for å redigere");
    public Label lblMsgPassword, lblMsgUsername, lblMsgZip, lblMsgEmail, lblMsgCity, lblMsgAdress, lblMsgName;
    UserWindowHandler userWindowHandler = new UserWindowHandler();
    @FXML
    private CheckBox cbAdmin;
    @FXML
    private GridPane userReg;
    @FXML
    private Label lblUserMsg;
    @FXML
    private TextField userSearch;
    @FXML
    private ChoiceBox<String> cbAdminFilter;
    @FXML
    private TableView<User> tblViewUser;

    private RegistryUserLogic registryUserLogic;
    private SuperUserController superUserController;

    @FXML
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

    public void updateView() {
        getUserRegister().attachTableView(tblViewUser);
    }

    @FXML
    void dblClickTblRow(MouseEvent event) {
        registryUserLogic.handlePopup(tblViewUser, userWindowHandler, userReg);
    }

    @FXML
    void search(KeyEvent event) {
        if (cbAdminFilter.getValue().equals("Ingen filter") || cbAdminFilter.getValue() == null) {
            setSearchedList();
        } else {
            tblViewUser.setItems(getSearchedAndFilteredList());
        }
    }

    ObservableList<User> getSearchedAndFilteredList() {
        return UserSearch.getFilteredList(getUserRegister()
                .filterByAdmin(cbAdminFilter.getValue()), userSearch.getText());
    }


    private void setSearchedList() {
        FilteredList<User> filteredData = UserSearch.getFilteredList(getUserRegister().getRegister(), userSearch.getText());
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblViewUser.comparatorProperty());
        tblViewUser.setItems(sortedData);
    }

    @FXML
    private void filterByAdmin() {
        filter();
    }

    private ObservableList<User> filter() {
        if (cbAdminFilter.getValue().equals("Ingen filter")) {
            updateView();
            return getUserRegister().getRegister();
        }
        ObservableList<User> result = getResultFromTypeFilter();
        tblViewUser.setItems(Objects.requireNonNullElseGet(result, FXCollections::observableArrayList));
        return result;
    }

    private ObservableList<User> getResultFromTypeFilter() {
        ObservableList<User> result = null;
        String filterString = cbAdminFilter.getValue();
        result = getUserRegister().filterByAdmin(filterString);
        return result;
    }

    public UserRegister getUserRegister() {
        return ModelUserRegister.INSTANCE.getUserRegister();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //user lagd av inputfields
        //getUserRegister().addBruker();
        registryUserLogic = new RegistryUserLogic(userReg, this);
        initChoiceBox();
        updateView();
        tblViewUser.setOnMouseClicked((MouseEvent event) -> tblViewUser.sort());
        tblViewUser.setTooltip(tooltip);
        clearLabels();
    }

    private void initChoiceBox() {
        cbAdminFilter.getItems().addAll("Ingen filter", "Admin", "Bruker");
        cbAdminFilter.setValue("Ingen filter");
    }

    public void init(SuperUserController superUserController) {
        this.superUserController = superUserController;
    }

    void setLblMsgPassword(String s) {
        lblMsgPassword.setText(s);
    }

    void setLblMsgUsername(String s) {
        lblMsgUsername.setText(s);
    }

    void setLblMsgZip(String s) {
        this.lblMsgZip.setText(s);
    }

    public void setLblMsgEmail(String s) {
        this.lblMsgEmail.setText(s);
    }

    void setLblMsgCity(String s) {
        this.lblMsgCity.setText(s);
    }

    void setLblMsgAdress(String s) {
        this.lblMsgAdress.setText(s);
    }

    void setLblMsgName(String s) {
        this.lblMsgName.setText(s);
    }

    public void clearLabels() {
        setLblMsgAdress("");
        setLblMsgCity("");
        setLblMsgName("");
        setLblMsgEmail("");
        setLblMsgPassword("");
        setLblMsgUsername("");
        setLblMsgZip("");
    }
}
