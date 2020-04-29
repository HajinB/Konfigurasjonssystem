package org.programutvikling.gui;

import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.User;
import org.programutvikling.user.UserPreferences;
import org.programutvikling.user.UserRegister;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public enum ContextModel {
    INSTANCE;

    //"Singletons are useful to provide a unique source of data or functionality to other Java Objects."
    //https://stackoverflow.com/questions/6059778/store-data-in-singleton-classes
    //todo: ContextModel er en singleton som lagrer alle objekter, som skal være mulig å aksesse fra alle controllers
    // - altså det er innom denne classen (som er oprettet EN gang, og bare en gang) -
    private SavedPathRegister savedPathRegister = new SavedPathRegister();
    private ComponentRegister componentRegister = new ComponentRegister();
    private ComputerRegister computerRegister = new ComputerRegister();
    private Computer computer = new Computer("current");

    //temporary master list - som har alle objekter fra fil.
    private ArrayList<Object> objects = new ArrayList<>();

    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/AppData.jobj");
    private ArrayList<Component> tempComponent = new ArrayList<>();
    private UserRegister userRegister = new UserRegister();

    private ContextModel() {
        System.out.println("hi from model constructor" + userPreferences.getPathToUser().toString());
        if (FileUtility.doesFileExist(userPreferences.getPathToUser().toString())) {
            FileHandling.openFile(objects, userPreferences.getPathToUser().toString());
            addDefaultUsers();
            loadObjectsIntoClasses();
        } else {
            System.out.println("ingen config fil ble funnet.");
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

    private void loadRegisterFromFile() throws IOException {
        File file = new File(String.valueOf(FileHandling.getPathToUser()));
        String path = file.getAbsolutePath();
        if (file.exists()) {
            FileHandling.openFile(ContextModel.INSTANCE.getCleanObjectList(), FileHandling.getPathToUser());
            ContextModel.INSTANCE.loadObjectsIntoClasses();
        }
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
        if (objects.get(0) instanceof ComponentRegister && objects.get(0) != null) {
            ComponentRegister componentRegisterFromFile = (ComponentRegister) objects.get(0);
            componentRegister.getRegister().addAll(componentRegisterFromFile.getRegister());
        }
            //componentRegister.getRegister().addAll(objects.get(0).;
    }

    public void loadComponentRegisterIntoModel() {
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
        //denne måten kunne ha appenda - men får npe
            /* ComponentRegister componentRegister1 = (ComponentRegister) (objects.get(0));
            ComputerRegister computerRegister1 = (ComputerRegister) objects.get(1);

            INSTANCE.getComponentRegister().getRegister().addAll(componentRegister1.getRegister());
            INSTANCE.getComputerRegister().getRegister().addAll(computerRegister1.getRegister());*/
    }

    private boolean checkIfObjectIsComponentRegister() {
        return objects.get(0) instanceof ComponentRegister;
    }

    /*
        public static ContextModel getInstance() {
            return INSTANCE;
        }
    */
/*
    // synchronized keyword has been removed from here
    public static ContextModel getInstance() {
        // needed because once there is singleton available no need to acquire
        // monitor again & again as it is costly
        if (INSTANCE == null) {
            synchronized (ContextModel.class) {
                // this is needed if two threads are waiting at the monitor at the
                // time when singleton was getting instantiated
                if (INSTANCE == null) {
                    INSTANCE = new ContextModel();
                }
            }
        }
        return singleton;
    }
*/
    public ComponentRegister getComponentRegister() {
        return componentRegister;
    }

    public void setComponentRegister(ComponentRegister componentRegister) {
        this.componentRegister = componentRegister;
    }

    public UserRegister getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(UserRegister userRegister) {
        this.userRegister = userRegister;
    }

    public ComputerRegister getComputerRegister() {
        return computerRegister;
    }

    public Computer getComputer() {
        return computer;
    }
}
