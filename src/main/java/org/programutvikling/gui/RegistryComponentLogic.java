package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.InvalidComponentFormatException;

import java.io.IOException;
import java.util.List;

class RegistryComponentLogic {
    Converter.StringDoubleConverter stringDoubleConverter = new Converter.StringDoubleConverter();

    private GridPane gridPane;

    RegistryComponentLogic(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    Component createComponentsFromGUIInputIFields() throws InvalidComponentFormatException {
        return createComponent();
}
    ObservableList<Component> createListOfCategoryInComponentRegister(String s, ComponentRegister componentRegister){
        ObservableList<Component> categoryList = FXCollections.observableArrayList();
        List<Component> listInn = componentRegister.getRegister();

        for (Component component : listInn) {
            if (s.equals(component.getName())) {
                categoryList.add(component);
            }
        }
        return categoryList;
    }

    private Component createComponent() {

    String type = getString((TextField)gridPane.lookup("#inputType"));
    String varenavn = getString((TextField) gridPane.lookup("#inputVarenavn"));
    String beskrivelse = getString((TextField) gridPane.lookup("#inputBeskrivelse"));
    double pris = getDouble((TextField) gridPane.lookup("#inputPris"));

    System.out.println(type);
    return new Component(type, varenavn, beskrivelse, pris);
}

    private String getString(TextField field) {
        if(field != null) {
            return field.getText();
        }
        System.out.println("textfield er null/IO kommer ikke inn - sjekk ID feltene");
        return "";
    }

    private double getDouble(TextField field) {

    return stringDoubleConverter.stringTilDouble(getString(field));
    }
}
