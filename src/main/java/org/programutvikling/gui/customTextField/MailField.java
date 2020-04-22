package org.programutvikling.gui.customTextField;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class MailField extends TextField {
    //feil regex
    private final String MAIL_REGEX = "^[a-zæøåA-ZÆØÅ0-9]+(?:[_.-][a-zæøåA-ZÆØÅ0-9]+)*@[a-zæøåA-ZÆØÅ0-9]+(?:[_.-][a-zæøåA-ZÆØÅ0-9]+)*\\.[a-zæøåA-ZÆØÅ]{2,3}$";

    public MailField() {
        super();

        this.textProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            if(!newValue.matches(MAIL_REGEX)) {
                this.setText(oldValue);
            }
        });
    }
}