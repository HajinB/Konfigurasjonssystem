package org.programutvikling.gui.customTextField;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ZipField extends TextField {
    private final String ZIPNUMBER = "^[\\d]{0,4}+$";

    public ZipField() {



        super();
        this.textProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            if (!newValue.matches(ZIPNUMBER)) {
                this.setText(oldValue);
            }
        });
    }
}
