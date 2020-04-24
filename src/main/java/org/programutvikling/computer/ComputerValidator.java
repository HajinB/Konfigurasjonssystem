package org.programutvikling.computer;

public class ComputerValidator {

    boolean isComputerValid(Computer computer) {
        return true;
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

    public boolean kabinettValidator(Computer computer) {
        //hvis det står count == 0, betyr det at det bare er plass til 1 i den typen.
        return computer.getComponentRegister().countByType("kabinett") == 0;
    }

    public boolean prosessorValidator(Computer computer) {
        return computer.getComponentRegister().countByType("prosessor") < 2;
    }

    public boolean skjermkortValidator(Computer computer) {
        return computer.getComponentRegister().countByType("skjermkort") < 2;
    }

    public boolean hovedkortValidator(Computer computer) {
        return computer.getComponentRegister().countByType("hovedkort") == 0;
    }

    public boolean minneValidator(Computer computer) {
        return computer.getComponentRegister().countByType("minne") < 4;
    }

    public boolean harddiskValidator(Computer computer) {
        return computer.getComponentRegister().countByType("harddisk") < 3;
    }

    public boolean tastaturValidator(Computer computer) {
        return computer.getComponentRegister().countByType("tastatur") == 0;
    }

    public boolean musValidator(Computer computer) {
        return computer.getComponentRegister().countByType("mus") == 0;
    }

    public boolean skjermValidator(Computer computer) {
        return computer.getComponentRegister().countByType("skjerm") < 3;
    }

    public boolean annetValidator(Computer computer) {
        return computer.getComponentRegister().countByType("annet") < 4;
    }
}

