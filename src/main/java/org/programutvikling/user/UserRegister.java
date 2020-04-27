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

    // returns user after finding
    public User loginCredentialsMatches(String username, String password) {
        for(User user : userRegister) {
            if(user.getUsername().equalsIgnoreCase(username)) {
                if(user.getPassword().matches(password)){
                    return user;
                }
            }
        }
        return null;
    }

    public boolean isAdmin(User user) {
        return user.getAdmin();
    }

    public boolean usernameExists(String username) {
        // Check if the username already exists by running
        // through the register and see if the username equals a taken username
        for(User user : userRegister) {
            if (user.getUsername().equalsIgnoreCase(user.getUsername())) {
                return false;
            }
        }
        return true;
    }

}