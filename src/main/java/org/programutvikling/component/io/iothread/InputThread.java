package org.programutvikling.component.io.iothread;

import javafx.concurrent.Task;

import java.util.ArrayList;


import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.FileHandling;

import static java.lang.Thread.sleep;

public class InputThread extends Task<ArrayList<Object>> {

    FileHandling fileHandling = new FileHandling();

    String filePath;
    private ArrayList<Object> componentRegisterList = new ArrayList<>();
    ComponentRegister componentRegisterThread;

    public InputThread(ComponentRegister listIn, String path) {
        this.componentRegisterList.add(listIn);
        this.filePath = path;
        call();
    }


    @Override
    public ArrayList<Object> call() {

        try {

            FileHandling.OpenSelectedComputerTxtFiles(componentRegisterList, filePath);
            sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return componentRegisterList;
    }
}
