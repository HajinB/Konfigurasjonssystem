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
import org.programutvikling.App;
import org.programutvikling.domain.user.User;
import org.programutvikling.gui.RegistryUserLogic;
import org.programutvikling.gui.UserPopupController;
import org.programutvikling.model.TemporaryUser;

import java.io.IOException;

public class UserWindowHandler {

    RegistryUserLogic registryUserLogic;

    public void openEditWindow(TableRow<? extends User> row, GridPane gridpane) throws IOException {
        registryUserLogic = new RegistryUserLogic(gridpane);
        if(row.isEmpty()){
            System.out.println("er row empty?");
            return;
        }
        FXMLLoader loader =  FXMLGetter.fxmlLoaderFactory("userPopup.fxml");

        //FXMLLoader loader = App.loadFXML("userPopup.fxml");
        System.out.println(loader.toString());
        System.out.println(loader.getLocation());
        System.out.println(loader.getClass());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        User u = row.getItem();
        UserPopupController popupController =
                loader.<UserPopupController>getController();

        popupController.init(u, stage, TemporaryUser.INSTANCE.getColumnIndex());
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
