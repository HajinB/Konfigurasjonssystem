package org.programutvikling.model;

import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerRegister;
import org.programutvikling.domain.user.User;
import org.programutvikling.gui.FileHandling;
import org.programutvikling.gui.SavedPathRegister;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.domain.user.UserPreferences;
import org.programutvikling.domain.user.UserRegister;

import java.util.ArrayList;

//singleton som holder data fra fil - slik at samme data kan aksesses fra flere controllere.
public enum Model {
    INSTANCE;
    private SavedPathRegister savedPathRegister = new SavedPathRegister();
    private ComponentRegister componentRegister = new ComponentRegister();
   // private ComputerRegister computerRegister = new ComputerRegister();
   // private Computer computer = new Computer("current");
    //temporary master list - som har alle objekter fra fil.
    private ArrayList<Object> EndUserObjects = new ArrayList<>();

    private UserPreferences userPreferences = new UserPreferences();
    private UserRegister userRegister = new UserRegister();

    private Model(){
        loadFileIntoModel();  //kan ikke kjøre denne metoden fra en annen klasse - konstruktøren må holdes privat
    }

    public void loadFileIntoModel() {
        System.out.println("hi from model constructor" + userPreferences.getPathToAdminFiles().toString());
        if (FileUtility.doesFileExist(userPreferences.getPathToAdminFiles().toString())) {
            FileHandling.openFile(EndUserObjects, userPreferences.getPathToAdminFiles().toString());
            addDefaultUsers();
            //legg til open computer her
            loadObjectsIntoClasses();
        } else {
            System.out.println("ingen config fil ble funnet - tilbake til default.");
            FileHandling.openFile(EndUserObjects, userPreferences.getStringPathToBackupAppFiles());
            addDefaultUsers();
            loadObjectsIntoClasses();
        }
    }

    private void addDefaultUsers() {
        User user = new User(true, "admin", "admin", "ola",
                "admin@admin.com", "trondheimsvegen 1", "0909", "Trondheim");

        User user2 = new User(false, "user", "user", "ola",
                "user@user.com",  "trondheimsvegen 1", "0909", "Trondheim");
        User user3 = new User(false,"Tom","password","Tom","tom@tom.com","Toms vei 2","2345","Oslo");
        User user4 = new User(true,"Hackerman","hacker","H4CK3R","h4ck@m4i1.com","Trodde du ja!","1337","Russia");
        userRegister.addBruker(user);
        userRegister.addBruker(user2);
        userRegister.addBruker(user3);
        userRegister.addBruker(user4);

    }


    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public SavedPathRegister getSavedPathRegister() {
        return savedPathRegister;
    }

    public ArrayList<Object> getCurrentObjectList() {
        return EndUserObjects;
    }

    public ArrayList<Object> getCleanObjectList() {
        if (EndUserObjects.size() > 0) {
            EndUserObjects.clear();
        }
        return EndUserObjects;
    }

    public void appendComponentRegisterIntoModel() {
        if (EndUserObjects.size() == 0) {
            return;
        }
        if (EndUserObjects.get(0) instanceof ComponentRegister && EndUserObjects.get(0) != null) {
            ComponentRegister componentRegisterFromFile = (ComponentRegister) EndUserObjects.get(0);
            componentRegister.getRegister().addAll(componentRegisterFromFile.getRegister());
        }
        //componentRegister.getRegister().addAll(objects.get(0).;
    }

    public void loadComponentRegisterIntoModel() {
        if (EndUserObjects.size() == 0) {
            return;
        }
        if (EndUserObjects.get(0) instanceof ComponentRegister && EndUserObjects.get(0) != null)
            setComponentRegister((ComponentRegister) EndUserObjects.get(0));
    }

    public void loadObjectsIntoClasses() {   //kan strengt talt være i en annen klasse....
        /**går det ann å skrive dette på en annen måte? factory method feks??*/
        System.out.println(EndUserObjects.size());
        if (EndUserObjects.size() > 0) {
            if (EndUserObjects.get(0) != null && EndUserObjects.get(0) instanceof ComponentRegister)
                setComponentRegister((ComponentRegister) EndUserObjects.get(0));

            if (EndUserObjects.size() > 1 && EndUserObjects.get(1) != null && EndUserObjects.get(1) instanceof SavedPathRegister)
                savedPathRegister = (SavedPathRegister) EndUserObjects.get(1);

                if (EndUserObjects.get(2) != null && EndUserObjects.get(2) instanceof  UserRegister)
                    userRegister = (UserRegister) EndUserObjects.get(2);
            }
           /* System.out.println("dette prøves å åpnes!!!!::::");
            System.out.println(EndUserObjects.get(0));
            System.out.println(EndUserObjects.get(1));
            System.out.println(EndUserObjects.get(2));
            System.out.println(EndUserObjects.get(3));*/
        }
/*
    private void loadComputerRegisterFromDirectory() {
        ArrayList<Object> computers =   FileHandling.findComputers();
        for(Object c : computers){
            computerRegister.addComputer((Computer) c);
        }

    }

*/
    private boolean checkIfObjectIsComponentRegister() {
        return EndUserObjects.get(0) instanceof ComponentRegister;
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
/*
    public ComputerRegister getComputerRegister() {
        return computerRegister;
    }

    public Computer getComputer() {
        return computer;
    }
*/    public void loadComputerIntoClass() {
}


    public void getCleanEndUserObjectList() {
    }
}
