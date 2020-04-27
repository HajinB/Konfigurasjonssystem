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
//    public boolean login(User user) {
//        for(User user : userRegister) {
//            if(user.getUsername().matches(username))
//        }
//    }
    public boolean isAdmin(User user) {
        // Check if the user is admin by running through
        // the register and see if the admin property is "true"
        return false;
    }
    public boolean usernameExists(User user) {
        // Check if the username already exists by running
        // through the register and see if the username equals a taken username
        for(User username : userRegister) {
            if (user.getUsername().equals(username.getUsername())) {
                return false;
            }
        }
        return true;
    }

}