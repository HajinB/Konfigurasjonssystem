package org.programutvikling.component.io;

import javafx.concurrent.Task;

import java.io.IOException;
import java.nio.file.*;


import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.FileHandling;

import static java.lang.Thread.sleep;

public class InputThread extends Task<Void> {

    FileHandling fileHandling = new FileHandling();

    String filePath;
    ComponentRegister komponentRegister;

    public InputThread(ComponentRegister komponentRegister, String filepath) {
        this.filePath = filepath;
        this.komponentRegister = komponentRegister;
        call();
    }

    public void openFileThread(ComponentRegister komponentregister, Path filePath) {

    }


    @Override
    public Void call() {

        try {
            fileHandling.loadAllFilesFromDirectory(komponentRegister, Paths.get(filePath));
            sleep(3000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
