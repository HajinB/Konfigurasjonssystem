package org.programutvikling.gui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.component.ComponentValidator;
import org.programutvikling.gui.customTextField.PriceField;
import org.programutvikling.gui.utility.Converter;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.WindowHandler;
import org.programutvikling.model.Model;
import org.programutvikling.model.TemporaryComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistryComponentLogic {
    TabComponentsController tabComponentsController;
    WindowHandler windowHandler = new WindowHandler();
    ThreadHandler threadHandler;
    private GridPane gridPane;
    private TableView<Component> tblViewComponent;


    public RegistryComponentLogic(GridPane gridPane, TabComponentsController tabComponentsController, TableView tableView) {
        this.gridPane = gridPane;
        Model.INSTANCE.getComponentRegister().removeDuplicates();
        setTextAreaListener(gridPane);
        this.tabComponentsController = tabComponentsController;
        this.tblViewComponent = tableView;
        this.threadHandler = new ThreadHandler(tabComponentsController);
    }


    public RegistryComponentLogic(GridPane gridPane) {
        this.gridPane = gridPane;
        setTextAreaListener(gridPane);
    }

    public RegistryComponentLogic(GridPane gridPane, ArrayList<Label> labelArrayList) {
        this.gridPane = gridPane;
        setTextAreaListener(gridPane);
    }

    public RegistryComponentLogic(TabComponentsController tabComponentsController) {
        this.tabComponentsController = tabComponentsController;
        this.threadHandler = new ThreadHandler(tabComponentsController);
    }

    void registerComponent() {

        //best å gjøre validering her - business rules kan throwe exceptions lengre downstream som de gjør, bare ta
        // bort dialogs maybe?
        tabComponentsController.clearLabels();
        if (areInputFieldsEmpty()) {
            if (isProductTypeEmpty()){
                System.out.println("hva skjer");
                tabComponentsController.setLblMsgType("Velg produkttype");
            }else{
                System.out.println("produkttype er ikke empty..");
            }
            if (isProductNameEmpty()){
                tabComponentsController.setLblMsgName("Skriv inn navn på produktet");
            }
            if (isProductDescriptionEmpty()) {
                tabComponentsController.setLblMsgDescription("Skriv inn en beskrivelse");
            }
            if (isProductPriceEmpty()) {
                tabComponentsController.setLblMsgPrice("Skriv inn pris på produktet");
            }
        }else {
            createComponentHandleDuplicate();
        }
    }

    public void setTblViewEventHandler() {
        /**detecter tablerow, for å hente ut component*/
        tblViewComponent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getComponentRegister().getRegister().size() > 0) {
                    tblViewComponent.getSelectionModel().setCellSelectionEnabled(false);
                    TableRow<? extends Component> row;
                    if (isDoubleClick(event)) {
                        Node node = ((Node) event.getTarget()).getParent();
                        if (node instanceof TableRow) {
                            row = (TableRow<Component>) node;
                        } else {
                            //hvis man trykker på tekst
                            row = (TableRow<Component>) node.getParent();
                        }
                        try {
                            windowHandler.openEditWindow(row, gridPane);
                            Model.INSTANCE.getComponentRegister().removeDuplicates();
                            tabComponentsController.updateView();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        /**detecter tablecolumn, for å kunne fokusere på riktig celle i popupvindu*/
        final ObservableList<TablePosition> selectedCells = tblViewComponent.getSelectionModel().getSelectedCells();
        //gjør det mulig å detecte cell på første klikk:
        tblViewComponent.getSelectionModel().setCellSelectionEnabled(true);
        selectedCells.addListener((ListChangeListener) c -> {
            if (selectedCells.size() != 0) {
                TemporaryComponent.INSTANCE.setColumnIndex(selectedCells.get(0).getColumn());
            }
        });

    }

    private boolean isDoubleClick(MouseEvent event) {
        return event.isPrimaryButtonDown() && event.getClickCount() == 2;
    }

    Component createComponentsFromGUIInputIFields() {
        System.out.println(getCBString((ChoiceBox<String>) gridPane.lookup("#productType")));
        try {
            Component c = createComponent();
            resetFields();
            //Dialog.showSuccessDialog(c.getProductName() + " er lagt til i listen");

            if (ComponentValidator.isComponentInRegisterThenReturnIt(c,
                    Model.INSTANCE.getComponentRegister()) == null) {
                tabComponentsController.setLblComponentMsg("Komponenten eksisterer allerede!");
            }
            return c;
        } catch (NumberFormatException nfe) {
            registerComponent();
            Dialog.showErrorDialog("Skriv inn pris");
        } catch (IllegalArgumentException iae) {
            registerComponent();
            Dialog.showErrorDialog(iae.getMessage());
        }
        return null;
    }

    private Component createComponent() {

        String productType = getCBString((ChoiceBox<String>) gridPane.lookup("#productType"));
        String productName = getString((TextField) gridPane.lookup("#productName"));
        String productDescription = getTextareaString((TextArea) gridPane.lookup("#productDescription"));
        double productPrice = getDouble((PriceField) gridPane.lookup("#productPrice"));

        Component componentIn = new Component(productType, productName, productDescription, productPrice);
        //kan gjøre validering på disse her - altså et felt om gangen.

        System.out.println(productType);
        tabComponentsController.setResultLabelTimed(componentIn.getProductName() + " er lagt til i listen");

        return componentIn;
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


    private void createComponentHandleDuplicate() {
        Component newComponent = createComponentsFromGUIInputIFields();
        Component possibleDuplicateComponentIfNotThenNull = ComponentValidator.isComponentInRegisterThenReturnIt(newComponent,
                getComponentRegister());

        System.out.println(possibleDuplicateComponentIfNotThenNull);
        if (possibleDuplicateComponentIfNotThenNull != null) {
            showDuplicateDialog(newComponent, possibleDuplicateComponentIfNotThenNull);
        } else {
            tabComponentsController.clearLabels();
            getComponentRegister().addComponent(newComponent);
        }
    }

    private void showDuplicateDialog(Component newComponent, Component possibleDuplicateComponentIfNotThenNull) {
        Alert alert = Dialog.getConfirmationAlert("Duplikat funnet", "", "Denne komponenten eksisterer allerede i" +
                " databasen, vil du erstatte den gamle med: " + newComponent.getProductName(), "");
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            int indexToReplace =
                    getComponentRegister().getRegister().indexOf(possibleDuplicateComponentIfNotThenNull);
            //   tabComponentsController.clearLabels();
            getComponentRegister().getRegister().set(indexToReplace, newComponent);
        }
    }

    private void justReplaceComponent(Component newComponent, Component possibleDuplicateComponentIfNotThenNull) {

        int indexToReplace =
                getComponentRegister().getRegister().indexOf(possibleDuplicateComponentIfNotThenNull);
        System.out.println("index to replac : " + indexToReplace);
        getComponentRegister().getRegister().set(indexToReplace, newComponent);

        //  tabComponentsController.updateView();

               /* getComponentRegister().getObservableRegister().remove(possibleDuplicateComponentIfNotThenNull);
                getComponentRegister().getObservableRegister().add(newComponent);*/
    }

    private boolean areInputFieldsEmpty() {
        return isProductNameEmpty() || isProductDescriptionEmpty() || isProductPriceEmpty() || isProductTypeEmpty();
    }

    private boolean isProductTypeEmpty() {
        return getCBString((ChoiceBox<String>) gridPane.lookup("#productType")) == null ||
                getCBString((ChoiceBox<String>) gridPane.lookup("#productType")).equalsIgnoreCase("") ||
                getCBString((ChoiceBox<String>) gridPane.lookup("#productType")).isBlank() ||
                getCBString((ChoiceBox<String>) gridPane.lookup("#productType")).contentEquals("");
    }

    private boolean isProductPriceEmpty() {
        return ((PriceField) gridPane.lookup("#productPrice")).getText().isEmpty() ||
                ((PriceField) gridPane.lookup("#productPrice")).getText().isBlank() ||
                ((PriceField) gridPane.lookup("#productPrice")).getText().equals("");
    }

    private boolean isProductDescriptionEmpty() {
        return ((TextArea) gridPane.lookup("#productDescription")).getText().isEmpty() ||
                ((TextArea) gridPane.lookup("#productDescription")).getText().isBlank() ||
                ((TextArea) gridPane.lookup("#productDescription")).getText().equals("");
    }

    private boolean isProductNameEmpty() {
        return ((TextField) gridPane.lookup("#productName")).getText().isEmpty() ||
                ((TextField) gridPane.lookup("#productName")).getText().isBlank() ||
                ((TextField) gridPane.lookup("#productName")).getText().equals("");
    }

    private ComponentRegister getComponentRegister() {
        return Model.INSTANCE.getComponentRegister();
    }

    private void resetSearchField() {
        ((TextField) gridPane.lookup("#componentSearch")).setText("");
    }

    public void editComponentFromPopup(Component c) {
        if (TemporaryComponent.INSTANCE.getIsEdited()) {
            Component dup =
                    ComponentValidator.areAllFieldsComponentInRegisterThenReturnIt(
                            TemporaryComponent.INSTANCE.getTempComponent(), getComponentRegister());
            System.out.println(TemporaryComponent.INSTANCE.getTempComponent());
            if (dup == null) {
                getObservableRegister().set(getObservableRegister().indexOf(c),
                        TemporaryComponent.INSTANCE.getTempComponent());
                TemporaryComponent.INSTANCE.resetTemps();
            } else {
               /* getObservableRegister().set(getObservableRegister().indexOf(c),
                        TemporaryComponent.INSTANCE.getTempComponent());
                TemporaryComponent.INSTANCE.resetTemps();*/
                justReplaceComponent(c, dup);
                Model.INSTANCE.getComponentRegister().removeDuplicates();
            }
            try {
                FileHandling.saveAllAdminFiles();
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
            FileHandling.saveAllAdminFiles();
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

    void handleOpenOptions(String chosenFile, Alert alert) throws IOException {
        //button.get(2) == avbryt
        if (alert.getResult() == alert.getButtonTypes().get(2)) {
            return;
        }
        //button.get(1) == overskriv
        if (alert.getResult() == alert.getButtonTypes().get(1)) {
            try {
                overWriteList(chosenFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //button.get(0) == legg til
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            threadHandler.openInputThread(chosenFile);
            Model.INSTANCE.appendComponentRegisterIntoModel();
            Model.INSTANCE.getComponentRegister().removeDuplicates();
            tabComponentsController.updateView();
        }
    }

    public void overWriteList(String chosenFile) throws IOException {
        threadHandler.openInputThread(chosenFile);
        Model.INSTANCE.loadComponentRegisterIntoModel();
        Model.INSTANCE.getComponentRegister().removeDuplicates();
        tabComponentsController.updateView();

    }
}