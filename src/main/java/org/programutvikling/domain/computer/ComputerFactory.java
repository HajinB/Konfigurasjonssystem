package org.programutvikling.domain.computer;

import org.programutvikling.domain.component.ComponentRegister;

public class ComputerFactory {

    public Computer computerFactory(ComponentRegister componentRegister, String name){
        Computer computer = new Computer(name);
        computer.getComponentRegister().getRegister().addAll(componentRegister.getRegister());
        return computer;
    }
}
