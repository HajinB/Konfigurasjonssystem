package org.programutvikling.domain.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

public class UserRegister implements Serializable {

    private transient ObservableList<User> userRegister = FXCollections.observableArrayList();

    public ObservableList<User> getRegister() {
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
            if(user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(username)) {
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
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        // If username not found in the loop, return false
        return false;
    }

    public boolean emailExists(String email) {
        for(User user : userRegister) {
            if(user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        // If email not found in the loop, return false
        return false;
    }

    public ObservableList<User> filterByAdmin(String type) {
        if(type.equals("Admin")) {
            return getRegister().stream().
                    filter(User::getAdmin).
                    collect(toCollection(FXCollections::observableArrayList));
        }
        else if(type.equals("Bruker")) {
            return getRegister().stream().
                    filter(u -> !u.getAdmin()).
                    collect(toCollection(FXCollections::observableArrayList));
        }
        throw new IllegalArgumentException("Feil filter i cbAdminFilter!");
    }

    public void attachTableView(TableView<User> tv) {
        if (!userRegister.isEmpty())
            tv.setItems(userRegister);
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