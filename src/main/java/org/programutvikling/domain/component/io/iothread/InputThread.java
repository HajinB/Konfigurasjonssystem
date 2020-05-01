package org.programutvikling.domain.component.io.iothread;

import javafx.concurrent.Task;

import java.util.ArrayList;


import org.programutvikling.model.Model;
import org.programutvikling.gui.FileHandling;

import static java.lang.Thread.sleep;

public class InputThread extends Task<ArrayList<Object>> {

    String filePath;

    public InputThread(String path) {
        this.filePath = path;
        call();
    }
    @Override
    public ArrayList<Object> call() {

        try {
            sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return FileHandling.openObjects(Model.INSTANCE.getCleanObjectList(), filePath);
    }
}
