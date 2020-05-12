package org.programutvikling.model;

import org.programutvikling.domain.component.Component;

import java.util.ArrayList;


//brukes i popup for å kontrollere editing av komponent.
public enum TemporaryComponent {
    INSTANCE;
    private Component tempComponent;
    boolean isEdited;
    int columnIndex;

    public ArrayList<String> errorList = new ArrayList<>();

    public void storeTempComponent(Component component){
        tempComponent = component;
    }

    public Component getTempComponent() {
        return tempComponent;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public boolean getIsEdited() {
        return isEdited;
    }

    public void resetTemps() {
        isEdited = false;
    }

    public void setColumnIndex(int column) {
        this.columnIndex = column;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public ArrayList<String> getErrorList() {
        return errorList;
    }

    public String errorListToString(){
        String melding= "";
        for(String s: errorList){
            melding = melding + s +"\n\n";
        }
        return melding;
    }
}
