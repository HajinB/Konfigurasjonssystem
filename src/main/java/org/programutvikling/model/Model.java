package org.programutvikling.model;

import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserPreferences;
import org.programutvikling.domain.user.UserRegister;
import org.programutvikling.gui.FileHandling;
import org.programutvikling.gui.SavedPathRegister;
import org.programutvikling.gui.utility.FileUtility;

import java.util.ArrayList;

//singleton som holder data fra fil - slik at samme data kan aksesses fra flere controllere.
public enum Model {
    INSTANCE;
    private SavedPathRegister savedPathRegister = new SavedPathRegister();
    private ComponentRegister componentRegister = new ComponentRegister();
    // private ComputerRegister computerRegister = new ComputerRegister();
    // private Computer computer = new Computer("current");
    //temporary master list - som har alle objekter fra fil.
    private ArrayList<Object> endUserObjects = new ArrayList<>();

    private UserPreferences userPreferences = new UserPreferences();
    private UserRegister userRegister = new UserRegister();

    private Model() {
        loadFileIntoModel();  //kan ikke kjøre denne metoden fra en annen klasse - konstruktøren må holdes privat
    }

    public void loadFileIntoModel() {
        System.out.println("hi from model constructor" + userPreferences.getPathToAdminFiles().toString());
        if (FileUtility.doesFileExist(userPreferences.getPathToAdminFiles().toString())) {
            FileHandling.openFile(endUserObjects, userPreferences.getPathToAdminFiles().toString());
            addDefaultUsers();
            //legg til open computer her
            loadObjectsIntoClasses();
            removeDuplicates();
        } else {
            System.out.println("ingen config fil ble funnet - tilbake til default.");
            FileHandling.openFile(endUserObjects, userPreferences.getStringPathToBackupAppFiles());
            addDefaultUsers();
            loadObjectsIntoClasses();
        }
    }

    private void removeDuplicates() {
        savedPathRegister.removeDuplicates();
    }

    public void addDefaultUsers() {
        System.out.println("addDefault utenfor if");
        System.out.println("userRegister.getRegister().size(): " + userRegister.getRegister().size());
        System.out.println("!userRegister.checkForAdmins(): " + !userRegister.checkForAdmins());
        if (userRegister.getRegister().size() == 0 || !userRegister.checkForAdmins()) {
            User user = new User(true, "admin", "admin", "ola",
                    "admin@admin.com", "trondheimsvegen 1", "0909", "Trondheim");

            User user2 = new User(false, "user", "user", "ola",
                    "user@user.com", "trondheimsvegen 1", "0909", "Trondheim");
            User user3 = new User(false, "Tom", "password", "Tom", "tom@tom.com", "Toms vei 2", "2345", "Oslo");
            User user4 = new User(true, "Hackerman", "hacker", "H4CK3R", "h4ck@m4i1.com", "Trodde du ja!", "1337", "Russia");
            userRegister.addBruker(user);
            userRegister.addBruker(user2);
            userRegister.addBruker(user3);
            userRegister.addBruker(user4);
            System.out.println("addDefault kjørt!");
        }
    }


    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public SavedPathRegister getSavedPathRegister() {
        return savedPathRegister;
    }

    public ArrayList<Object> getCurrentObjectList() {
        return endUserObjects;
    }

    public ArrayList<Object> getCleanObjectList() {
        if (endUserObjects.size() > 0) {
            endUserObjects.clear();
        }
        return endUserObjects;
    }

    public void appendComponentRegisterIntoModel() {
        if (endUserObjects.size() == 0) {
            return;
        }
        if (endUserObjects.get(0) instanceof ComponentRegister && endUserObjects.get(0) != null) {
            ComponentRegister componentRegisterFromFile = (ComponentRegister) endUserObjects.get(0);
            System.out.println("size før append: " + componentRegister.getRegister().size());
            componentRegister.getRegister().addAll(componentRegisterFromFile.getRegister());
            System.out.println("size etter append: " + componentRegister.getRegister().size());
        }
        //componentRegister.getRegister().addAll(objects.get(0).;
    }

    public void loadComponentRegisterIntoModel() {
        if (endUserObjects.size() == 0) {
            return;
        }
        if (endUserObjects.get(0) instanceof ComponentRegister && endUserObjects.get(0) != null)
            setComponentRegister((ComponentRegister) endUserObjects.get(0));
    }

    public void loadObjectsIntoClasses() {   //kan strengt talt være i en annen klasse....
        SavedPathRegister savedPathRegisterTemp = null;
        /**går det ann å skrive dette på en annen måte? factory method feks??*/
        System.out.println(endUserObjects.size());
        if (endUserObjects.size() > 0) {
            if (endUserObjects.get(0) != null && endUserObjects.get(0) instanceof ComponentRegister)
                setComponentRegister((ComponentRegister) endUserObjects.get(0));

            if (endUserObjects.size() > 1 && endUserObjects.get(1) != null && endUserObjects.get(1) instanceof SavedPathRegister)
                savedPathRegister = (SavedPathRegister) endUserObjects.get(1);
            savedPathRegister.removeDuplicates();

            if (endUserObjects.get(2) != null && endUserObjects.get(2) instanceof UserRegister)
                userRegister = (UserRegister) endUserObjects.get(2);
        }
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


    public void getCleanEndUserObjectList() {
    }
}
