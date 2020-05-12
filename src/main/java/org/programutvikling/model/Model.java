package org.programutvikling.model;

import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.user.SavedPathRegister;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserRegister;
import org.programutvikling.domain.utility.UserPreferences;
import org.programutvikling.gui.controllers.FileHandling;
import org.programutvikling.domain.user.SavedPathRegister;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;

import java.util.ArrayList;

//singleton som holder data fra fil - slik at samme data enkelt kan aksesses fra flere controllere.
public enum Model {
    INSTANCE;

    private SavedPathRegister savedPathRegister = new SavedPathRegister();
    private ComponentRegister componentRegister = new ComponentRegister();
    private UserPreferences userPreferences = new UserPreferences();
    private UserRegister userRegister = new UserRegister();
    private boolean endUserLoggedIn = false;
    private ArrayList<Object> EndUserObjects = new ArrayList<>();

    private Model() {
        loadFileIntoModel();  //kan ikke kjøre denne metoden fra en annen klasse - konstruktøren må holdes privat
    }

    public void loadFileIntoModel() {
        if (FileUtility.doesFileExist(userPreferences.getPathToAdminFiles().toString())) {
            FileHandling.openFile(EndUserObjects, userPreferences.getPathToAdminFiles().toString());
            addDefaultUsers();
            //legg til open computer her
            loadObjectsIntoClasses();
            removeDuplicates();
        } else {
            System.out.println("Ingen config fil ble funnet - tilbake til default.");
            FileHandling.openFile(EndUserObjects, userPreferences.getStringPathToBackupAppFiles());
            addDefaultUsers();
            loadObjectsIntoClasses();
        }
    }

    private void removeDuplicates() {
        savedPathRegister.removeDuplicates();
    }

    public void addDefaultUsers() {
        if (userRegister.getRegister().size() == 0) {
            User admin = new User(true, "admin", "admin", "Administrator",
                    "admin@admin.com", "Adminsgate 7", "0001", "Oslo");

            User user2 = new User(false, "user", "user", "Bruker",
                    "user@user.com", "Brukersgate 8", "0010", "Oslo");
            User user3 = new User(false, "Ola_n", "password", "Ola Nordmann", "ola@nordmann.com", "Trondheimsveien 1", "0956", "Oslo");
            User user4 = new User(false, "Kari_h1", "kari123", "Kari Nordmann v/ Host&Servers1", "kari@hostandservers1.no", "Postboks 183 Moholt", "7448", "Trondheim");
            userRegister.addBruker(admin);
            userRegister.addBruker(user2);
            userRegister.addBruker(user3);
            userRegister.addBruker(user4);
            System.out.println("Default brukere lagt til i registeret!");
        } else if (!userRegister.checkForAdmins()) {
            User admin = new User(true, "admin", "admin", "Administrator",
                    "admin@admin.com", "Adminsgate 7", "0001", "Oslo");
            userRegister.addBruker(admin);
            Dialog.showInformationDialog("Ingen admins i registeret. \"admin\" har blitt lagt til i registeret.");
        }
    }

    public void appendComponentRegisterIntoModel() {
        if (EndUserObjects.size() == 0) {
            return;
        }
        if (EndUserObjects.get(0) instanceof ComponentRegister && EndUserObjects.get(0) != null) {
            ComponentRegister componentRegisterFromFile = (ComponentRegister) EndUserObjects.get(0);
            System.out.println("size før append: " + componentRegister.getRegister().size());
            componentRegister.getRegister().addAll(componentRegisterFromFile.getRegister());
            System.out.println("size etter append: " + componentRegister.getRegister().size());
        }
    }

    public void loadComponentRegisterIntoModel() {
        if (EndUserObjects.size() == 0) {
            return;
        }
        if (EndUserObjects.get(0) instanceof ComponentRegister && EndUserObjects.get(0) != null)
            setComponentRegister((ComponentRegister) EndUserObjects.get(0));
    }

    public void loadObjectsIntoClasses() {
        System.out.println(EndUserObjects.size());
        if (EndUserObjects.size() > 0) {
            if (EndUserObjects.get(0) != null && EndUserObjects.get(0) instanceof ComponentRegister)
                setComponentRegister((ComponentRegister) EndUserObjects.get(0));

            if (EndUserObjects.size() > 1 && EndUserObjects.get(1) != null && EndUserObjects.get(1) instanceof SavedPathRegister)
                savedPathRegister = (SavedPathRegister) EndUserObjects.get(1);

            if (EndUserObjects.get(2) != null && EndUserObjects.get(2) instanceof UserRegister)
                userRegister = (UserRegister) EndUserObjects.get(2);
        }
    }

    /**Get/Set*/

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public SavedPathRegister getSavedPathRegister() {
        return savedPathRegister;
    }

    public ArrayList<Object> getCleanObjectList() {
        if (EndUserObjects.size() > 0) {
            EndUserObjects.clear();
        }
        return EndUserObjects;
    }

    public boolean isEndUserLoggedIn() {
        return endUserLoggedIn;
    }

    public void setEndUserLoggedIn(boolean endUserLoggedIn) {
        this.endUserLoggedIn = endUserLoggedIn;
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

}
