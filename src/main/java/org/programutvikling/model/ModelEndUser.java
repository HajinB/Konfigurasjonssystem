package org.programutvikling.model;

import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerRegister;
import org.programutvikling.gui.controllers.FileHandling;

import java.util.ArrayList;

public enum ModelEndUser {
    INSTANCE;

    private ComputerRegister computerRegister = new ComputerRegister();
    private Computer computer = new Computer("current");

    private ModelEndUser() {
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

    public void loadCompleteComputers() {
        //FileHandling.findComputers
        computerRegister.getObservableRegister().clear();
        ArrayList<Computer> computers = FileHandling.findComputers();
        for (Computer c : computers) {
            if (c.calculatePrice() != 0) {
                computerRegister.getObservableRegister().add((Computer) c);
            }
        }
    }
}
