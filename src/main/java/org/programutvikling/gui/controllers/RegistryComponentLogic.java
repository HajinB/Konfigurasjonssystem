package org.programutvikling.gui.controllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.component.ComponentValidator;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.io.FileOpenerTxt;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.gui.customTextField.PriceField;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.Stageable;
import org.programutvikling.gui.utility.WindowHandler;
import org.programutvikling.model.Model;
import org.programutvikling.model.TemporaryComponent;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class RegistryComponentLogic implements Stageable {
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


    public RegistryComponentLogic(TabComponentsController tabComponentsController) {
        this.tabComponentsController = tabComponentsController;
        this.threadHandler = new ThreadHandler(tabComponentsController);
    }

    void registerComponent() {
        tabComponentsController.clearLabels();
        if(ComponentValidator.isThereASemiColon(returnTempComponent())){
            Dialog.showErrorDialog("Et eller flere av av tekstfeltene innholder en semikolon. På grunn av at " +
                    "komponentene skal være " +
                    "kompatible med Excel, er ikke semikolon et gyldig tegn. Vennligst fjern " +
                    "semikolon.");
            return;
        }
        if (areInputFieldsEmpty()) {
            if (isProductTypeEmpty()) {
                tabComponentsController.setLblMsgType("Velg produkttype");
            } else {
                System.out.println("produkttype er ikke empty..");
            }
            if (isProductNameEmpty()) {
                tabComponentsController.setLblMsgName("Skriv inn navn på produktet");
            }
            if (isProductDescriptionEmpty()) {
                tabComponentsController.setLblMsgDescription("Skriv inn en beskrivelse");
            }
            if (isProductPriceEmpty()) {
                tabComponentsController.setLblMsgPrice("Skriv inn pris på produktet");
            }
        } else {
            createComponentHandleDuplicate();
        }
    }

    public void setTblViewEventHandler() {
        /**detecter tablerow, for å hente ut component, for å kunne åpne edit-window*/
        tblViewComponent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getComponentRegister().getRegister().size() > 0) {
                    //skrur av cellebasert seleksjon.
                    tblViewComponent.getSelectionModel().setCellSelectionEnabled(false);
                    TableRow<? extends Component> row;
                    if (isDoubleClick(event)) {
                        Node node = ((Node) event.getTarget()).getParent();
                        if (node instanceof TableRow) {
                            row = (TableRow<Component>) node;
                        } else {
                            //hvis man trykker på tekst, altså en child av den noden vi vil ha
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

    Component returnTempComponent(){
        String productType = getCBString((ChoiceBox<String>) gridPane.lookup("#productType"));
        String productName = getString((TextField) gridPane.lookup("#productName"));
        String productDescription = getTextareaString((TextArea) gridPane.lookup("#productDescription"));
        double productPrice = getDouble((PriceField) gridPane.lookup("#productPrice"));
        return new Component(productType, productName, productDescription, productPrice);
    }


    private Component createComponent() {

        String productType = getCBString((ChoiceBox<String>) gridPane.lookup("#productType"));
        String productName = getString((TextField) gridPane.lookup("#productName"));
        String productDescription = getTextareaString((TextArea) gridPane.lookup("#productDescription"));
        double productPrice = getDouble((PriceField) gridPane.lookup("#productPrice"));


        Component componentIn = new Component(productType, productName, productDescription, productPrice);
        //kan gjøre validering på disse her - altså et felt om gangen.

        tabComponentsController.setResultLabelTimed(componentIn.getProductName() + " er lagt til i listen");
        tabComponentsController.scrollToItem();
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
        System.out.println("index to replace : " + indexToReplace);
        getComponentRegister().getRegister().set(indexToReplace, newComponent);
    }

    @Override
    public void editClickableFromPopup(Clickable c) {
        if (TemporaryComponent.INSTANCE.getIsEdited()) {
            if(ComponentValidator.isThereASemiColon(returnTempComponent())){
                Dialog.showErrorDialog("Et eller flere av av tekstfeltene innholder en semikolon. På grunn av at " +
                        "komponentene skal være " +
                        "kompatible med Excel, er ikke semikolon et gyldig tegn. Vennligst fjern " +
                        "semikolon.");
                //open popup again
                return;
            }
            Component dup =
                    ComponentValidator.areAllFieldsComponentInRegisterThenReturnIt(
                            TemporaryComponent.INSTANCE.getTempComponent(), getComponentRegister());
            if (dup == null) {
                getObservableRegister().set(getObservableRegister().indexOf(c),
                        TemporaryComponent.INSTANCE.getTempComponent());
                TemporaryComponent.INSTANCE.resetTemps();
            } else {
                justReplaceComponent((Component) c, dup);

                Model.INSTANCE.getComponentRegister().removeDuplicates();
            }
            try {
                FileHandling.saveAllAdminFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        textArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    keyEvent.consume();
                }
            }
        });
    }

     void setTextAreaMaxLength(TextArea textArea) {
        // Limit the TextArea to more than 2500 characters to prevent OutOfMemoryError
        textArea.setTextFormatter(new TextFormatter<String>(newChars ->
                newChars.getControlNewText().length() <= 2500 ? newChars : null));
    }

    void handleOpenOptions(String chosenFile, Alert alert) throws IOException {
        //button.get(2) == avbryt
        if (alert.getResult() == alert.getButtonTypes().get(2)) {
            return;
        }
        //button.get(1) == overskriv
        if (alert.getResult() == alert.getButtonTypes().get(1)) {
            System.out.println("OVERSKRIV");
            try {
                overWriteList(chosenFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //button.get(0) == legg til
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            System.out.println("LEGG TIL");

            threadHandler.openInputThread(chosenFile, this);
            Model.INSTANCE.appendComponentRegisterIntoModel();
            Model.INSTANCE.getComponentRegister().removeDuplicates();
            tabComponentsController.updateView();
        }
    }

    public void overWriteList(String chosenFile) throws IOException {
        threadHandler.openInputThread(chosenFile, this);
        Model.INSTANCE.loadComponentRegisterIntoModel();
        Model.INSTANCE.getComponentRegister().removeDuplicates();
        tabComponentsController.updateView();
    }

    public void openComputer(String chosenPath) {
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        Computer computer = new Computer("temp");
        if (chosenPath != null) {
            try {
                fileOpenerTxt.openWithoutValidation(computer, Paths.get(chosenPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tabComponentsController.setLblComponentMsg("Komponentene ble lastet inn!");
            loadComputerIntoComponentRegister(computer);
            removeDuplicates();
        }
    }


    private void removeDuplicates() {
        Model.INSTANCE.getComponentRegister().removeDuplicates();
        tabComponentsController.updateView();
    }

    private void loadComputerIntoComponentRegister(Computer computer) {
        for (Component c : computer.getComponentRegister().getObservableRegister()) {
            Model.INSTANCE.getComponentRegister().addComponent(c);
        }
    }
}