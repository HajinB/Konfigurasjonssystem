package org.programutvikling.Model;

import org.programutvikling.component.Component;

public enum TemporaryComponent {
    INSTANCE;
    private Component tempComponent;
    boolean isEdited;
    int columnIndex;

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
}
