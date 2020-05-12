package org.programutvikling.gui.logic;

import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.model.Model;

public class EndUserService {
    ComponentRegister cabinetRegister = new ComponentRegister();
    ComponentRegister processorRegister = new ComponentRegister();
    ComponentRegister videoCardRegister = new ComponentRegister();
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
    public void updateEndUserRegisters() {
        clearRegisters(motherboardRegister, cabinetRegister, videoCardRegister, screenRegister, mouseRegister,
                processorRegister, memoryRegister, hardDiscRegister, otherRegister, keyboardRegister);
        //kan lage en componentregisterList istedet? vil det redusere codespam?
        addByName("hovedkort", motherboardRegister);
        addByName("kabinett", cabinetRegister);
        addByName("skjerm", screenRegister);
        addByName("mus", mouseRegister);
        addByName("prosessor", processorRegister);
        addByName("skjermkort", videoCardRegister);
        addByName("minne", memoryRegister);
        addByName("harddisk", hardDiscRegister);
        addByName("tastatur", keyboardRegister);
        addByName("annet", otherRegister);
    }

    public void addByName(String s, ComponentRegister componentRegister){
        ComponentRegister dataBaseRegister = Model.INSTANCE.getComponentRegister();
        componentRegister.getRegister().addAll(dataBaseRegister.createListByType(s));
    }

    private void clearRegisters(ComponentRegister... c) {
        for (ComponentRegister i : c) {
            i.getRegister().clear();
        }
    }

    public ComponentRegister getProcessorRegister() {
        return processorRegister;
    }

    public ComponentRegister getVideoCardRegister() {
        return videoCardRegister;
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


}
