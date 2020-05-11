package org.programutvikling.gui.utility;

import javafx.scene.control.TableRow;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.controllers.RegistryUserLogic;

import java.io.IOException;

public class UserWindowHandler {

    RegistryUserLogic registryUserLogic;

    public void openEditWindow(TableRow<? extends Clickable> row, GridPane gridpane) throws IOException {
        registryUserLogic = new RegistryUserLogic(gridpane);
        CreatePopupWindow.getStageForPopup(row, "userPopup.fxml", registryUserLogic);
    }
}
