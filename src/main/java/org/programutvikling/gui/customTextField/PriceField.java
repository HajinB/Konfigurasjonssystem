package org.programutvikling.gui.customTextField;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class PriceField extends TextField {
    private static final String DOUBLE_REGEX = "^[\\d]*+$";

    public PriceField() {
        super();

        this.textProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            if(!newValue.matches(DOUBLE_REGEX)) {
                this.setText(oldValue);
            }
        });
    }
}
