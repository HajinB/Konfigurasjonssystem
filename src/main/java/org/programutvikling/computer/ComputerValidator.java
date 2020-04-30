package org.programutvikling.computer;

import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.Model.Model;

import java.util.ArrayList;

public class ComputerValidator {

    public static boolean isComponentValidForList(Component component) {
        String type = component.getProductType().toLowerCase();
        System.out.println(type);

        return validatorFactory(type);

    }

    static Computer getCurrentComputer() {
        return Model.INSTANCE.getComputer();
    }

    //sjekker om handlekurven er full for valgt type
    private static boolean validatorFactory(String type) {
        if (type.equalsIgnoreCase("kabinett")) {
            return cabinetListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("prosessor")) {
            return processorListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("skjermkort")) {
            return videoCardListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("hovedkort")) {
            return motherboardListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("minne")) {
            return memoryListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("harddisk")) {
            return hardDiscListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("keyboard")) {
            return keyboardListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("mus")) {
            return mouseListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("skjerm")) {
            return screenListValidator(getCurrentComputer());
        }
        if (type.equalsIgnoreCase("skjerm")) {
            return otherListValidator(getCurrentComputer());
        }else{
            return false;
        }
    }

    /**
     * Business rules - man kan ikke legge til flere enn x i handlekurven av hver type
     */

    public static boolean cabinetListValidator(Computer computer) {
        //hvis det står count == 0, betyr det at det bare er plass til 1 i den typen.
        return computer.getComponentRegister().countByType("kabinett") == 0;
    }

    public static boolean processorListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("prosessor") < 2;
    }

    public static boolean videoCardListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("skjermkort") < 2;
    }

    public static boolean motherboardListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("hovedkort") == 0;
    }

    public static boolean memoryListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("minne") < 4;
    }

    public static boolean hardDiscListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("harddisk") < 3;
    }

    public static boolean keyboardListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("tastatur") == 0;
    }

    public static boolean mouseListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("mus") == 0;
    }

    public static boolean screenListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("skjerm") < 3;
    }

    public static boolean otherListValidator(Computer computer) {
        return computer.getComponentRegister().countByType("annet") < 4;
    }

    public ArrayList<String> listOfMissingComponentTypes(Computer computer) {
        ArrayList<String> perfectList = ComponentTypes.getNeededComponentTypeNames();

        //1 . lager en unique, sortert liste av ComponentNames currently i Computer/currentComputer
        ArrayList<String> componentsInCurrentComputer =
                (ArrayList<String>) computer.createSortedUniqueComponentTypeList();
        //fjerner de som er i computer fra perfekt lista - det er de som er igjen som mangler.
        perfectList.removeAll(componentsInCurrentComputer);
        return perfectList;
    }

    //kan bruke denne også ha parametre for == 0 osv der metoden bli kalt (?)
    public boolean checkComponentTypeAmount(Computer computer, int maxValue, String type) {
        return computer.getComponentRegister().countByType(type) < maxValue;
    }

}




