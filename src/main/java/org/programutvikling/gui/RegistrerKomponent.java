package org.programutvikling.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.programutvikling.komponent.Komponent;
import org.programutvikling.komponent.io.InvalidComponentFormatException;

import java.io.IOException;

class RegistrerKomponent {
    Converter.DoubleStringConverter doubleStringConverter = new Converter.DoubleStringConverter();

    private GridPane gridPane;

    RegistrerKomponent(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    Komponent opprettKomponentFraGUIFelt() throws InvalidComponentFormatException {
        Komponent komponent = opprettKomponent();
        return komponent;
}

    Komponent opprettKomponent() {

    String type = getString((TextField)gridPane.lookup("#inputType"));
    String varenavn = getString((TextField) gridPane.lookup("#inputVarenavn"));
    String beskrivelse = getString((TextField) gridPane.lookup("#inputBeskrivelse"));
    Double pris = getDouble((TextField) gridPane.lookup("#inputPris"));

        System.out.println(type);
    return new Komponent(type, varenavn, beskrivelse, pris);
}

    private String getString(TextField field) {
        if(field != null) {
            return field.getText();
        }
        System.out.println("textfield er null/IO kommer ikke inn - sjekk ID feltene");
        return "";
    }

    private double getDouble(TextField field) {

    return doubleStringConverter.stringTilDouble(getString(field));
    }
}
