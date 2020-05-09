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
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.RegistryComponentLogic;
import org.programutvikling.gui.RegistryUserLogic;
import org.programutvikling.gui.UserPopupController;
import org.programutvikling.model.TemporaryUser;

import java.io.IOException;

public class UserWindowHandler {

    RegistryUserLogic registryUserLogic;

    public void openEditWindow(TableRow<? extends Clickable> row, GridPane gridpane) throws IOException {
        registryUserLogic = new RegistryUserLogic(gridpane);
        CreatePopupWindow.getStageForPopup(row, "userPopup.fxml", registryUserLogic);
    }
}
