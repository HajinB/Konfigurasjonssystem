package org.programutvikling.domain.utility;

import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.computer.Computer;

public class ComputerFactory {

    public Computer computerFactory(ComponentRegister componentRegister, String name){
        Computer computer = new Computer(name);
        computer.getComponentRegister().getRegister().addAll(componentRegister.getRegister());
        return computer;
    }
}
