package org.programutvikling.model;

import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerRegister;
import org.programutvikling.gui.FileHandling;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.io.FileOpenerTxt;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public enum ModelEndUser {
    INSTANCE;

    public ArrayList<Object> getEndUserObjects() {
        return endUserObjects;
    }
    private ArrayList<Object> endUserObjects = new ArrayList<>();
    private ComputerRegister computerRegister = new ComputerRegister();
    private Computer computer = new Computer("current");

    private ModelEndUser(){
        loadCompleteComputers();
        loadComputer();
    }

    private void loadComputer() {
        FileHandling.openCart(computer);
    }

    public ComputerRegister getComputerRegister() {
        return computerRegister;
    }

    public Computer getComputer() {
        return computer;
    }

    private void loadCompleteComputers() {
        //FileHandling.findComputers
        ArrayList<Computer> computers =   FileHandling.findComputers();
        for(Object c : computers){
            computerRegister.getObservableRegister().add((Computer) c);
        }

    }
}
