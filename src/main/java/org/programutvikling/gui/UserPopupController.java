package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentTypes;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.customTextField.NoSpacebarField;
import org.programutvikling.gui.customTextField.ZipField;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.IController;
import org.programutvikling.model.TemporaryUser;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserPopupController extends TabUsersController implements Initializable, IController {

    @FXML
    private GridPane userEditNode;
    @FXML
    private Label lblRedigerBruker;
    @FXML
    private CheckBox cbAdmin;
    @FXML
    private NoSpacebarField userUsername, email;
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextField name, address, UserCity;
    @FXML
    private ZipField userZip;
    @FXML
    private Button btnEditUser;

    private RegistryUserLogic registryUserLogic;

    Stage stage;

    private void editUser() {
        if (areInputFieldsEmpty()) {
            Dialog.showErrorDialog("Fyll inn alle felt!");
            return;
        }
        User user = null;
        try {
            user = new User(cbAdmin.isSelected(), userUsername.getText(), userPassword.getText(), name.getText(), email.getText(), address.getText(), userZip.getText(), UserCity.getText());
            System.out.println(user);
        } catch (IllegalArgumentException illegalArgumentException) {
            Dialog.showErrorDialog(illegalArgumentException.getMessage());
            return;
        }
        TemporaryUser.INSTANCE.setEdited(true);
        TemporaryUser.INSTANCE.storeTempUser(user);
        stage.close();
    }

    @FXML
    private void btnEditUser(ActionEvent event) {
        editUser();
    }

    @Override
    public void initData(Clickable c, Stage stage, int columnIndex) {
        System.out.println("kolonne for userpopup: "+ columnIndex);
        User u = (User) c;
        //tar inn stage for å kunne lukke når brukeren trykker endre
        RegistryUserLogic registryUserLogic = new RegistryUserLogic(userEditNode);
//        registryUserLogic.setTextAreaListener(userEditNode);
        this.stage = stage;
        System.out.println(userEditNode);
        cbAdmin.setSelected(u.getAdmin());
        userUsername.setText(u.getUsername());
        userPassword.setText(u.getPassword());
        name.setText(u.getName());
        email.setText(u.getEmail());
        address.setText(u.getAddress());
        userZip.setText(u.getZip());
        UserCity.setText(u.getCity());
        setFocusOnField(columnIndex, u);
    }

    private void setFocusOnField(int columnIndex, User u) {
        System.out.println("Kolonne index er: " + columnIndex);

        if (columnIndex == 0) {
            cbAdmin.requestFocus();
        }
        if (columnIndex == 1) {
            userUsername.requestFocus();
            userUsername.selectAll();
        }
        if (columnIndex == 2) {
            userPassword.requestFocus();
            userPassword.selectAll();
        }
        if (columnIndex == 3) {
            name.requestFocus();
            name.selectAll();
        }
        if (columnIndex == 4) {
            email.requestFocus();
            email.selectAll();
        }
        if (columnIndex == 5) {
            address.requestFocus();
            address.selectAll();
        }
        else if(columnIndex == 6) {
            userZip.requestFocus();
            userZip.selectAll();
        } else if(columnIndex == 7) {
            UserCity.requestFocus();
            UserCity.selectAll();
        }
    }
    private boolean areInputFieldsEmpty() {
        return (((TextField) userEditNode.lookup("#userUsername")).getText().isEmpty() || ((TextField) userEditNode.lookup("#userPassword")).getText().isEmpty()
                || ((TextField) userEditNode.lookup("#userName")).getText().isEmpty() || ((TextField) userEditNode.lookup("#userMail")).getText().isEmpty()
                || ((TextField) userEditNode.lookup("#userAddress")).getText().isEmpty() || ((ZipField) userEditNode.lookup("#userZip")).getText().isEmpty()
                || ((TextField) userEditNode.lookup("#userCity")).getText().isEmpty());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
