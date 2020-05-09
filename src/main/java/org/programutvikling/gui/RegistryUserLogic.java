package org.programutvikling.gui;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserValidator;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.customTextField.ZipField;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.Stageable;
import org.programutvikling.model.Model;
import org.programutvikling.model.TemporaryComponent;
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
        tabUsersController.clearLabels();

        if(areInputFieldsEmpty()) {
            if (isUsernameEmpty()) {
                tabUsersController.setLblMsgUsername("Skriv inn brukernavn");
            }
            if (isPasswordEmpty()) {
                tabUsersController.setLblMsgPassword("Skriv inn passord, minst "+ UserValidator.PASSWORD_LENGTH+" tegn" );
            }
            if (isNameEmpty()) {
                tabUsersController.setLblMsgName("Skriv inn navn");
            }
            if (isEmailEmpty()) {
                tabUsersController.setLblMsgEmail("Skriv inn e-postadresse");
            }
            if (isAdressEmpty()) {
                tabUsersController.setLblMsgAdress("Skriv inn gateadresse");
            }
            if (isZipEmpty()) {
                tabUsersController.setLblMsgZip("Skriv inn postnummer, minst " +UserValidator.ZIP_LENGTH+ " tegn");
            }
            if (isCityEmpty()) {
                tabUsersController.setLblMsgCity("Skriv inn poststed");
            }
        }
        createUserFromGUIInputFields();
    }

    User createUserFromGUIInputFields() {
        try {
            User user = createUser();
            Model.INSTANCE.getUserRegister().addBruker(user);
            resetFields();
            Dialog.showSuccessDialog(user.getUsername() + " er lagt til i listen!");
            return user;
        } catch (IllegalArgumentException illegalArgumentException) {
            registerUser();
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


    private boolean areInputFieldsEmpty() {
        return isAdressEmpty() || isCityEmpty() || isEmailEmpty() || isNameEmpty() ||
                isPasswordEmpty() || isUsernameEmpty() || isZipEmpty();

    }

    boolean isUsernameEmpty () {
        return ((TextField) gridPane.lookup("#userUsername")).getText().isEmpty() ||
                ((TextField) gridPane.lookup("#userUsername")).getText().isBlank() ||
                ((TextField) gridPane.lookup("#userUsername")).getText().equals("");
    }

    boolean isPasswordEmpty () {
        return ((TextField) gridPane.lookup("#userPassword")).getText().isEmpty() ||
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
