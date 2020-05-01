package org.programutvikling.model;

import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerRegister;
import org.programutvikling.gui.FileHandling;
import org.programutvikling.gui.SavedPathRegister;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserPreferences;
import org.programutvikling.domain.user.UserRegister;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
//singleton som holder data fra fil - slik at samme data kan aksesses fra flere controllere.
public enum Model {
    INSTANCE;

    private SavedPathRegister savedPathRegister = new SavedPathRegister();
    private ComponentRegister componentRegister = new ComponentRegister();
    private ComputerRegister computerRegister = new ComputerRegister();
    private Computer computer = new Computer("current");
    //temporary master list - som har alle objekter fra fil.
    private ArrayList<Object> objects = new ArrayList<>();
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Database/AppDataBackup.jobj");
    private UserRegister userRegister = new UserRegister();

    private Model(){
        // test - alltid reset preferences
        try {
            userPreferences.clearPreferences();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        loadFileIntoModel();  //kan ikke kjøre denne metoden fra en annen klasse - konstruktøren må holdes privat
    }

    public void loadFileIntoModel() {
        System.out.println("hi from model constructor" + userPreferences.getPathToUser().toString());
        if (FileUtility.doesFileExist(userPreferences.getPathToUser().toString())) {
            FileHandling.openFile(objects, userPreferences.getPathToUser().toString());
            addDefaultUsers();
            loadObjectsIntoClasses();
        } else {
            System.out.println("ingen config fil ble funnet - tilbake til default.");
            try {
                userPreferences.clearPreferences();
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
            System.out.println(userPreferences.getStringPathToUser().toString());
            FileHandling.openFile(objects, "AppFiles/Database/Backup/AppDataBackup.jobj");
            addDefaultUsers();
            loadObjectsIntoClasses();
        }
    }

    private void addDefaultUsers() {
        User user = new User(true, "admin", "admin", "ola",
                "hhhh@gmail.com", "45505715", "trondheimsvegen 1", "0909", "Trondheim");

        User user2 = new User(false, "user", "user", "ola",
                "hhhh@gmail.com", "45505715", "trondheimsvegen 1", "0909", "Trondheim");

        userRegister.addBruker(user);
        userRegister.addBruker(user2);
    }


    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public SavedPathRegister getSavedPathRegister() {
        return savedPathRegister;
    }

    public ArrayList<Object> getCurrentObjectList() {
        return objects;
    }

    public ArrayList<Object> getCleanObjectList() {
        if (objects.size() > 0) {
            objects.clear();
        }
        return objects;
    }

    public void appendComponentRegisterIntoModel() {
        if (objects.size() == 0) {
            return;
        }
        if (objects.get(0) instanceof ComponentRegister && objects.get(0) != null) {
            ComponentRegister componentRegisterFromFile = (ComponentRegister) objects.get(0);
            componentRegister.getRegister().addAll(componentRegisterFromFile.getRegister());
        }
        //componentRegister.getRegister().addAll(objects.get(0).;
    }

    public void loadComponentRegisterIntoModel() {
        if (objects.size() == 0) {
            return;
        }
        if (objects.get(0) instanceof ComponentRegister && objects.get(0) != null)
            setComponentRegister((ComponentRegister) objects.get(0));
    }

    public void loadObjectsIntoClasses() {   //kan strengt talt være i en annen klasse....
        /**går det ann å skrive dette på en annen måte? factory method feks??*/
        System.out.println(objects.size());
        if (objects.size() > 0) {
            if (objects.get(0) != null)
                setComponentRegister((ComponentRegister) objects.get(0));
            if (objects.get(1) != null)
                computerRegister = (ComputerRegister) objects.get(1);

            if (objects.size() > 2 && objects.get(2) != null)
                savedPathRegister = (SavedPathRegister) objects.get(2);

            if (objects.size() > 3 && objects.get(3) != null && !objects.get(3).equals("")) {
                computer = (Computer) objects.get(3);

                if (objects.get(4) != null)
                    userRegister = (UserRegister) objects.get(4);
            }
            System.out.println("dette prøves å åpnes!!!!::::");
            System.out.println(objects.get(0));
            System.out.println(objects.get(1));
            System.out.println(objects.get(2));
            System.out.println(objects.get(3));
        }

        objects.clear();
    }

    private boolean checkIfObjectIsComponentRegister() {
        return objects.get(0) instanceof ComponentRegister;
    }

    public ComponentRegister getComponentRegister() {
        return componentRegister;
    }

    public void setComponentRegister(ComponentRegister componentRegister) {
        this.componentRegister = componentRegister;
    }

    public UserRegister getUserRegister() {
        return userRegister;
    }

    public ComputerRegister getComputerRegister() {
        return computerRegister;
    }

    public Computer getComputer() {
        return computer;
    }
}
