package org.programutvikling.gui;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserValidator;
import org.programutvikling.gui.customTextField.ZipField;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.Model;
import org.programutvikling.model.TemporaryComponent;
import org.programutvikling.model.TemporaryUser;

import java.io.IOException;

public class RegistryUserLogic {
    private GridPane gridPane;
    TabUsersController tabUsersController;

    public RegistryUserLogic(GridPane gridPane, TabUsersController tabUsersController) {
        this.gridPane = gridPane;
        this.tabUsersController = tabUsersController;
    }
    public RegistryUserLogic(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    User createUserFromGUIInputFields() {
        try {
            User user = createUser();
            Model.INSTANCE.getUserRegister().addBruker(user);
            resetFields();
            Dialog.showSuccessDialog(user.getUsername() + " er lagt til i listen!");
            return user;
        } catch (IllegalArgumentException illegalArgumentException) {
            Dialog.showErrorDialog(illegalArgumentException.getMessage());
        }
        return null;
    }

    private User createUser() {
        boolean admin = getBoolean((CheckBox) gridPane.lookup("#userAdmin"));
        String username = getString((TextField) gridPane.lookup("#userUsername"));
        String password = getString((TextField) gridPane.lookup("#userPassword"));
        String name = getString((TextField) gridPane.lookup("#userName"));
        String email = getString((TextField) gridPane.lookup("#userMail"));
        String address = getString((TextField) gridPane.lookup("#userAddress"));
        String zip = getString((ZipField) gridPane.lookup("#userZip"));
        String city = getString((TextField) gridPane.lookup("#userCity"));

        // validation
        usernameValidation(username);
        emailValidation(email);

        return new User(admin,username,password,name,email,address,zip,city);
    }

    public void registerUser() {
        if(areInputFieldsEmpty()) {
            Dialog.showErrorDialog("Noen av feltene er tomme!");
            return;
        }

        createUserFromGUIInputFields();
        // duplikat her

    }

    private boolean areInputFieldsEmpty() {
        return (((TextField) gridPane.lookup("#userUsername")).getText().isEmpty() || ((TextField) gridPane.lookup("#userPassword")).getText().isEmpty()
                || ((TextField) gridPane.lookup("#userName")).getText().isEmpty() || ((TextField) gridPane.lookup("#userMail")).getText().isEmpty()
                || ((TextField) gridPane.lookup("#userAddress")).getText().isEmpty() || ((ZipField) gridPane.lookup("#userZip")).getText().isEmpty()
                || ((TextField) gridPane.lookup("#userCity")).getText().isEmpty());

    }

    private void resetFields() {
        ((CheckBox) gridPane.lookup("#userAdmin")).setSelected(false);
        ((TextField) gridPane.lookup("#userUsername")).setText("");
        ((TextField) gridPane.lookup("#userPassword")).setText("");
        ((TextField) gridPane.lookup("#userName")).setText("");
        ((TextField) gridPane.lookup("#userMail")).setText("");
        ((TextField) gridPane.lookup("#userAddress")).setText("");
        ((ZipField) gridPane.lookup("#userZip")).setText("");
        ((TextField) gridPane.lookup("#userCity")).setText("");
    }

    public void usernameValidation(String username) {
        if(Model.INSTANCE.getUserRegister().usernameExists(username)) {
            System.out.println("UsernameAlreadyExistsException thrown!");
            throw new IllegalArgumentException("Brukernavnet er allerede i bruk!");
        } else {
            System.out.println("UsernameAlreadyExistsException NOT thrown, Model.INSTANCE.getUserRegister().usernameExists(username) = " + Model.INSTANCE.getUserRegister().usernameExists(username));
        }
    }

    public void emailValidation(String email) {
        if(Model.INSTANCE.getUserRegister().emailExists(email)) {
            System.out.println("EmailExistsException thrown!");
            throw new IllegalArgumentException("Emailen er allerede registrert!");
        } else {
            System.out.println("EmailExistsException NOT thrown, Model.INSTANCE.getUserRegister().emailExists(email) = " + Model.INSTANCE.getUserRegister().emailExists(email));
        }
    }

    void askForDeletion(User selectedItem) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "Trykk ja for å slette.", "Vil du slette ",
                selectedItem.getName());
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            deleteUser(selectedItem);
            FileHandling.saveAllAdminFiles();
        }
    }

        public void editUserFromPopup(User u) {
        if (TemporaryUser.INSTANCE.getIsEdited()) {
            //User dup = TemporaryUser.INSTANCE.getTempUser();
            User dup =
                    UserValidator.returnUserIfAlreadyExists(TemporaryUser.INSTANCE.getTempUser(),
                            Model.INSTANCE.getUserRegister());

            System.out.println(TemporaryUser.INSTANCE.getTempUser());
            if (dup == null) {
                System.out.println(getRegister().indexOf(u));
                getRegister().set(getRegister().indexOf(u),
                        TemporaryUser.INSTANCE.getTempUser());
                TemporaryUser.INSTANCE.resetTemps();
            } else {
                justReplaceUser(u, dup);
                Model.INSTANCE.getComponentRegister().removeDuplicates();
            }
            try {
                FileHandling.saveAllAdminFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void justReplaceUser(User newUser, User possibleDuplicateUserIfNotThenNull){

        int indexToReplace =
                getRegister().indexOf(possibleDuplicateUserIfNotThenNull);

        System.out.println("index to replac : "+indexToReplace);
        getRegister().set(indexToReplace, newUser);

        //  tabComponentsController.updateView();

               /* getComponentRegister().getObservableRegister().remove(possibleDuplicateComponentIfNotThenNull);
                getComponentRegister().getObservableRegister().add(newComponent);*/
    }

    private void deleteUser(User user) {
        Model.INSTANCE.getUserRegister().getRegister().remove(user);
    }
    // getters from gridPane

    private boolean getBoolean(CheckBox checkBox) {
        return checkBox.isSelected();
    }

    private String getString(TextField textField) {
        return textField.getText();
    }

    private ObservableList<User> getRegister() {
        return Model.INSTANCE.getUserRegister().getRegister();
    }


}
