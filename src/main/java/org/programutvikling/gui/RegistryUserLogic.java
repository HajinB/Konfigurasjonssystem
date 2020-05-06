package org.programutvikling.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.exceptions.*;
import org.programutvikling.gui.customTextField.ZipField;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.Model;

public class RegistryUserLogic {
    private GridPane gridPane;

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
        } catch (UsernameAlreadyExistsException exception) {
            Dialog.showErrorDialog(exception.getMessage());
        } catch (InvalidUsernameException exception) {
            Dialog.showErrorDialog(exception.getMessage());
        } catch (InvalidPasswordException exception) {
            Dialog.showErrorDialog(exception.getMessage());
        } catch (EmailExistsException exception) {
            Dialog.showErrorDialog(exception.getMessage());
        } catch (InvalidEmailException exception) {
            Dialog.showErrorDialog(exception.getMessage());
        } catch (InvalidZipException exception) {
            Dialog.showErrorDialog(exception.getMessage());
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
            throw new UsernameAlreadyExistsException();
        } else {
            System.out.println("UsernameAlreadyExistsException NOT thrown, Model.INSTANCE.getUserRegister().usernameExists(username) = " + Model.INSTANCE.getUserRegister().usernameExists(username));
        }
    }

    public void emailValidation(String email) {
        if(Model.INSTANCE.getUserRegister().emailExists(email)) {
            System.out.println("EmailExistsException thrown!");
            throw new EmailExistsException();
        } else {
            System.out.println("EmailExistsException NOT thrown, Model.INSTANCE.getUserRegister().emailExists(email) = " + Model.INSTANCE.getUserRegister().emailExists(email));
        }
    }
    // getters from gridPane

    private boolean getBoolean(CheckBox checkBox) {
        return checkBox.isSelected();
    }

    private String getString(TextField textField) {
        return textField.getText();
    }


}
