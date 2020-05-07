package org.programutvikling.model;

import org.programutvikling.domain.user.User;

import java.util.ArrayList;

public enum TemporaryUser {
    INSTANCE;
    private User tempUser;
    boolean isEdited;
    int columnIndex;
    ArrayList<String> errorList = new ArrayList<>();

    public void storeTempUser(User user){
        tempUser = user;
    }

    public User getTempUser() {
        return tempUser;
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
            melding = melding + s +"\n";
        }
        return melding;
    }
}
