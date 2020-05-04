package org.programutvikling.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.user.User;
import org.programutvikling.gui.customTextField.PriceField;

public class RegistryUserLogic {
    private GridPane gridPane;

    public RegistryUserLogic(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    User createUserFromGUIInputFields() {
        try {
            User user = createUser();
        }
    }

    private User createUser() {
        boolean admin = getBoolean((CheckBox) gridPane.lookup("#UserAdmin"));
        String username = getString((TextField) gridPane.lookup("#UserUsername"));
        String password = getString((TextField) gridPane.lookup("#UserPassword"));
        String name = getString((TextField) gridPane.lookup("#userName"));
        String email = getString((TextField) gridPane.lookup("#userMail"));
        String address = getString((TextField) gridPane.lookup("#userAddress"));
        String zip = getString((TextField) gridPane.lookup("#userName"));
        String city = getString((TextField) gridPane.lookup("#userName"));
    }

    // getters from gridPane

    private boolean getBoolean(CheckBox checkBox) {
        return checkBox.isSelected();
    }

    private String getString(TextField textField) {
        return textField.getText();
    }
}
