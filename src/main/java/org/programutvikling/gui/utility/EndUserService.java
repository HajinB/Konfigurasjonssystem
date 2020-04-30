package org.programutvikling.gui.utility;

import org.programutvikling.component.ComponentRegister;
import org.programutvikling.Model.Model;

public class EndUserService {
    ComponentRegister cabinetRegister = new ComponentRegister();
    ComponentRegister processorRegister = new ComponentRegister();
    ComponentRegister videoRegister = new ComponentRegister();
    ComponentRegister memoryRegister = new ComponentRegister();
    ComponentRegister hardDiscRegister = new ComponentRegister();
    ComponentRegister motherboardRegister = new ComponentRegister();
    ComponentRegister keyboardRegister = new ComponentRegister();
    ComponentRegister otherRegister = new ComponentRegister();
    ComponentRegister mouseRegister = new ComponentRegister();
    ComponentRegister screenRegister = new ComponentRegister();

    //kunne man ha gjort dette med et componentregister-list? altså list som har

    public EndUserService() {
        updateEndUserRegisters();
    }

    public ComponentRegister getProcessorRegister() {
        return processorRegister;
    }

    public ComponentRegister getVideoRegister() {
        return videoRegister;
    }

    public ComponentRegister getMemoryRegister() {
        return memoryRegister;
    }

    public ComponentRegister getHardDiscRegister() {
        return hardDiscRegister;
    }

    public ComponentRegister getCabinetRegister() {
        return cabinetRegister;
    }

    public ComponentRegister getMotherboardRegister() {
        return motherboardRegister;
    }

    public ComponentRegister getMouseRegister() {
        return mouseRegister;
    }

    public ComponentRegister getScreenRegister() {
        return screenRegister;
    }



    public ComponentRegister getKeyboardRegister() {
        return keyboardRegister;
    }

    public ComponentRegister getOtherRegister() {
        return otherRegister;
    }

    public void updateEndUserRegisters() {
        clearRegisters(motherboardRegister, cabinetRegister, videoRegister, screenRegister, mouseRegister,
                processorRegister, memoryRegister, hardDiscRegister, otherRegister, keyboardRegister);
        //kan lage en componentregisterList istedet? vil det redusere codespam?
        ComponentRegister dataBaseRegister = Model.INSTANCE.getComponentRegister();
        motherboardRegister.getRegister().addAll(dataBaseRegister.filterByProductType("hovedkort"));
        cabinetRegister.getRegister().addAll(dataBaseRegister.filterByProductType("kabinett"));
        screenRegister.getRegister().addAll(dataBaseRegister.filterByProductType("skjerm"));
        mouseRegister.getRegister().addAll(dataBaseRegister.filterByProductType("mus"));
        processorRegister.getRegister().addAll(dataBaseRegister.filterByProductType("prosessor"));
        videoRegister.getRegister().addAll(dataBaseRegister.filterByProductType("skjermkort"));
        memoryRegister.getRegister().addAll(dataBaseRegister.filterByProductType("minne"));
        hardDiscRegister.getRegister().addAll(dataBaseRegister.filterByProductType("harddisk"));
        keyboardRegister.getRegister().addAll(dataBaseRegister.filterByProductType("tastatur"));
        otherRegister.getRegister().addAll(dataBaseRegister.filterByProductType("annet"));
    }


    private void clearRegisters(ComponentRegister... c) {
        for (ComponentRegister i : c) {
            i.getRegister().clear();
        }
    }
}
