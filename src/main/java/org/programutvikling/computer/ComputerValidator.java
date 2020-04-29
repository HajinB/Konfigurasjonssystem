package org.programutvikling.computer;

import org.programutvikling.component.ComponentTypes;

import java.util.ArrayList;

public class ComputerValidator {

    public ArrayList<String> listOfMissingComponentTypes(Computer computer) {
        ArrayList<String> perfectList = ComponentTypes.getNeededComponentTypeNames();

        //1 . lager en unique, sortert liste av ComponentNames currently i Computer/currentComputer
        ArrayList<String> componentsInCurrentComputer =
                (ArrayList<String>) computer.createSortedUniqueComponentTypeList();
        //fjerner de som er i computer fra perfekt lista - det er de som er igjen som mangler.
        perfectList.removeAll(componentsInCurrentComputer);
        return perfectList;
    }

    public boolean checkComponentTypeAmount(Computer computer, int maxValue, String type) {
        return computer.getComponentRegister().countByType(type) < maxValue;
    }

    public boolean cabinetListValidator(Computer computer) {
        //hvis det står count == 0, betyr det at det bare er plass til 1 i den typen.
        return computer.getComponentRegister().countByType("kabinett") == 0;
    }

    public boolean processorListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("prosessor") < 2;
    }

    public boolean videoCardListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("skjermkort") < 2;
    }

    public boolean motherboardListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("hovedkort") == 0;
    }

    public boolean memoryListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("minne") < 4;
    }

    public boolean hardDiscListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("harddisk") < 3;
    }

    public boolean keyboardListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("tastatur") == 0;
    }

    public boolean mouseListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("mus") == 0;
    }

    public boolean screenListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("skjerm") < 3;
    }

    public boolean otherListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("annet") < 4;
    }
}

