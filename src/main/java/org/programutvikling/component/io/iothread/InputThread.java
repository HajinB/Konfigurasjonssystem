package org.programutvikling.component.io.iothread;

import javafx.concurrent.Task;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;


import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.FileHandling;

import static java.lang.Thread.sleep;

public class InputThread extends Task<Void> {

    FileHandling fileHandling = new FileHandling();

    String filePath;
    ArrayList<Component> componentRegisterList = new ArrayList<>();
    ComponentRegister componentRegisterThread;

    public InputThread(ComponentRegister componentRegisterInn, String filepath) {
        this.filePath = filepath;
        this.componentRegisterThread = componentRegisterInn;
        call();
    }

    @Override
    public Void call() {

        try {
            fileHandling.loadSelectedFile(componentRegisterThread, filePath);
            sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
