package org.programutvikling.gui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import org.programutvikling.component.Component;
import org.programutvikling.gui.customTextField.PriceField;

class RegistryComponentLogic {
    Converter.DoubleStringConverter doubleStringConverter = new Converter.DoubleStringConverter();

    private GridPane gridPane;

    RegistryComponentLogic(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    Component createComponentsFromGUIInputIFields() {
        try {
            Component c = createComponent();
            resetFields();
            return c;
        } catch (NumberFormatException nfe) {
            Dialog.showErrorDialog("Skriv inn tall");
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

    private String getCBString (ChoiceBox choiceBox) {
        return String.valueOf(choiceBox.getValue());
    }

    private String getTextareaString (TextArea textArea) {
        return textArea.getText();
    }

    private double getDouble(TextField field) {

        return doubleStringConverter.fromString(getString(field));
    }

    private void resetFields() {
        ((ChoiceBox) gridPane.lookup("#productType")).setValue(null);
        ((TextField) gridPane.lookup("#productName")).setText("");
        ((TextArea) gridPane.lookup("#productDescription")).setText("");
        ((PriceField) gridPane.lookup("#productPrice")).setText("");
    }


}