package org.programutvikling.gui.utility;

import javafx.scene.control.TableRow;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.controllers.RegistryComponentLogic;

import java.io.IOException;

public class WindowHandler {

    RegistryComponentLogic registryComponentLogic;

    public void openEditWindow(TableRow<? extends Clickable> row, GridPane gridpane) throws IOException {
        registryComponentLogic = new RegistryComponentLogic(gridpane);
        CreatePopupWindow.getStageForPopup(row, "editPopup.fxml", registryComponentLogic);
    }

}
