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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.component.io.InvalidComponentFormatException;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.user.UserPreferences;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//todo: få til å loade alle nødvendige files fra components folderen - ikke bare forhåndsvalgt fil

//todo:

public class SecondaryController {
    @FXML
    BorderPane topLevelPane;
    //ArrayList<Object> objectsForSaving = new ArrayList<>();
    //private RegistrerKomponent registerKomponent;
    ComponentTypes componentTypes = new ComponentTypes();
    private Stage stage;
    private RegistryComponentLogic registryComponentLogic;
    //default path:
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    @FXML
    private ProgressBar progressBar;

    ContextModel currentContext = ContextModel.getInstance();
    private ComputerRegister computerRegister = ContextModel.getInstance().getComputerRegister();
    private ComponentRegister componentRegister = ContextModel.getInstance().getComponentRegister();
    private Converter.DoubleStringConverter doubleStrConverter
            = new Converter.DoubleStringConverter();
    @FXML
    private Tab tabComponents;
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

    private void saveAll() throws IOException {

        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objectsToSave = createObjectList(componentRegister, computerRegister);
        //ContextModel.getInstance().getComponentRegister()

//todo husk å fyll inn der i createObjectList når vi får opp omputerregister og userregister
        FileHandling.autoSaveFileJobj(objectsToSave,
                Paths.get(userPreferences.getPathToUser()));
    }

    private void autoSave() throws IOException {
        saveAll();
    }
    ThreadHandler threadHandler;
    @FXML
    public void initialize() throws IOException {
        initChoiceBox();
        loadRegisterFromFile();
        loadObjectsIntoClasses();
        cpTypeFilter.setValue("Ingen filter");
        registryComponentLogic = new RegistryComponentLogic(componentReg);
        updateComponentList();
        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(doubleStrConverter));
        saveTimer();
        threadHandler = new ThreadHandler(stage, componentReg, this);
    }

    private void initChoiceBox() {
        cbType.setItems(componentTypes.getObservableTypeListName());
        cpTypeFilter.setItems(componentTypes.getObservableTypeListNameForFilter());
    }

    @FXML
    public void refreshTableAndSave() throws IOException {
        tblViewComponent.refresh();
        saveAll();
    }

    @FXML
    void btnAddFromFile(ActionEvent event) {
        openFileFromChooserWithThreadSleep(componentRegister);
    }

    private void loadRegisterFromFile() throws IOException {
        File file = new File(String.valueOf(userPreferences.getPathToUser()));
        String path = file.getAbsolutePath();
        if (file.exists()) {
            //currentContext.getComponentRegister().getRegister().addAll(
                    FileHandling.openObjects(currentContext.getCleanObjectList(),
                    userPreferences.getPathToUser());
            System.out.println(componentRegister.toString());
        }
    }

    @FXML
    void btnOpenJobj(ActionEvent event) {

        FileHandling.openFile(currentContext.getCleanObjectList(), "FileDirectory/Components/ComponentList.jobj");
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
        userPreferences.setPreference(stage);
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
        SaveAll();
    }

    void openFileFromChooserWithThreadSleep(ComponentRegister componentRegister) {
        String chosenPath = FileHandling.getFilePathFromFileChooser(stage);
        //path her blir ikke riktig.
        //String chosenPath = FileHandling.getStringPathFromFile(path);
        ArrayList<Object> objects = new ArrayList<>();
        threadHandler.openInputThread(componentRegister, chosenPath);
        updateComponentList();
    }

    //skal det åpnes bare componentregister også??? hvordan kan man gjøre det?
    //altså superbruker må jo kunne legge til Componentregister fra fil???
    //openInputThread bør jo da være kun for componentregister ? altså threadinga er bare for componentregister??


    private void SaveAll() throws IOException {

        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objects = createObjectList(componentRegister, null);

//todo husk å fyll inn der i createObjectList når vi får opp omputerregister og userregister evt fra en annen classe
        FileHandling.saveFileJobj(objects,
                Paths.get(userPreferences.getPathToUser()));
    }

    //1 - lagrer en svær object liste - det er denne som blir lagret som jobj.
    //2 på init, eller ved oppstart, blir alt lastet inn i minnet.
    private ArrayList<Object> createObjectList(ComponentRegister componentRegister,
                                               ComputerRegister computerRegister) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(componentRegister);
        objects.add(computerRegister);

        return objects;
    }

       /*Så hvis det er mulig å få opp alle prosessorer når man filtrer på det uten å måtte skrive noe inn på søkefeltet,
    men samtidig kunne spesifisere ord som skal være i filtreringen,
    hadde det vært superb. Hvis du skjønner hva jeg mener?*/

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
        String filterString = cpTypeFilter.getValue().toString().toLowerCase();
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
         if(cpTypeFilter.getValue().toString().equals("Ingen filter") || cpTypeFilter.getValue() == null ){
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