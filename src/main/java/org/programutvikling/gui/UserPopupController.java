package org.programutvikling.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.domain.user.User;

import java.io.Serializable;

public class UserPopupController extends TabUsersController implements Serializable {
    @FXML
    private GridPane userEditNode;
    @FXML
    private ChoiceBox<String> cbAdmin;

    Stage stage;



    public void initData(User u, Stage stage, int columnIndex) {
        //tar inn stage for å kunne lukke når brukeren trykker endre
        RegistryUserLogic registryUserLogic = new RegistryUserLogic(userEditNode);
//        registryUserLogic.setTextAreaListener(userEditNode);
        this.stage = stage;
        System.out.println(userEditNode);
        cbAdmin.setValue(u.getAdminString());

        txtPopupProductName.setText(u.getProductName());
        txtPopupProductDescription.setText(u.getProductDescription());
        txtPopupProductPrice.setText(Double.toString(u.getProductPrice()));
        setFocusOnField(columnIndex, u);
    }
}
