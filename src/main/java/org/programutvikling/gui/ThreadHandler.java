package org.programutvikling.gui;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.iothread.InputThread;
import org.programutvikling.user.UserPreferences;

import java.util.ArrayList;

public class ThreadHandler {
    Stage stage;
    GridPane gridPane;
    InputThread inputThread;
    SecondaryController controller;
    private InputThread task;
    private ArrayList<Object> objectsLoaded = new ArrayList<>();

    ThreadHandler(Stage stage, GridPane gridPane, SecondaryController controller) {
        this.stage = stage;
        this.gridPane = gridPane;
        this.controller = controller;
    }

/*
    void openFileWithThreadSleep(ComponentRegister componentRegister, UserPreferences userPreferences) {
        inputThread = new InputThread(componentRegister, userPreferences.getPathToUser());
        inputThread.setOnSucceeded(this::threadDone);
        inputThread.setOnFailed(this::threadFailed);
        startThread(inputThread);
    }
*/
    ArrayList<Object> openInputThread(ComponentRegister componentRegister, String s) {
        task = new InputThread(componentRegister, s);
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        startThread(task);
        return task.getValue();
    }

    void startThread(InputThread task) {
        Thread th = new Thread(task);
        th.setDaemon(true);
        controller.disableGUI();
        //gridPane.setDisable(true);//prøver å slå av hele gridpane
        th.start();
        task.call();  //call bruker filepathen fra konstruktøren til å åpne/laste inn
    }

    ArrayList<Object> threadDone(WorkerStateEvent e) {
        Dialog.showSuccessDialog("Opening complete");
        //btnLeggTil.getclass.setDisable(false);
        controller.enableGUI();
        //objectsLoaded.addAll(task.getValue());
        //btnSaveID.setDisable(false);
        //ComponentRegister componentRegisterInn =
        //her bør man instansiere objectsForSaving ??? aner ikke hva som er best måte
        return task.getValue();
    }

    void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        Dialog.showErrorDialog("Avviket sier: " + e.getMessage());
        controller.enableGUI();
    }

}
