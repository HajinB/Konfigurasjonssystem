package org.programutvikling.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.component.ComponentValidator;
import org.programutvikling.component.io.InvalidComponentFormatException;
import org.programutvikling.component.io.iothread.InputThread;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.user.UserPreferences;
import org.programutvikling.user.UserRegister;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

//todo:

public class SecondaryController {
    @FXML
    BorderPane topLevelPane;
    ComponentTypes componentTypes = new ComponentTypes();
    private Stage stage;
    private RegistryComponentLogic registryComponentLogic;
    ThreadHandler threadHandler;

    //default path:
    //todo set metoden til userpreferences pathen fungerer ikke (får vi uttelling for å lagre brukerpath? - eller bør
    // den være umulig å endre)
    @FXML
    private ProgressBar progressBar;
    ContextModel model = ContextModel.INSTANCE;
    private ComputerRegister computerRegister = model.getComputerRegister();
    private ComponentRegister componentRegister = model.getComponentRegister();
    private Converter.DoubleStringConverter doubleStrConverter
            = new Converter.DoubleStringConverter();
    @FXML
    private GridPane componentReg;
    @FXML
    private Label lblComponentMsg;
    @FXML
    private ChoiceBox<String> cbType;
    @FXML
    private TextField componentSearch;
    @FXML
    private ChoiceBox<String> cpTypeFilter;
    @FXML
    private TableView<Component> tblViewComponent;
    @FXML
    private TableColumn<TableView<Component>, Double> productPriceColumn;
    @FXML
    private GridPane userReg;
    @FXML
    private Label lblUserMsg;
    @FXML
    private TextField userSearch;
    @FXML
    private TableView<?> tblViewUser;

    private static void deletefile(String file) {
        File f1 = new File(file);
        boolean success = f1.delete();

        if (!success) {
            System.out.println("Deletion failed.");

            //System.exit(0);
        } else {
            System.out.println("File deleted.");
        }
    }

    public void shutdown() throws IOException {
        // cleanup code here...
        saveAll();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
FileHandling fileHandling = new FileHandling();

    private void saveAll() throws IOException {

        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objectsToSave = fileHandling.createObjectList(componentRegister, computerRegister);
        FileHandling.autoSaveFileJobj(objectsToSave,
                Paths.get(fileHandling.getPathToUser()));
    }

    private void autoSave() throws IOException {
        fileHandling.saveAll();
    }

    @FXML
    public void initialize() throws IOException {
        initChoiceBox();
        loadRegisterFromFile();
        cpTypeFilter.setValue("Ingen filter");
        registryComponentLogic = new RegistryComponentLogic(componentReg);
        updateComponentList();
        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(doubleStrConverter));
        saveTimer();
        threadHandler = new ThreadHandler(stage, componentReg, this);
        tblViewComponent.setOnMouseClicked(( MouseEvent event )-> tblViewComponent.sort());
    }

    private void initChoiceBox() {
        cbType.setItems(componentTypes.getObservableTypeListName());
        cpTypeFilter.setItems(componentTypes.getObservableTypeListNameForFilter());
    }

    @FXML
    public void refreshTableAndSave() throws IOException {
        tblViewComponent.refresh();
        fileHandling.saveAll();
    }

    @FXML
    void btnAddFromFile(ActionEvent event) {
        openFileFromChooserWithThreadSleep(componentRegister);
    }

    private void loadRegisterFromFile() throws IOException {
        File file = new File(String.valueOf(fileHandling.getPathToUser()));
        String path = file.getAbsolutePath();
        if (file.exists()) {
            //currentContext.getComponentRegister().getRegister().addAll(
                    FileHandling.openObjects(model.getCleanObjectList(),
                    fileHandling.getPathToUser());
            System.out.println(componentRegister.toString());
        }
    }

    @FXML
    void btnOpenJobj(ActionEvent event) {
        openFileFromChooserWithThreadSleep(componentRegister);
    }

