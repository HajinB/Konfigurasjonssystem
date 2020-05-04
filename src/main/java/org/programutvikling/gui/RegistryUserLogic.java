package org.programutvikling.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.user.User;
import org.programutvikling.gui.customTextField.PriceField;
import org.programutvikling.gui.customTextField.ZipField;

public class RegistryUserLogic {
    private GridPane gridPane;

    public RegistryUserLogic(GridPane gridPane) {
        this.gridPane = gridPane;
    }

//    User createUserFromGUIInputFields() {
//        try {
//            User user = createUser();
//        }
//    }

    private User createUser() {
        boolean admin = getBoolean((CheckBox) gridPane.lookup("#userAdmin"));
        String username = getString((TextField) gridPane.lookup("#userUsername"));
        String password = getString((TextField) gridPane.lookup("#userPassword"));
        String name = getString((TextField) gridPane.lookup("#userName"));
        String email = getString((TextField) gridPane.lookup("#userMail"));
        String address = getString((TextField) gridPane.lookup("#userAddress"));
        String zip = getString((ZipField) gridPane.lookup("#userZip"));
        String city = getString((TextField) gridPane.lookup("#userCity"));

        return new User(admin,username,password,name,email,address,zip,city);
    }

    private boolean areInputFieldsEmpty() {
        return ((TextField) gridPane.lookup("#userUsername")).getText().isEmpty() || ((TextField) gridPane.lookup("#userPassword")).getText().isEmpty()
                || ((TextField) gridPane.lookup("#userName")).getText().isEmpty() || ((TextField) gridPane.lookup("#userMail")).getText().isEmpty()
                || ((TextField) gridPane.lookup("#userAddress")).getText().isEmpty() || ((ZipField) gridPane.lookup("#userZip")).getText().isEmpty()
                || ((TextField) gridPane.lookup("#userCity")).getText().isEmpty();

    }
    // getters from gridPane

    private boolean getBoolean(CheckBox checkBox) {
        return checkBox.isSelected();
    }

    private String getString(TextField textField) {
        return textField.getText();
    }


}
