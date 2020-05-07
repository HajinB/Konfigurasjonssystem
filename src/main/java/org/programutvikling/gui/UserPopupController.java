package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.domain.user.User;
import org.programutvikling.gui.customTextField.NoSpacebarField;
import org.programutvikling.gui.customTextField.ZipField;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.TemporaryUser;

import java.io.Serializable;

public class UserPopupController extends TabUsersController implements Serializable {

    @FXML
    private GridPane userEditNode;
    @FXML
    private Label lblRedigerBruker;
    @FXML
    private CheckBox cbAdmin;
    @FXML
    private NoSpacebarField userUsername;
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextField name;
    @FXML
    private NoSpacebarField email;
    @FXML
    private TextField address;
    @FXML
    private ZipField userZip;
    @FXML
    private TextField UserCity;
    @FXML
    private Button btnEditUser;

    Stage stage;

    private void editUser() {
        if (areInputFieldsEmpty()) {
            Dialog.showErrorDialog("Fyll inn alle felt!");
            return;
        }
        User user = null;
        try {
            user = new User(cbAdmin.isSelected(), userUsername.getText(), userPassword.getText(), name.getText(), email.getText(), address.getText(), userZip.getText(), UserCity.getText());
        } catch (IllegalArgumentException illegalArgumentException) {
            Dialog.showErrorDialog(illegalArgumentException.getMessage());
        }
        TemporaryUser.INSTANCE.setEdited(true);
        TemporaryUser.INSTANCE.storeTempUser(user);
    }


    private void btnEditUser(ActionEvent event) {
        editUser();
    }

    private RegistryUserLogic registryUserLogic;

    public void initData(User u, Stage stage, int columnIndex) {
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
    }
    private boolean areInputFieldsEmpty() {
        return (((TextField) userEditNode.lookup("#userUsername")).getText().isEmpty() || ((TextField) userEditNode.lookup("#userPassword")).getText().isEmpty()
                || ((TextField) userEditNode.lookup("#userName")).getText().isEmpty() || ((TextField) userEditNode.lookup("#userMail")).getText().isEmpty()
                || ((TextField) userEditNode.lookup("#userAddress")).getText().isEmpty() || ((ZipField) userEditNode.lookup("#userZip")).getText().isEmpty()
                || ((TextField) userEditNode.lookup("#userCity")).getText().isEmpty());

    }
}
