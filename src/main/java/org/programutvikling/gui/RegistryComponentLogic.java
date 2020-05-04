package org.programutvikling.gui;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.component.ComponentValidator;
import org.programutvikling.gui.customTextField.PriceField;
import org.programutvikling.gui.utility.Converter;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.Model;
import org.programutvikling.model.TemporaryComponent;

import java.io.IOException;
import java.util.List;

public class RegistryComponentLogic {
    Converter.DoubleStringConverter doubleStringConverter = new Converter.DoubleStringConverter();

    private GridPane gridPane;

    public RegistryComponentLogic(GridPane gridPane) {
        this.gridPane = gridPane;
        setTextAreaListener(gridPane);
    }

    Component createComponentsFromGUIInputIFields() {
        try {
            Component c = createComponent();
            resetFields();
            Dialog.showSuccessDialog(c.getProductName() + " er lagt til i listen");
            return c;
        } catch (NumberFormatException nfe) {
            Dialog.showErrorDialog("Skriv inn pris");
        } catch (IllegalArgumentException iae) {
            Dialog.showErrorDialog(iae.getMessage());
        }
        return null;
    }

    public void setComponentPopup(Component item) {
        /**kanskje det bør være en metode som velger en faktisk choicebox valg her?*/
//        ((ChoiceBox) gridPane.lookup("#popupProductType")).setValue(item.getProductType());
        ((TextField) gridPane.lookup("#popupProductName")).setText(item.getProductName());
        ((TextArea) gridPane.lookup("#popupProductDescription")).setText(item.getProductDescription());
        ((PriceField) gridPane.lookup("#popupProductPrice")).setText(Double.toString(item.getProductPrice()));
    }

    private Component createComponent() {

        String productType = getCBString((ChoiceBox<String>) gridPane.lookup("#productType"));
        String productName = getString((TextField) gridPane.lookup("#productName"));
        String productDescription = getTextareaString((TextArea) gridPane.lookup("#productDescription"));
        double productPrice = getDouble((PriceField) gridPane.lookup("#productPrice"));

        System.out.println(productType);
        return new Component(productType, productName, productDescription, productPrice);
    }


    private String getString(TextField field) {
        return field.getText();
    }

    private String getCBString(ChoiceBox choiceBox) {
        return String.valueOf(choiceBox.getValue());
    }

    private String getTextareaString(TextArea textArea) {
        return textArea.getText();
    }

    private double getDouble(TextField field) {
        return Double.parseDouble(getString(field));
    }


    public void registerComponent() {
        if (areInputFieldsEmpty()) {
            //feilmelding her for å ta den før NPE kommer
            System.out.println("noen av feltene er tomme");
            return;
        }

        Component newComponent = createComponentsFromGUIInputIFields();
        Component possibleDuplicateComponentIfNotThenNull = ComponentValidator.isComponentInRegisterThenReturnIt(newComponent,
                getComponentRegister());

        System.out.println(possibleDuplicateComponentIfNotThenNull);
        if (possibleDuplicateComponentIfNotThenNull != null) {
            Alert alert = Dialog.getConfirmationAlert("Duplikat funnet", "", "Denne komponenten eksisterer allerede i" +
                    " databasen, vil du erstatte den gamle med: " + newComponent.getProductName(), "");
            alert.showAndWait();
            if (alert.getResult() == alert.getButtonTypes().get(0)) {
                int indexToReplace =
                        getComponentRegister().getRegister().indexOf(possibleDuplicateComponentIfNotThenNull);
                getComponentRegister().getRegister().set(indexToReplace, newComponent);
            }
        } else {
            getComponentRegister().addComponent(newComponent);
        }
    }

    private boolean areInputFieldsEmpty() {
        return ((TextField) gridPane.lookup("#productName")).getText().isEmpty() || ((TextArea) gridPane.lookup(
                "#productDescription")).getText().isEmpty() || ((PriceField) gridPane.lookup("#productPrice")).getText().isEmpty();

    }

    private ComponentRegister getComponentRegister() {
        return Model.INSTANCE.getComponentRegister();
    }

    private void resetSearchField() {
        ((TextField) gridPane.lookup("#componentSearch")).setText("");
    }

    public void editComponentFromPopup(Component c) {
        if (TemporaryComponent.INSTANCE.getIsEdited()) {
            getObservableRegister().set(getObservableRegister().indexOf(c),
                    TemporaryComponent.INSTANCE.getTempComponent());
            TemporaryComponent.INSTANCE.resetTemps();
            try {
                FileHandling.saveAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List getObservableRegister() {
        return Model.INSTANCE.getComponentRegister().getObservableRegister();

    }

    private void deleteComponent(Component selectedComp) {
        getComponentRegister().getRegister().remove(selectedComp);
        //updateView();
    }

    void askForDeletion(Component selectedItem) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "Trykk ja for å slette.", "Vil du slette ",
                selectedItem.getProductName());
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            deleteComponent(selectedItem);
            FileHandling.saveAll();
        }
    }


    private void resetFields() {
        ((ChoiceBox) gridPane.lookup("#productType")).setValue(null);
        ((TextField) gridPane.lookup("#productName")).setText("");
        ((TextArea) gridPane.lookup("#productDescription")).setText("");
        ((PriceField) gridPane.lookup("#productPrice")).setText("");
    }


    public void setTextAreaListener(GridPane gridPane) {
        TextArea textArea = ((TextArea) gridPane.lookup("#productDescription"));
        textArea.setWrapText(true);

        //overrider ENTER keyEvent for å unngå lineshift ( brukes for å lese fra txtfil)
        textArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ENTER) {
                    keyEvent.consume();
                }
            }
        });
    }
}