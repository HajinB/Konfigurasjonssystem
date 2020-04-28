package org.programutvikling.computer;

import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentTypes;

import java.util.ArrayList;
import java.util.List;

public class ComputerValidator {



    //skriv en generic "remove duplicates" for lists??


    //returner en liste med ting man mangler for at pcn skal være komplett
    //hvis lista denne metoden returnerer er tom, er computeren i parameteret valid.

    public ArrayList<String> listOfMissingComponentTypes(Computer computer) {
        ArrayList<String> namesMissing = new ArrayList<>();
        ArrayList<String> perfectList = ComponentTypes.getNeededComponentTypeNames();
      // ArrayList<Component> componentsInList = (ArrayList<Component>) computer.getComponentRegister().getRegister();
                //computer.createSortedComponentTypeList();
        //1 . lager en unique, sortert liste av ComponentNames currently i Computer/currentComputer
        ArrayList<String> componentsInCurrentComputer =
                (ArrayList<String>) computer.createSortedUniqueComponentTypeList();
        System.out.println(componentsInCurrentComputer);

        perfectList.removeAll(componentsInCurrentComputer);
        return perfectList;
        //den er ikke sortert
        //skal sammenligne denne med "fasit-lista" - og lage en tredje liste som har det som mangler.



    }

    // <Key, Value>
    // "Kabinett", "Prosessor", "Hovedkort", "Skjermkort", "Minne", "Harddisk", "Tastatur", "Mus", "Skjerm", "Annet"

    /**
     * må man ha forskjellige metoder for "adding" til listen, og for lagringen?
     */
    //disse metodene er for å sjekke antallen før tillegging i listen/computer
    // - så hvis kabinett er max 1 - må denne sjekke om det allerede er tomt - altså det må være count==0 - sender
    // true til gui

    public boolean checkComponentTypeAmount(Computer computer, int maxValue, String type) {
        return computer.getComponentRegister().countByType(type) < maxValue;
    }

    public boolean cabinetListValidator(Computer computer) {
        //hvis det står count == 0, betyr det at det bare er plass til 1 i den typen.
        return computer.getComponentRegister().countByType("kabinett") < 0 ;
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

