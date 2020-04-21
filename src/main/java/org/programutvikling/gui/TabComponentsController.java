package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.component.io.InvalidComponentFormatException;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.gui.utility.Search;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TabComponentsController {
    @FXML
    AnchorPane topLevelPane;
    ComponentTypes componentTypes = new ComponentTypes();
    ThreadHandler threadHandler;
    FileHandling fileHandling = new FileHandling();
    private Stage stage;
    private RegistryComponentLogic registryComponentLogic;
    private Converter.DoubleStringConverter doubleStrConverter
            = new Converter.DoubleStringConverter();
    @FXML
    private GridPane componentRegNode;
    @FXML
    private Label lblComponentMsg;
    @FXML
    private ChoiceBox<String> cbType;
    @FXML
    private ComboBox<String> cbRecentFiles;
    @FXML
    private TextField componentSearch;
    @FXML
    private ChoiceBox<String> cbTypeFilter;
    @FXML
    private TableView<Component> tblViewComponent;
    @FXML
    private TableColumn<TableView<Component>, Double> productPriceColumn;

    @FXML
    public void initialize() throws IOException {
        initChoiceBoxes();
        registryComponentLogic = new RegistryComponentLogic(componentRegNode);
        updateComponentList();
        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(doubleStrConverter));
        threadHandler = new ThreadHandler(this);
        tblViewComponent.setOnMouseClicked((MouseEvent event) -> tblViewComponent.sort());
    }

    private ObservableList<Component> getObservableRegister() {
        return ContextModel.INSTANCE.getComponentRegister().getObservableRegister();
    }

    private ComponentRegister getComponentRegister() {
        return ContextModel.INSTANCE.getComponentRegister();
    }

    private void saveAll() throws IOException {
        fileHandling.saveAll();
    }

    private void initChoiceBoxes() {
        cbTypeFilter.setValue("Ingen filter");
        cbRecentFiles.setOnMouseClicked((MouseEvent event) -> updateRecentFiles());
        updateRecentFiles();
        cbType.setItems(componentTypes.getObservableTypeListName());
        cbTypeFilter.setItems(componentTypes.getObservableTypeListNameForFilter());
    }

    @FXML
    public void refreshTableAndSave() throws IOException {
        tblViewComponent.refresh();
        updateComponentList();
        fileHandling.saveAll();
    }

    @FXML
    void btnAddFromFile(ActionEvent event) throws IOException {
        openFileFromChooserWithThreadSleep();
        ContextModel.INSTANCE.loadComponentRegisterIntoModel();
        refreshTableAndSave();
    }

    public void updateRecentFiles() {
        cbRecentFiles.setItems(ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths());
    }

    @FXML
    void btnDelete(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "trykk ja for å slette", "Vil du slette ",
                tblViewComponent.getSelectionModel().getSelectedItems().get(0).getProductName());
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            Component selectedComp = tblViewComponent.getSelectionModel().getSelectedItem();
            getComponentRegister().removeComponent(selectedComp);
            updateComponentList();
            saveAll();
        }
    }

    private void updateComponentList() {
        getComponentRegister().attachTableView(tblViewComponent);
        //tblViewComponent.refresh();
    }

    private void registerComponent(){

        Component newComponent = registryComponentLogic.createComponentsFromGUIInputIFields();
        if (newComponent != null) {
            getComponentRegister().addComponent(newComponent);
            updateComponentList();
        }
    }

    public void disableGUI() {
        topLevelPane.setDisable(true);//prøver å slå av hele gridpane
    }

    public void enableGUI() {
        topLevelPane.setDisable(false);//kan ikke gjøres her
    }

    @FXML
        //Komponent(String type, String name, String description, double price)
    void btnAddComponent(ActionEvent event) throws IOException {
        /*if (inputValidated()) {
            registerComponent();
        }*/
        registerComponent();
        updateComponentList();
        fileHandling.saveAll();
    }

    void openFileFromChooserWithThreadSleep() throws IOException {
        String chosenFile = FileUtility.getFilePathFromOpenDialog(stage);
        if (chosenFile == null) {
            return;
        }
        threadHandler.openInputThread(chosenFile);
    }

    @FXML
    void btnOpenRecentFile(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Åpne nylig fil", "Vil du åpne den valgte filen, og dermed " +
                        "overskrive den nåværende listen?", "Vil du åpne ",
                cbRecentFiles.getSelectionModel().getSelectedItem());
        alert.showAndWait();
        String chosenFile = cbRecentFiles.getSelectionModel().getSelectedItem();
        if (chosenFile.equals(cbRecentFiles.getPromptText())) {
            System.out.println("velg en fil fra listen");
            return;
        }
        if (alert.getResult() == alert.getButtonTypes().get(0)) {

            threadHandler.openInputThread(chosenFile);
            ContextModel.INSTANCE.loadComponentRegisterIntoModel();
            refreshTableAndSave();
        }
    }

    @FXML
    private void filterByTypeSelected() {
        filter();
    }

    private ObservableList<Component> filter() {
        if (cbTypeFilter.getValue().equals("Ingen filter")) {
            updateComponentList();
            return getObservableRegister();
        }
        ObservableList<Component> result = getResultFromTypeFilter();
        tblViewComponent.setItems(Objects.requireNonNullElseGet(result, FXCollections::observableArrayList));
        return result;
    }

    private ObservableList<Component> getResultFromTypeFilter() {
        ObservableList<Component> result = null;
        String filterString = cbTypeFilter.getValue().toLowerCase();
        result = getComponentRegister().filterByProductType(filterString);
        return result;
    }

    @FXML
    void search(KeyEvent event) {
        if (cbTypeFilter.getValue().equals("Ingen filter") || cbTypeFilter.getValue() == null) {
            setSearchedList();
        } else {
            tblViewComponent.setItems(
                    Search.getFilteredList(getComponentRegister()
                            .filterByProductType(cbTypeFilter.getValue().toLowerCase()), componentSearch.getText()));
        }
    }

    private void setSearchedList() {
        FilteredList<Component> filteredData = Search.getFilteredList(getObservableRegister(), componentSearch.getText());
        SortedList<Component> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblViewComponent.comparatorProperty());
        tblViewComponent.setItems(sortedData);
    }




    @FXML
    private void productTypeEdited(TableColumn.CellEditEvent<Component, String> event) throws IOException {
        try {
            event.getRowValue().setProductType(event.getNewValue());
        } catch (IllegalArgumentException e) {
            Dialog.showErrorDialog("Ikke gyldig produkt: " + e.getMessage());
        }
        refreshTableAndSave();
    }

    @FXML
    private void productNameEdited(TableColumn.CellEditEvent<Component, String> event) throws IOException {
        try {
            event.getRowValue().setProductName(event.getNewValue());
        } catch (IllegalArgumentException e) {
            Dialog.showErrorDialog("Ugyldig navn: " + e.getMessage());
        }
        refreshTableAndSave();
    }

    @FXML
    private void productDescriptionEdited(TableColumn.CellEditEvent<Component, String> event) throws IOException {
        try {
            event.getRowValue().setProductDescription(event.getNewValue());
        } catch (IllegalArgumentException e) {
            Dialog.showErrorDialog("Ugyldig tegn i beskrivelse: " + e.getMessage());
        }
        refreshTableAndSave();
    }

    @FXML
    private void productPriceEdited(TableColumn.CellEditEvent<Component, Double> event) throws IOException {
        try {
            if (doubleStrConverter.wasSuccessful()) {
                event.getRowValue().setProductPrice(event.getNewValue());
            }
        } catch (NumberFormatException e) {
            Dialog.showErrorDialog("Ugyldig pris: " + e.getMessage());
        } catch (IllegalArgumentException i) {
            Dialog.showErrorDialog("Ugyldige tegn: " + i.getMessage());
        }
        refreshTableAndSave();
    }
}