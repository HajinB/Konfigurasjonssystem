package org.programutvikling.gui;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.iothread.InputThread;

import java.io.File;

public class ThreadHandler {
    Stage stage;
    GridPane gridPane;

    ThreadHandler(Stage stage, GridPane gridPane){
        this.stage = stage;
        this.gridPane = gridPane;
    }

}
