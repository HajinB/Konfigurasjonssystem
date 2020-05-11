package org.programutvikling.model;

import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerRegister;
import org.programutvikling.gui.controllers.FileHandling;

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
        for(Computer c : computers){
            if(c.calculatePrice()!=0) {
                computerRegister.getObservableRegister().add((Computer) c);
            }
        }

    }
}
