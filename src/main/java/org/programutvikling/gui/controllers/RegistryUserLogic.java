package org.programutvikling.gui.controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserValidator;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.domain.utility.UserFactory;
import org.programutvikling.gui.customTextField.NoSpacebarField;
import org.programutvikling.gui.customTextField.ZipField;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.Stageable;
import org.programutvikling.model.Model;
import org.programutvikling.model.TemporaryUser;

import java.io.IOException;

public class RegistryUserLogic implements Stageable {
    private GridPane gridPane;
    TabUsersController tabUsersController;

    public RegistryUserLogic(GridPane gridPane, TabUsersController tabUsersController) {
        this.gridPane = gridPane;
        this.tabUsersController = tabUsersController;
    }
    public RegistryUserLogic(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public void registerUser() {
        //todo legg til lbls for alle som på tabcomponents?
        boolean missingField = false;
        tabUsersController.clearLabels();

        if (areInputFieldsEmpty()) {
            if (isUsernameEmpty()) {
                tabUsersController.setLblMsgUsername("Skriv inn brukernavn");
                missingField = true;
            }
            if (isPasswordEmpty()) {
                tabUsersController.setLblMsgPassword("Skriv inn passord, minst " + UserValidator.PASSWORD_LENGTH + " tegn");
                missingField = true;
            }
            if (isNameEmpty()) {
                tabUsersController.setLblMsgName("Skriv inn navn");
                missingField = true;
            }
            if (isEmailEmpty()) {
                tabUsersController.setLblMsgEmail("Skriv inn e-postadresse");
                missingField = true;
            }
            if (isAdressEmpty()) {
                tabUsersController.setLblMsgAdress("Skriv inn gateadresse");
                missingField = true;
            }
            if (isZipEmpty()) {
                tabUsersController.setLblMsgZip("Skriv inn postnummer, minst " + UserValidator.ZIP_LENGTH + " tegn");
                missingField = true;
            }
            if (isCityEmpty()) {
                tabUsersController.setLblMsgCity("Skriv inn poststed");
                missingField = true;
            }
        } else {
            if (!isEmailValid()) {
                tabUsersController.setLblMsgEmail("E-postadressen er ikke gyldig eller eksisterer allerede");
                return;
            }
            if (!isUsernameValid()) {
                tabUsersController.setLblMsgUsername("brukernavnet eksisterer allerede ");
                return;
            }
            System.out.println(missingField);
            System.out.println(areInputFieldsEmpty());
            createUserFromGUIInputFields();
        }
    }

    private boolean isUsernameValid() {
        String username = getString((NoSpacebarField) gridPane.lookup("#userUsername"));
        return !usernameExists(username) && UserValidator.username(username);
    }

    private boolean isEmailValid() {
        String email = getString((NoSpacebarField) gridPane.lookup("#userMail"));
        return !emailExists(email) && UserValidator.email(email);
    }

    void createUserFromGUIInputFields() {
        try {
            User user = createUser();
          /*  if(!isEmailValid()){
                tabUsersController.setLblMsgEmail("Epost er ikke gyldig");
                return;
            }
            if(isUsernameValid()){
                tabUsersController.setLblMsgUsername("Brukernavnet eksisterer allerede");
                return;
            }*/
            Model.INSTANCE.getUserRegister().addBruker(user);
            Dialog.showInformationDialog(user.getUsername() + " er lagt til i listen!");
            resetFields();
            return;
        } catch (IllegalArgumentException illegalArgumentException) {
            //registerUser();
            Dialog.showErrorDialog(illegalArgumentException.getMessage());
        }
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
        UserFactory userFactory = new UserFactory();
        User u = userFactory.createUser(admin, username, password, name, email, address, zip, city);

        return u;
    }


    private boolean areInputFieldsEmpty() {
        return isAdressEmpty() || isCityEmpty() || isEmailEmpty() || isNameEmpty() ||
                isPasswordEmpty() || isUsernameEmpty() || isZipEmpty();
    }

    //"#userUsername"
    boolean isUsernameEmpty () {
        //return isFieldEmpty("#userUsername");
        return ((TextField) gridPane.lookup("#userUsername")).getText().isEmpty() ||
                ((TextField) gridPane.lookup("#userUsername")).getText() == null ||
                ((TextField) gridPane.lookup("#userUsername")).getText().isBlank() ||
                ((TextField) gridPane.lookup("#userUsername")).getText().equals("");
    }

    boolean isPasswordEmpty () {
        return ((TextField) gridPane.lookup("#userPassword")).getText().isEmpty() ||
                ((TextField) gridPane.lookup("#userPassword")).getText() == null ||
                ((TextField) gridPane.lookup("#userPassword")).getText().isBlank() ||
                ((TextField) gridPane.lookup("#userPassword")).getText().equals("");
    }

    boolean isNameEmpty () {
        return ((TextField) gridPane.lookup("#userName")).getText().isEmpty() ||
                ((TextField) gridPane.lookup("#userName")).getText().isBlank() ||
                ((TextField) gridPane.lookup("#userName")).getText().equals("");
    }

    boolean isEmailEmpty () {
        return ((TextField) gridPane.lookup("#userMail")).getText().isEmpty() ||
                ((TextField) gridPane.lookup("#userMail")).getText().isBlank() ||
                ((TextField) gridPane.lookup("#userMail")).getText().equals("");
    }

    boolean isAdressEmpty () {
        return ((TextField) gridPane.lookup("#userAddress")).getText().isEmpty() ||
                ((TextField) gridPane.lookup("#userAddress")).getText().isBlank() ||
                ((TextField) gridPane.lookup("#userAddress")).getText().equals("");
    }

    boolean isZipEmpty () {
        return ((ZipField) gridPane.lookup("#userZip")).getText().isEmpty() ||
                ((ZipField) gridPane.lookup("#userZip")).getText().isBlank() ||
                ((ZipField) gridPane.lookup("#userZip")).getText().equals("");
    }

    boolean isCityEmpty () {
        return ((TextField) gridPane.lookup("#userCity")).getText().isEmpty() ||
                ((TextField) gridPane.lookup("#userCity")).getText().isBlank() ||
                ((TextField) gridPane.lookup("#userCity")).getText().equals("");
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

    public boolean usernameExists(String username) {
        return Model.INSTANCE.getUserRegister().usernameExists(username);
    }

    public boolean emailExists(String email) {
        return Model.INSTANCE.getUserRegister().emailExists(email);
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


    @Override
    public void editClickableFromPopup(Clickable u) {
        if (TemporaryUser.INSTANCE.getIsEdited()) {
            System.out.println("tempuserrrrr: " + TemporaryUser.INSTANCE.getTempUser() + " tempuser slutt");
                System.out.println("RegistryUserLogic.editUserFromPopup() getRegister().indexOf(u): " + getRegister().indexOf(u));
                getRegister().set(getRegister().indexOf(u),
                TemporaryUser.INSTANCE.getTempUser());
                TemporaryUser.INSTANCE.resetTemps();
            try {
                FileHandling.saveAllAdminFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void editUserFromPopup(Clickable u) {

    }

    private void justReplaceUser(User newUser, User possibleDuplicateUserIfNotThenNull) {

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
