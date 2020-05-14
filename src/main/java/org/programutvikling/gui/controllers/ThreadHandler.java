package org.programutvikling.gui.controllers;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.programutvikling.App;
import org.programutvikling.domain.io.iothread.InputThread;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.model.Model;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ThreadHandler {
    TabComponentsController controller;

    public ThreadHandler(TabComponentsController controller) {
        this.controller = controller;
    }

    public static void loadInThread(Task<Boolean> task) {
        Alert alert = Dialog.getLoadingDialog();
        task.setOnRunning((e) -> alert.showAndWait());
        task.setOnSucceeded((e) -> {
            alert.close();
            try {
                Boolean returnValue = task.get();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        });
        task.setOnFailed((e) -> {
        });
        new Thread(task).start();
    }

    public static Task<Boolean> getTask() {
        return new Task<Boolean>() {
            @Override
            public Boolean call() throws IOException, InterruptedException {
                //her skjer actionen
                Thread.sleep(2000);
                //App.setRoot("secondary");
                /** gjør tingen her: aka lad inn ting**/
                App.setRoot("superUser");
                //dette er det første kallet til Model.INSTANCE, da kjøres konstruktøren, som betyr at dataen lastes
                // fra fil til Model.
                System.out.println("Loader adminModel" + Model.INSTANCE);
                // FileUtility.populateRecentFiles("AppFiles/Database/Admin/ComponentRegisters");
                FileUtility.populateRecentFilesVoid();
                return true;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
            }
        };
    }

    public void openInputThread(String path, RegistryComponentLogic registryComponentLogic) {
        InputThread task = new InputThread(path, registryComponentLogic);
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        startThread(task);
    }

    public void startThread(InputThread task) {
        Thread th = new Thread(task);
        th.setDaemon(true);
        controller.disableGUI();
        th.start();
        task.call();
    }

    public void threadDone(WorkerStateEvent e) {
        System.out.println("thread done");
        Dialog.showSuccessDialog("Opening complete");
        controller.enableGUI();
    }

    public void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        Dialog.showErrorDialog("Avviket sier: " + e.getMessage());
        controller.enableGUI();
    }

}
