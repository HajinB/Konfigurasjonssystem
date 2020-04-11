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
    //ComponentRegister componentRegisterThread;
    ArrayList<Object> objects = new ArrayList<>();

    public InputThread(ArrayList<Object> objects, String filepath) {
        this.filePath = filepath;
        this.objects.clear();
        this.objects.add(objects);
        call();
    }

    @Override
    public Void call() {

        try {
            fileHandling.loadSelectedFile(objects, filePath);
            sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;   //er det bedre å returnere en verdi enn å
    }
}
