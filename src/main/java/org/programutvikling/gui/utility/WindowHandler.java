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
import org.programutvikling.domain.component.Component;
import org.programutvikling.gui.EditPopupController;
import org.programutvikling.gui.RegistryComponentLogic;
import org.programutvikling.gui.TabComponentsController;
import org.programutvikling.model.TemporaryComponent;

import java.io.IOException;

public class WindowHandler {

    RegistryComponentLogic registryComponentLogic;

    public void openEditWindow(TableRow<? extends Component> row, GridPane gridpane) throws IOException {
        registryComponentLogic = new RegistryComponentLogic(gridpane);
        if(row.isEmpty()){
            return;
        }
        FXMLLoader loader =  FXMLGetter.fxmlLoaderFactory("editPopup.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        Component c = row.getItem();
        EditPopupController popupController =
                loader.<EditPopupController>getController();
        popupController.initData(c, stage, TemporaryComponent.INSTANCE.getColumnIndex());
        stage.show();
        handlePopUp(stage, c);

    }
    void handlePopUp(Stage stage, Component c) {
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                registryComponentLogic.editComponentFromPopup(c);
            }
        });
    }

}
