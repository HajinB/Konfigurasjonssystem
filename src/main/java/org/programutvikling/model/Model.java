package org.programutvikling.model;

import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.user.SavedPathRegister;
import org.programutvikling.domain.utility.UserPreferences;
import org.programutvikling.gui.controllers.FileHandling;
import org.programutvikling.gui.utility.FileUtility;

import java.util.ArrayList;

//singleton som holder data fra fil - slik at samme data enkelt kan aksesses fra flere controllere.
public enum Model {
    INSTANCE;

    private SavedPathRegister savedPathRegister = new SavedPathRegister();
    private ComponentRegister componentRegister = new ComponentRegister();
    private UserPreferences userPreferences = new UserPreferences();
    private boolean endUserLoggedIn = false;
    private ArrayList<Object> adminObjects = new ArrayList<>();

    private Model() {
        loadFileIntoModel();  //kan ikke kjøre denne metoden fra en annen klasse - konstruktøren må holdes privat
    }

    public void loadFileIntoModel() {
        if (FileUtility.doesFileExist(userPreferences.getPathToAdminFiles().toString())) {
            FileHandling.openFile(adminObjects, userPreferences.getPathToAdminFiles().toString());
            loadObjectsIntoClasses();
            removeDuplicates();
        } else {
            System.out.println("Ingen datafil ble funnet - prøv backup");
            if (FileUtility.doesFileExist(userPreferences.getStringPathToBackupAppFiles())) {
                FileHandling.openFile(adminObjects, userPreferences.getStringPathToBackupAppFiles());
                removeDuplicates();
            }
            loadObjectsIntoClasses();
        }
    }

    private void removeDuplicates() {
        savedPathRegister.removeDuplicates();
    }

    public void appendComponentRegisterIntoModel() {
        if (adminObjects.size() == 0) {
            return;
        }
        if (adminObjects.get(0) instanceof ComponentRegister && adminObjects.get(0) != null) {
            ComponentRegister componentRegisterFromFile = (ComponentRegister) adminObjects.get(0);
            componentRegister.getRegister().addAll(componentRegisterFromFile.getRegister());
        }
    }

    public void loadComponentRegisterIntoModel() {
        if (adminObjects.size() == 0) {
            return;
        }
        if (adminObjects.get(0) instanceof ComponentRegister && adminObjects.get(0) != null)
            setComponentRegister((ComponentRegister) adminObjects.get(0));
    }

    public void loadObjectsIntoClasses() {
        if (adminObjects.size() > 0) {
            if (adminObjects.get(0) != null && adminObjects.get(0) instanceof ComponentRegister)
                setComponentRegister((ComponentRegister) adminObjects.get(0));

            if (adminObjects.size() > 1 && adminObjects.get(1) != null && adminObjects.get(1) instanceof SavedPathRegister)
                savedPathRegister = (SavedPathRegister) adminObjects.get(1);
        }
    }

    /**
     * Get/Set
     */

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public SavedPathRegister getSavedPathRegister() {
        return savedPathRegister;
    }

    public ArrayList<Object> getCleanObjectList() {
        if (adminObjects.size() > 0) {
            adminObjects.clear();
        }
        return adminObjects;
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


}
