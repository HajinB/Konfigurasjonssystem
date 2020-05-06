package org.programutvikling.model;

import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerRegister;
import org.programutvikling.gui.FileHandling;
import org.programutvikling.model.io.FileOpenerTxt;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public enum ModelEndUser {
    INSTANCE;

    private ModelEndUser(){
        loadComputerRegisterFromDirectory();
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        System.out.println("modelenduser"+endUserObjects);
        FileHandling.openObjects(endUserObjects,
                Model.INSTANCE.getUserPreferences().getPathPathToUserComputer().toString());
        try {
            fileOpenerTxt.open(computer, Paths.get(Model.INSTANCE.getUserPreferences().getPathPathToUserComputer().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("modelenduser etter "+endUserObjects);
        System.out.println(TemporaryComponent.INSTANCE.errorListToString());
//kan ikke kjøre denne metoden fra en annen klasse -
        // konstruktøren må
        // holdes
        // privat
    }

    public void loadComputerIntoClass() {
    }

    public ArrayList<Object> getEndUserObjects() {
        return endUserObjects;
    }

    private ArrayList<Object> endUserObjects = new ArrayList<>();
    private ComputerRegister computerRegister = new ComputerRegister();
    private Computer computer = new Computer("current");


    public ComputerRegister getComputerRegister() {
        return computerRegister;
    }

    public Computer getComputer() {
        return computer;
    }

    private void loadComputerRegisterFromDirectory() {
        //FileHandling.findComputers


        ArrayList<Computer> computers =   FileHandling.findComputers();
        for(Object c : computers){
            computerRegister.getObservableRegister().add((Computer) c);
        }

    }
}
