package org.programutvikling.gui.utility;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.*;
import org.programutvikling.model.TemporaryComponent;

import java.io.IOException;
import java.util.Objects;

public class CreatePopupWindow {

    static void getStageForPopup(TableRow<? extends Clickable> row, String fxml, Stageable registryComponentLogic) throws IOException {
        if(row.isEmpty()){
            return;
        }
        FXMLLoader loader =  FXMLGetter.fxmlLoaderFactory(fxml);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        Clickable clickable = getRowObject(row);
        Objects.requireNonNull(createController(fxml, loader)).initData(clickable, stage, TemporaryComponent.INSTANCE.getColumnIndex());
        stage.show();
        handlePopUp(stage, clickable, registryComponentLogic);
    }

    private static Clickable getRowObject(TableRow<? extends Clickable> row) {
        Clickable g;
        if(row.getItem() instanceof Component){
           g = (Component) row.getItem();
        }else{
           g = (User) row.getItem();
        }
        return g;
    }

    private static IController createController(String fxml, FXMLLoader loader) {
        if(fxml.equals("editPopup.fxml")) {
            IController editPopupController =
                    (IController) loader.<EditPopupController>getController();
            //editPopupController.initData(c, stage, TemporaryComponent.INSTANCE.getColumnIndex());
            return editPopupController;
        }
        else if(fxml.equals("userPopup.fxml")) {
            IController userPopupController =
                    (IController) loader.<UserPopupController>getController();
            //editPopupController.initData(c, stage, TemporaryComponent.INSTANCE.getColumnIndex());
            return userPopupController;
        }
        return null;
        }



    static void handlePopUp(Stage stage, Clickable c, Stageable registryComponentLogic) {
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                registryComponentLogic.editClickableFromPopup(c);
            }
        });
    }
}
