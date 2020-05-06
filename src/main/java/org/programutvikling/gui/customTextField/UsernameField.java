package org.programutvikling.gui.customTextField;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class UsernameField extends TextField {
    private static final String SPACEBAR_REGEX = "^[\\S.]*+$";

    public UsernameField() {
        super();

        this.textProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            if(!newValue.matches(SPACEBAR_REGEX)) {
                this.setText(oldValue);
            }
        });
    }
}