package org.programutvikling.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserRegister implements Serializable {

    private transient ObservableList<User> userRegister = FXCollections.observableArrayList();

    public List<User> getRegister() {
        return userRegister;
    }

    public void removeAll() {
        userRegister.clear();
    }

    public void addBruker(User user) {
        userRegister.add(user);
    }


    // returns user after validating the correct username and password
    public User loginCredentialsMatches(String username, String password) {
        for(User user : getRegister()) {
            if(user.getUsername().equalsIgnoreCase(username)) {
                if(user.getPassword().equals(password)){
                    return user;
                }
            }
        }
        // Can't find a user with the correct username or password
        return null;
    }
    // Return true if user is admin.
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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(userRegister));
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {

        List<User> list = (List<User>) inputStream.readObject();
        userRegister = FXCollections.observableArrayList();
        userRegister.addAll(list);
    }

    public String toString() {
        String out = "";
        for(User user : getRegister()) {
            out += user.toString();
        }
        return out;
    }

}