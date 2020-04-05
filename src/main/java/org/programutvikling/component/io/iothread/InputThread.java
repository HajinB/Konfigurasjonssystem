package org.programutvikling.component.io.iothread;

import javafx.concurrent.Task;

import java.io.IOException;
import java.nio.file.*;


import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.FileHandling;

import static java.lang.Thread.sleep;

public class InputThread extends Task<Void> {

    FileHandling fileHandling = new FileHandling();

    String filePath;
    //ArrayList<Component> komponentRegisterList = new ArrayList<>();
    ComponentRegister componentRegisterThread = new ComponentRegister();
    //componentregister blir ikke her faktisk lagra gjennom

    public InputThread(ComponentRegister komponentRegister, String filepath) {
        this.filePath = filepath;
        componentRegisterThread.getRegister().addAll(komponentRegister.getRegister());
        call();
    }

    public void openFileThread(ComponentRegister komponentregister, Path filePath) {

    }


    @Override
    public Void call() {

        try {
            sleep(3000);
            fileHandling.loadAllFilesFromDirectory(componentRegisterThread, Paths.get(filePath));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