    @FXML
    void btnDelete(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "trykk yes for å slette",
                tblViewComponent.getSelectionModel().getSelectedItems().get(0).getProductName());
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            Component selectedComp = tblViewComponent.getSelectionModel().getSelectedItem();
            componentRegister.removeComponent(selectedComp);
            updateComponentList();
            tblViewComponent.refresh();
            saveAll();
        }
        //fjern fra directory og array ?
    }

    private void updateComponentList() {
        componentRegister.attachTableView(tblViewComponent);
    }

    @FXML
    void btnOpenFile(ActionEvent event) {

        Component komponent = new Component("2", "ffsaddfs", "asffsa", 299.00);
    }

    @FXML
    void btnSetDirectory(ActionEvent event) {
        fileHandling.getUserPreferences().setPreference(stage);
    }


    private void registerComponent() throws InvalidComponentFormatException {

        Component newComponent = registryComponentLogic.createComponentsFromGUIInputIFields();
        if (newComponent != null) {
            componentRegister.addComponent(newComponent);
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

    void openFileFromChooserWithThreadSleep(ComponentRegister componentRegister) {
        String chosenPath = FileHandling.getFilePathFromFileChooser(stage);
        //path her blir ikke riktig.
        //String chosenPath = FileHandling.getStringPathFromFile(path);
        ArrayList<Object> objects = new ArrayList<>();
        threadHandler.openInputThread(chosenPath);
        updateComponentList();
    }

     @FXML
     private void filterByTypeSelected(){
        filter();
     }

    private ObservableList<Component> filter() {
        if(cpTypeFilter.getValue().equals("Ingen filter")) {
            updateComponentList();
            return componentRegister.getObservableRegister();
        }
        ObservableList<Component> result = null;
        String filterString = cpTypeFilter.getValue().toLowerCase();
        result = componentRegister.filterByProductType(filterString);
        if(result == null) {
            tblViewComponent.setItems(FXCollections.observableArrayList());
        } else {
            tblViewComponent.setItems(result);
        }
        return result;
    }

    @FXML
    void search(KeyEvent event) {
         if(cpTypeFilter.getValue().equals("Ingen filter") || cpTypeFilter.getValue() == null ){
             FilteredList<Component> filteredData = getFiltered(componentRegister.getObservableRegister());
             // 3. Lager en ny liste som er en sortertversjon
            SortedList<Component> sortedData = new SortedList<>(filteredData);
            // 4. "binder" denne sorterte listen og sammenligner det med tableviewens data
            sortedData.comparatorProperty().bind(tblViewComponent.comparatorProperty());
            // 5. legger til sortert og filtrert data
            tblViewComponent.setItems(sortedData);
            tblViewComponent.refresh();
        }else{
                 tblViewComponent.setItems(getFiltered(componentRegister.filterByProductType(cpTypeFilter.getValue().toLowerCase())));
                 tblViewComponent.refresh();
             }
        }//skal sende en liste som allerede er filtrert basert på

    private FilteredList<Component> getFiltered(ObservableList<Component> list) {
        FilteredList<Component> filteredData = new FilteredList<>(list, p -> true);
        /*FilteredList<Component> filteredData =
                new FilteredList<Component>((FilteredList<Component>) componentRegister.getRegister(), p -> true);*/
        componentSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(component -> {
                // If filter text is empty, display all persons.
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


    void loadObjectsIntoClasses() {
        //første index er componentregister
        //2. = userregister
        //3 = computerregister      disse tre er egentlig alt man trenger (for auto-load all files).
        //componentRegister = (ComponentRegister) (objectsForSaving.get(0));
        //userRegister = (UserRegister) objectsForSaving.get(1);
        //computerRegister = (ComputerRegister) objectsForSaving.get(2);
    }

    @FXML
    void btnLogOut(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    // Tableview edit
    // CellEdit - problem: Endringene er ikke varige (til neste gang man åpner).
    // Går ikke an å endre pris heller??
    @FXML
    private void productTypeEdited(TableColumn.CellEditEvent<Component, String> event) throws IOException {
        try {
            event.getRowValue().editSetProductType(event.getNewValue());
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
        } catch(IllegalArgumentException i){
            Dialog.showErrorDialog("Ugyldige tegn: " + i.getMessage());
        }
        refreshTableAndSave();
    }

    //user-fane

    @FXML
    void columnAdressEdit(ActionEvent event) {

    }

    @FXML
    void columnCtyEdit(ActionEvent event) {

    }

    @FXML
    void columnPostalEdit(ActionEvent event) {

    }

    @FXML
    void columnUMailEdit(ActionEvent event) {

    }

    @FXML
    void columnUNameEdit(ActionEvent event) {

    }

    @FXML
    void btnAddUser(ActionEvent event) {

    }

    @FXML
    void btnDeleteUser(ActionEvent event) {

    }

    @FXML
    void btnUserFromFile(ActionEvent event) {

    }

}