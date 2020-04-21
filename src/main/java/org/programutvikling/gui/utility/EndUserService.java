package org.programutvikling.gui.utility;

import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.ContextModel;

public class EndUserService {
    ComponentRegister prosessorRegister = new ComponentRegister();
    ComponentRegister skjermkortRegister = new ComponentRegister();
    ComponentRegister minneRegister = new ComponentRegister();
    ComponentRegister harddiskRegister = new ComponentRegister();
    ComponentRegister ssdRegister = new ComponentRegister();
    ComponentRegister tastaturRegister = new ComponentRegister();
    ComponentRegister annetRegister = new ComponentRegister();

    //kunne man ha gjort dette med et componentregister-list? altså list som har

    public EndUserService() {
        updateEndUserRegisters();
    }

    public ComponentRegister getProsessorRegister() {
        return prosessorRegister;
    }

    public ComponentRegister getSkjermkortRegister() {
        return skjermkortRegister;
    }

    public ComponentRegister getMinneRegister() {
        return minneRegister;
    }

    public ComponentRegister getHarddiskRegister() {
        return harddiskRegister;
    }

    public ComponentRegister getSsdRegister() {
        return ssdRegister;
    }

    public ComponentRegister getTastaturRegister() {
        return tastaturRegister;
    }

    public ComponentRegister getAnnetRegister() {
        return annetRegister;
    }

    public void updateEndUserRegisters() {

        clearRegisters(prosessorRegister, skjermkortRegister, minneRegister, harddiskRegister, ssdRegister,
                tastaturRegister, annetRegister);

        ComponentRegister dataBaseRegister = ContextModel.INSTANCE.getComponentRegister();
        prosessorRegister.getRegister().addAll(dataBaseRegister.filterByProductType("prosessor"));
        skjermkortRegister.getRegister().addAll(dataBaseRegister.filterByProductType("skjermkort"));
        minneRegister.getRegister().addAll(dataBaseRegister.filterByProductType("minne"));
        minneRegister.getRegister().addAll(dataBaseRegister.filterByProductType("harddisk"));
        ssdRegister.getRegister().addAll(dataBaseRegister.filterByProductType("ssd"));
        prosessorRegister.getRegister().addAll(dataBaseRegister.filterByProductType("tastatur"));
        prosessorRegister.getRegister().addAll(dataBaseRegister.filterByProductType("annet"));
    }


    private void clearRegisters(ComponentRegister... c) {
        for (ComponentRegister i : c) {
            i.getRegister().clear();
        }
    }
}
