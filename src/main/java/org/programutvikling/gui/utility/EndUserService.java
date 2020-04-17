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

    public EndUserService(){
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


    /**Skulle ønske vi kunne bruke den filtreringsmetoden fra controlleren - eller skriver om den slik at man kan
     * gjenbruke*/
    public void updateEndUserRegisters() {
        clearRegisters(prosessorRegister, skjermkortRegister, minneRegister, harddiskRegister, ssdRegister,
                tastaturRegister, annetRegister);
        ComponentRegister dataBaseRegister = ContextModel.INSTANCE.getComponentRegister();
        for (int i = 0; i < dataBaseRegister.getRegister().size(); i++) {
            if (dataBaseRegister.getRegister().get(i).getProductType().toLowerCase().equals("prosessor")) {
                prosessorRegister.getRegister().add(dataBaseRegister.getRegister().get(i));
            }if (dataBaseRegister.getRegister().get(i).getProductType().toLowerCase().equals("skjermkort")) {
                skjermkortRegister.getRegister().add(dataBaseRegister.getRegister().get(i));
            }if (dataBaseRegister.getRegister().get(i).getProductType().toLowerCase().equals("minne")) {
                minneRegister.getRegister().add(dataBaseRegister.getRegister().get(i));
            }if (dataBaseRegister.getRegister().get(i).getProductType().toLowerCase().equals("harddisk")) {
                harddiskRegister.getRegister().add(dataBaseRegister.getRegister().get(i));
            }if (dataBaseRegister.getRegister().get(i).getProductType().toLowerCase().equals("ssd")) {
                ssdRegister.getRegister().add(dataBaseRegister.getRegister().get(i));
            }if (dataBaseRegister.getRegister().get(i).getProductType().toLowerCase().equals("tastatur")) {
                tastaturRegister.getRegister().add(dataBaseRegister.getRegister().get(i));
            }if (dataBaseRegister.getRegister().get(i).getProductType().toLowerCase().equals("annet")) {
                annetRegister.getRegister().add(dataBaseRegister.getRegister().get(i));
            }
        }
    }
    private void clearRegisters(ComponentRegister ... c){
        for(ComponentRegister i : c) {
            i.getRegister().clear();
        }
    }
}
