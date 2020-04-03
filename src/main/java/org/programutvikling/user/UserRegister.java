package org.programutvikling.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserRegister {
    private ObservableList<User> userRegister = FXCollections.observableArrayList();

    public List<User> getRegister() {
        return userRegister;
    }

    public void removeAll() {
        userRegister.clear();
    }

    public void addBruker(User user) {
        userRegister.add(user);
    }
}