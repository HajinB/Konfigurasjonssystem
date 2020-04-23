package org.programutvikling.gui.utility;

import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.ContextModel;

public class EndUserService {
    ComponentRegister kabinettRegister = new ComponentRegister();
    ComponentRegister prosessorRegister = new ComponentRegister();
    ComponentRegister skjermkortRegister = new ComponentRegister();
    ComponentRegister minneRegister = new ComponentRegister();
    ComponentRegister harddiskRegister = new ComponentRegister();
    ComponentRegister hovedkortRegister = new ComponentRegister();
    ComponentRegister tastaturRegister = new ComponentRegister();
    ComponentRegister annetRegister = new ComponentRegister();
    ComponentRegister musRegister = new ComponentRegister();
    ComponentRegister skjermRegister = new ComponentRegister();

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

    public ComponentRegister getKabinettRegister() {
        return kabinettRegister;
    }

    public ComponentRegister getHovedkortRegister() {
        return hovedkortRegister;
    }

    public ComponentRegister getMusRegister() {
        return musRegister;
    }

    public ComponentRegister getSkjermRegister() {
        return skjermRegister;
    }

    public ComponentRegister getTastaturRegister() {
        return tastaturRegister;
    }

    public ComponentRegister getAnnetRegister() {
        return annetRegister;
    }

    public void updateEndUserRegisters() {

        clearRegisters(hovedkortRegister, kabinettRegister, skjermkortRegister, skjermRegister, musRegister,
                prosessorRegister, minneRegister, harddiskRegister, annetRegister, tastaturRegister);

        ComponentRegister dataBaseRegister = ContextModel.INSTANCE.getComponentRegister();
        hovedkortRegister.getRegister().addAll(dataBaseRegister.filterByProductType("hovedkort"));
        kabinettRegister.getRegister().addAll(dataBaseRegister.filterByProductType("kabinett"));
        skjermRegister.getRegister().addAll(dataBaseRegister.filterByProductType("skjerm"));
        musRegister.getRegister().addAll(dataBaseRegister.filterByProductType("mus"));
        prosessorRegister.getRegister().addAll(dataBaseRegister.filterByProductType("prosessor"));
        skjermkortRegister.getRegister().addAll(dataBaseRegister.filterByProductType("skjermkort"));
        minneRegister.getRegister().addAll(dataBaseRegister.filterByProductType("minne"));
        harddiskRegister.getRegister().addAll(dataBaseRegister.filterByProductType("harddisk"));
        tastaturRegister.getRegister().addAll(dataBaseRegister.filterByProductType("tastatur"));
        annetRegister.getRegister().addAll(dataBaseRegister.filterByProductType("annet"));
    }


    private void clearRegisters(ComponentRegister... c) {
        for (ComponentRegister i : c) {
            i.getRegister().clear();
        }
    }
}
