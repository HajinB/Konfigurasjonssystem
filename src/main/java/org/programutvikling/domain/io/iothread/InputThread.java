package org.programutvikling.domain.io.iothread;

import javafx.concurrent.Task;

import java.util.ArrayList;


import org.programutvikling.gui.controllers.RegistryComponentLogic;
import org.programutvikling.model.Model;
import org.programutvikling.gui.controllers.FileHandling;

import static java.lang.Thread.sleep;
import static javafx.application.Platform.runLater;

public class InputThread extends Task<ArrayList<Object>> {

    String filePath;
    RegistryComponentLogic registryComponentLogic;


    public InputThread(String path, RegistryComponentLogic registryComponentLogic) {
        this.filePath = path;
        this.registryComponentLogic = registryComponentLogic;
        call();
    }
    @Override
    public ArrayList<Object> call() {

        try {
            sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("filepath for openrecent: "+filePath);
        if(filePath.endsWith(".txt")){
            //bruker runlater for å unngå concurrent modification
            runLater(() -> {
                registryComponentLogic.openComputer(filePath);
            });
            return null;
        }
        return FileHandling.openObjects(Model.INSTANCE.getCleanObjectList(), filePath);
    }
}
