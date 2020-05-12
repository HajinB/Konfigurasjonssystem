package org.programutvikling.model;

import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserRegister;
import org.programutvikling.domain.utility.UserPreferences;
import org.programutvikling.gui.controllers.FileHandling;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;

import java.util.ArrayList;

public enum ModelUserRegister {
    INSTANCE;
    private UserRegister userRegister = new UserRegister();
    private ArrayList<Object> objectsFromFile = new ArrayList<>();
    UserPreferences userPreferences = new UserPreferences();

    ModelUserRegister() {
        if (FileUtility.doesFileExist(userPreferences.getPathToAdminFiles().toString())) {
            FileHandling.openFile(objectsFromFile, userPreferences.getPathToAdminFiles().toString());
            loadUserRegisterFromFile();
        }
        addDefaultUsers();
    }

    private void loadUserRegisterFromFile() {
        if(objectsFromFile.size()>0) {
            if (objectsFromFile.get(2) != null && objectsFromFile.get(2) instanceof UserRegister)
                userRegister = (UserRegister) objectsFromFile.get(2);
        }
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

    public UserRegister getUserRegister() {
        return userRegister;
    }

}
