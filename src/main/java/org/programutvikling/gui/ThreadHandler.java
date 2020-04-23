package org.programutvikling.gui;

import javafx.concurrent.WorkerStateEvent;
import org.programutvikling.component.io.iothread.InputThread;
import org.programutvikling.gui.utility.Dialog;

public class ThreadHandler {
    TabComponentsController controller;

    ThreadHandler(TabComponentsController controller) {
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
        th.start();
        task.call();
    }

    void threadDone(WorkerStateEvent e) {
        System.out.println("thread done");
        Dialog.showSuccessDialog("Opening complete");
        controller.enableGUI();
    }

    void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        Dialog.showErrorDialog("Avviket sier: " + e.getMessage());
        controller.enableGUI();
    }

}
