package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.component.io.InvalidComponentFormatException;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.gui.utility.FileUtility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//todo: fiks openthread..

public class TabComponentsController {
    @FXML
    AnchorPane topLevelPane;

    ComponentTypes componentTypes = new ComponentTypes();
    ThreadHandler threadHandler;
    FileHandling fileHandling = new FileHandling();
    private Stage stage;
    private RegistryComponentLogic registryComponentLogic;
    //default path:
    //todo set metoden til userpreferences pathen fungerer ikke (får vi uttelling for å lagre brukerpath? - eller bør
    // den være umulig å endre)
    @FXML
    private ProgressBar progressBar;

    /** Dette er ikke en grei måte å instansiere på - da blir objektet bare slik det først var når scenen ble åpna -
     * vi må alltid accesse all data fra singleton på : COntextModel.INSTANCE.get....*/
    //private ComputerRegister computerRegister = ContextModel.INSTANCE.getComputerRegister();
    //private ComponentRegister componentRegister = ContextModel.INSTANCE.getComponentRegister();
    private Converter.DoubleStringConverter doubleStrConverter
            = new Converter.DoubleStringConverter();
    @FXML
    private GridPane componentReg;
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
        initChoiceBox();
        cbTypeFilter.setValue("Ingen filter");
        registryComponentLogic = new RegistryComponentLogic(componentReg);
        updateComponentList();
        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(doubleStrConverter));
        saveTimer();
        threadHandler = new ThreadHandler(stage, componentReg, this);
        tblViewComponent.setOnMouseClicked((MouseEvent event) -> tblViewComponent.sort());
    }


    private void saveTimer() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(() -> {
            try {
                autoSave();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void saveAll() throws IOException {

        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objectsToSave = FileUtility.createObjectList(getComponentRegister(),
                ContextModel.INSTANCE.getComputerRegister());
        FileHandling.saveFileAuto(objectsToSave,
                Paths.get(fileHandling.getPathToUser()));
    }

    private void autoSave() throws IOException {
        fileHandling.saveAll();
    }


    private void initChoiceBox() {
       // initOpenRecentFiles();
        cbRecentFiles.setOnMouseClicked((MouseEvent event) -> updateRecentFiles());
        updateRecentFiles();
        cbType.setItems(componentTypes.getObservableTypeListName());
        cbTypeFilter.setItems(componentTypes.getObservableTypeListNameForFilter());
    }
    private void initOpenRecentFiles(){
        if(ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths().size()>0)
            cbRecentFiles.setItems(ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths());
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
        ContextModel.INSTANCE.loadObjectsIntoClasses();
        refreshTableAndSave();
    }

    public void updateRecentFiles(){
        cbRecentFiles.setItems(ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths());
    }

    @FXML
    void btnClickTheRecentFiles(){
        updateRecentFiles();
    }

    @FXML
    void btnOpenJobj(ActionEvent event) throws IOException {
        openFileFromChooserWithThreadSleep();
        saveAll();
    }

    @FXML
    void btnDelete(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "trykk ja for å slette",
                tblViewComponent.getSelectionModel().getSelectedItems().get(0).getProductName());
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            Component selectedComp = tblViewComponent.getSelectionModel().getSelectedItem();
            getComponentRegister().removeComponent(selectedComp);
            updateComponentList();
            saveAll();
        }
        //fjern fra directory og array ?
    }

    private void updateComponentList() {
        getComponentRegister().attachTableView(tblViewComponent);
        //tblViewComponent.refresh();
    }



    private void registerComponent() throws InvalidComponentFormatException {

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
        threadHandler.openInputThread(chosenFile);
    }

    @FXML
    private void filterByTypeSelected() {
        filter();
    }
/*
    @FXML
    private void filterByTypeSelectedRelease() {
        filter();
    }

@FXML
private void filterByTypeSelectedTyped(){
        filter();
}*/



/**https://stackoverflow.com/questions/49564002/keycode-event-for-backspace-in-javafx/49575995#49575995*/
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
       // componentSearch.setOnKeyTyped((KeyEvent event) -> event.getKeyChar() != KeyEvent.VK_BACK_SPACE);
        if(event.getCode() == KeyCode.BACK_SPACE){
            System.out.println("backspace was pressed");
            tblViewComponent.setItems(
                    getFiltered(getComponentRegister().getObservableRegister()));
            tblViewComponent.refresh();
        }

        if (cbTypeFilter.getValue().equals("Ingen filter") || cbTypeFilter.getValue() == null) {
            FilteredList<Component> filteredData = getFiltered(getObservableRegister());
            // 3. Lager en ny liste som er en sortertversjon
            SortedList<Component> sortedData = new SortedList<>(filteredData);
            // 4. "binder" denne sorterte listen og sammenligner det med tableviewens data
            sortedData.comparatorProperty().bind(tblViewComponent.comparatorProperty());
            // 5. legger til sortert og filtrert data
            tblViewComponent.setItems(sortedData);
            tblViewComponent.refresh();
        } else {
            tblViewComponent.setItems(
                    getFiltered(getComponentRegister()
                            .filterByProductType(cbTypeFilter.getValue().toLowerCase())));
            tblViewComponent.refresh();
        }
    }//skal sende en liste som allerede er filtrert basert på

    private ObservableList<Component> getObservableRegister() {
        return ContextModel.INSTANCE.getComponentRegister().getObservableRegister();
    }
    private ComponentRegister getComponentRegister(){
        return ContextModel.INSTANCE.getComponentRegister();
    }

    private FilteredList<Component> getFiltered(ObservableList<Component> list) {
        FilteredList<Component> filteredData = new FilteredList<>(list, p -> true);
        /*FilteredList<Component> filteredData =
                new FilteredList<Component>((FilteredList<Component>) componentRegister.getRegister(), p -> true);*/
        componentSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(component -> {
                // If filter text is empty, display all components
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // sammenligner alle feltene med filteret.
                String lowerCaseFilter = newValue.toLowerCase();
                if (component.getProductType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (component.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches epost
                } else if (component.getProductDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches fødselsdato
                } else
                    return Double.toString(component.getProductPrice()).toLowerCase().matches(lowerCaseFilter); // Filter
                // matches fødselsdato
                // Does not match.
            });
        });
        return filteredData;
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


    public void shutdown() throws IOException {
        saveAll();
    }



}