package org.programutvikling.gui.utility;

import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.RegistryComponentLogic;

import java.io.IOException;

public class WindowHandler {

    RegistryComponentLogic registryComponentLogic;

    public void openEditWindow(TableRow<? extends Clickable> row, GridPane gridpane) throws IOException {
        registryComponentLogic = new RegistryComponentLogic(gridpane);
        CreatePopupWindow.getStageForPopup(row, "editPopup.fxml", registryComponentLogic);
    }

}
