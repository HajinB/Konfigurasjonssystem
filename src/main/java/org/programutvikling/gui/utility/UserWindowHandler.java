package org.programutvikling.gui.utility;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.programutvikling.domain.user.User;
import org.programutvikling.gui.EditPopupController;
import org.programutvikling.gui.RegistryUserLogic;
import org.programutvikling.gui.UserPopupController;
import org.programutvikling.model.TemporaryComponent;
import org.programutvikling.model.TemporaryUser;

import java.io.IOException;

public class UserWindowHandler {

    RegistryUserLogic registryUserLogic;

    public void openEditWindow(TableRow<? extends User> row, GridPane gridpane) throws IOException {
        registryUserLogic = new RegistryUserLogic(gridpane);
        if(row.isEmpty()){
            return;
        }
        FXMLLoader loader =  FXMLGetter.fxmlLoaderFactory("editPopup.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        User u = row.getItem();
        UserPopupController popupController =
                loader.<EditPopupController>getController();
        popupController.initData(u, stage, TemporaryUser.INSTANCE.getColumnIndex());
        stage.show();
        handlePopUp(stage, u);
    }

    void handlePopUp(Stage stage, User u) {
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                registryUserLogic.editUserFromPopup(u);
            }
        });
    }

}
