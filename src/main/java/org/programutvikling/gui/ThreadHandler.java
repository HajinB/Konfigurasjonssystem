package org.programutvikling.gui;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.component.io.iothread.InputThread;

public class ThreadHandler {
    Stage stage;
    GridPane gridPane;
    TabComponentsController controller;
    ProgressBar progressBar = new ProgressBar();

    ThreadHandler(Stage stage, GridPane gridPane, TabComponentsController controller) {
        this.stage = stage;
        this.gridPane = gridPane;
        this.controller = controller;
    }

    void openInputThread(String path) {
        InputThread task = new InputThread(path);
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        startThread(task);
    }

    void startThread(InputThread task) {
        Thread th = new Thread(task);
        th.setDaemon(true);
        controller.disableGUI();
        //gridPane.setDisable(true);//prøver å slå av hele gridpane
        progressBar.setVisible(true);
        th.start();
        task.call();  //call bruker filepathen fra konstruktøren til å åpne/laste inn
    }

    void threadDone(WorkerStateEvent e) {
        System.out.println("thread done");
        Dialog.showSuccessDialog("Opening complete");
        //btnLeggTil.getclass.setDisable(false);
        controller.enableGUI();
        progressBar.setVisible(false);
        //task.getValue();
        //ContextModel.getInstance().getCleanObjectList().addAll(task.getValue());
        //ContextModel.getInstance().loadObjectsIntoClasses();
        //her bør man instansiere objectsForSaving ??? aner ikke hva som er best måte
    }

    void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        Dialog.showErrorDialog("Avviket sier: " + e.getMessage());
        controller.enableGUI();
    }

}
