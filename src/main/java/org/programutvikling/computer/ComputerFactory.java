package org.programutvikling.computer;

import org.programutvikling.component.ComponentRegister;

public class ComputerFactory {

    public Computer computerFactory(ComponentRegister componentRegister, String name){
        Computer computer = new Computer(name);
        computer.getComponentRegister().getRegister().addAll(componentRegister.getRegister());
        return computer;
    }
}
