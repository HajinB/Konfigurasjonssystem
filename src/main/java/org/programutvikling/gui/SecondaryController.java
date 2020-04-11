package org.programutvikling.gui;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentTypes;
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
import java.util.prefs.Preferences;

//todo: få til å loade alle nødvendige files fra components folderen - ikke bare forhåndsvalgt fil

//todo:

public class SecondaryController {
    Preferences prefs;
    String componentPath;// = Paths.get(("FileDirectory/Components/ComponentList.jobj"));
    FileHandling fileHandling;
    ArrayList<Object> objectsForSaving = new ArrayList<>();
    //private RegistrerKomponent registerKomponent;
    UserRegister userRegister = new UserRegister();
    ComputerRegister computerRegister;
    ThreadHandler threadHandler;
    ComponentTypes componentTypes = new ComponentTypes();
    int componentname = 1;
    Path directoryPath = Paths.get("FileDirectory");
    private Stage stage;
    private RegistryComponentLogic registryComponentLogic;
    //default path:
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    @FXML
    private MenuBar menyBar;
    @FXML
    private Label tblOverskrift;
    private ComponentRegister componentRegister = new ComponentRegister();
    private Converter.DoubleStringConverter doubleStrConverter
            = new Converter.DoubleStringConverter();
    @FXML
    private Tab tabComponents;
    @FXML
    private GridPane componentReg;
    @FXML
    private Label lblComponentMsg;
    @FXML
    private Button btnLeggTil;
    @FXML
    private ChoiceBox<String> cbType;
    @FXML
    private TextField componentSearch;
    @FXML
    private ChoiceBox<?> cpTypeFilter;
    @FXML
    private TextField inputVaretype;
    @FXML
    private TextField inputVarenavn;
    @FXML
    private TextArea inputBeskrivelse;
    @FXML
    private TextField inputPris;
    private TableView<Component> tblViewComponent;
    @FXML
    private TableColumn<Component, Double> productPriceColumn;
    @FXML
    private GridPane gridPaneSuperbruker;
    @FXML
    private ChoiceBox<String> choiceBoxVaretype;
    @FXML
    private TableColumn<Component, String> kolonneType;
    @FXML
    private TableColumn<Component, String> kolonneVNavn;
    @FXML
    private TableColumn<Component, String> kolonneBesk;
    @FXML
    private TableColumn<Component, Double> kolonnePris;
    @FXML
    private Tab tabUsers;
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() throws IOException {
        initChoiceBox();
        initColumns();
        loadRegisterFromDirectory();
        //sender ut gridpane for å få tak i nodes i en annen class.
        registryComponentLogic = new RegistryComponentLogic(gridPaneSuperbruker);
        updateList();
        refreshTable();
        threadHandler = new ThreadHandler(stage, gridPaneSuperbruker);
        tblViewComponent.refresh();
        //componentPath = userPreferences.getPathToUser();
        //Path userDirPath =
        //System.out.println(directoryPath.toString());
        //bare lag en metode som gjør alt dette!

        loadRegisterFromFile();

        //Path componentPath = Paths.get(("FileDirectory/Components/ComponentList.jobj"));
        //sender ut gridpane for å få tak i nodes i en annen class.
        registryComponentLogic = new RegistryComponentLogic(componentReg);

        //System.out.println(componentRegister.toString());
        updateComponentList();
        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(doubleStrConverter));

    }

    private void initChoiceBox() {
        choiceBoxVaretype.setItems(componentTypes.getConcreteTypeListName());
    }

    private void initColumns() {
        kolonneType.setCellValueFactory(new PropertyValueFactory<Component, String>("type"));
        kolonneVNavn.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        kolonneBesk.setCellValueFactory(new PropertyValueFactory<Component, String>("description"));
        kolonnePris.setCellValueFactory(new PropertyValueFactory<Component, Double>("price"));

        kolonneType.setCellFactory(TextFieldTableCell.forTableColumn());
        kolonneVNavn.setCellFactory(TextFieldTableCell.forTableColumn());
        kolonneBesk.setCellFactory(TextFieldTableCell.forTableColumn());
        //kolonnePris.setCellFactory(TextFieldTableCell.forTableColumn(new Converter.DoubleStringConverter()));
        kolonnePris.setCellFactory(TextFieldTableCell.forTableColumn(doubleStrConverter));
    }

    @FXML
    public void refreshTable() {
        tblViewComponent.refresh();
    }

    private void updateList() {
        componentRegister.attachTableView(tblViewComponent);
    }

    @FXML
    void btnAddFromFile(ActionEvent event) {
        //bruker openfilethread somegen klasse som arver Task - så setter man metoder til å være failed eller done
        // (skrur av knappene i det milisekundet det tar å laste inn en fil)
        openFileWithThreadSleep();


        //GJør åpningen i new trheead!!!!!
       /* JComboBox cb = (JComboBox)e.getSource();
        String petName = (String)cb.getSelectedItem();
        updateLabel(petName);*/
        /*string selected = openCombobox.getSele
        BufferedReader reader = Files.newBufferedReader(Paths.get(path))*/

    }

    @FXML
    void btnOpen(ActionEvent event) {
        //bruker openfilethread somegen klasse som arver Task - så setter man metoder til å være failed eller done
        // (skrur av knappene i det milisekundet det tar å laste inn en fil)
        //GJør åpningen i new trheead!!!!!
       /* JComboBox cb = (JComboBox)e.getSource();
        String petName = (String)cb.getSelectedItem();
        updateLabel(petName);*/
        /*string selected = openCombobox.getSele
        BufferedReader reader = Files.newBufferedReader(Paths.get(path))*/
    }

    //todo delete filen componentlist etter den er loada inn i initialize?
    // slik at man lager en ny hver gang? auto save..

    //bør man delete den rett før man kjører savejobj? er det det som er greia?

    //todo det som skjer er at den prøver å appende på filen som er der, slik at den ikke er riktig
    // format, altså den appender binary filene til en arraylist til en eksisterende fil  - da finner
    // ikke openjobj filen..

    //http://javafxportal.blogspot.com/2012/03/java-deleting-file-or-directory.html

    //    System.out.println(componentRegister.getRegister().get(1));

                /*filbehandling.loadJobjFromDirectory(stage, componentRegister, Paths.get("FileDirectory/ConfigMain" +
                        ".jobj"));*/

    private void loadRegisterFromDirectory() throws IOException {
        File file = new File((userPreferences.getPathToUser()));
        if (file.exists()) {
            FileHandling.loadAppFiles(objectsForSaving, userPreferences.getPathToUser());
            loadObjectsIntoClasses();
        }
    }



    private void openFileWithThreadSleep(ComponentRegister componentRegister, String s) {
        //componentRegister.getRegister();
        ;
        openInputThread(componentRegister, s);
    }

    private void openFileWithThreadSleep() {
        InputThread task = new InputThread(componentRegister, userPreferences.getPathToUser());
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        startThread(task);
    }



    private void startThread(InputThread task) {
        Thread th = new Thread(task);
        th.setDaemon(true);
        componentReg.setDisable(true);//prøver å slå av hele gridpane
        th.start();
        task.call();  //call bruker filepathen fra konstruktøren til å åpne/laste inn
    }

    private void threadDone(WorkerStateEvent e) {
        Dialog.showSuccessDialog("Opening complete");
        //btnLeggTil.getclass.setDisable(false);
        componentReg.setDisable(false);
        //btnSaveID.setDisable(false);
    }

    private void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        Dialog.showErrorDialog("Avviket sier: " + e.getMessage());
        componentReg.setDisable(false);
        componentReg.setDisable(false);
    }

    private void loadRegisterFromFile() throws IOException {
        File file = new File(String.valueOf(userPreferences.getPathToUser()));
        String path = file.getAbsolutePath();

        if (file.exists()) {
            FileHandling.loadAppFiles(objectsForSaving, userPreferences.getPathToUser());
            System.out.println(componentRegister.toString());
        }
    }

    @FXML
    void btnOpenJobj(ActionEvent event) {

        FileHandling.openFile(objectsForSaving, "FileDirectory/Components/ComponentList.jobj");
    }

    //https://www.youtube.com/watch?v=EVEiePe_UVw hvordan slette fra tableview
    @FXML
    void btnDelete(ActionEvent event) {
        ObservableList<Component> allProduct, SingleProduct;
        allProduct = tblViewComponent.getItems();
        SingleProduct = tblViewComponent.getSelectionModel().getSelectedItems();
        SingleProduct.forEach(allProduct::remove);
        //fjern fra directory og array ?
    }

    /* Funker ikke om vi bruker gridpane
    private Component createComponentFromGUI() {
        return new Component(choiceBoxVare.getValue(),
                inputVarenavn.getText(),
                inputBeskrivelse.getText(),
                doubleStrConverter.stringTilDouble(inputPris.getText()));
    }*/
    private void updateComponentList() {
        componentRegister.attachTableView(tblViewComponent);
    }

    @FXML
    void btnOpenFile(ActionEvent event) {

        Component komponent = new Component("2", "ffsaddfs", "asffsa", 299.00);
    }

    private void loadFromDirectory() {
    }

    @FXML
    void btnSetDirectory(ActionEvent event) {
        userPreferences.setPreference(stage);
    }

    @FXML
    void btnFraFil(ActionEvent event) {
        openFileFromChooserWithThreadSleep(componentRegister);
    }

    boolean inputValidated() {
        //send inputfields til en metode - valider de en egen klasse.
        return true;
    }

    private void registerComponent() throws InvalidComponentFormatException {

        Component newComponent = registryComponentLogic.createComponentsFromGUIInputIFields();
        if (newComponent != null) {
            componentRegister.addComponent(newComponent);
        }
    }

    /*
        private void registerComponent() {

            Component newComponent = registryComponentLogic.createComponentsFromGUIInputIFields();
            if (newComponent != null) {
                componentRegister.addComponent(newComponent);
            }
        }
    */
    private void disableGUI() {
        gridPaneSuperbruker.setDisable(true);//prøver å slå av hele gridpane
    }

    private void enableGUI() {
        gridPaneSuperbruker.setDisable(false);//kan ikke gjøres her
    }

    @FXML
        //Komponent(String type, String name, String description, double price)
    void btnAddComponent(ActionEvent event) throws IOException {
        if (inputValidated()) {
            registerComponent();
            SaveAll();
        }

    }

    void openFileFromChooserWithThreadSleep(ComponentRegister componentRegister) {
        FileChooser fileChooser = new FileChooser();
        File path = fileChooser.showOpenDialog(stage);
        //path her blir ikke riktig.
        String chosenPath = FileHandling.getStringPathFromFile(path);
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(componentRegister);
        openInputThread(componentRegister, chosenPath);
    }

    //skal det åpnes bare componentregister også??? hvordan kan man gjøre det?
    //altså superbruker må jo kunne legge til Componentregister fra fil???
    //openInputThread bør jo da være kun for componentregister ? altså threadinga er bare for componentregister??
    void openInputThread(ComponentRegister componentRegister, String s) {
        InputThread task = new InputThread(componentRegister, s);
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        startThread(task);
    }

    private void SaveAll() throws IOException {

        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objects = createObjectList(componentRegister, null, null);


        FileHandling.saveFileJobj(objects,
                Paths.get(userPreferences.getPathToUser()));
    }

    //1 - lagrer en svær object liste - det er denne som blir lagret som jobj.
    //2 på init, eller ved oppstart, blir alt lastet inn i minnet.
    private ArrayList<Object> createObjectList(ComponentRegister componentRegister, UserRegister user,
                                               ComputerRegister computerRegister) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(componentRegister);
        objects.add(user);
        objects.add(computerRegister);

        return objects;
    }

    void loadObjectsIntoClasses() {
        //første index er componentregister
        //2. = userregister
        //3 = computerregister      disse tre er egentlig alt man trenger (for auto-load all files).
        componentRegister = (ComponentRegister) (objectsForSaving.get(0));
        userRegister = (UserRegister) objectsForSaving.get(1);
        computerRegister = (ComputerRegister) objectsForSaving.get(2);
    }

    @FXML
    void btnLogOut(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }


    @FXML
    void kolonneTypeEdit(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setType(event.getNewValue());
        updateList();
    }

    @FXML
    void kolonneVNavnEdit(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setName(event.getNewValue());
        updateList();
    }

    @FXML
    void kolonneBeskEdit(TableColumn.CellEditEvent<Component, String> event) {
        event.getRowValue().setDescription(event.getNewValue());
        updateList();

    }

    @FXML
    void kolonnePrisEdit(TableColumn.CellEditEvent<Component, Double> event) {

        event.getRowValue().setPrice(event.getNewValue());
        updateList();

    }

    // Tableview edit
    // CellEdit - problem: Endringene er ikke varige (til neste gang man åpner).
    // Går ikke an å endre pris heller??
    @FXML
    private void productTypeEdited(TableColumn.CellEditEvent<Component, String> event) {
        try {
            event.getRowValue().setProductType(event.getNewValue());
        } catch (IllegalArgumentException e) {
            Dialog.showErrorDialog("Ikke gyldig produkt: " + e.getMessage());
        }
        tblViewComponent.refresh();
    }

    @FXML
    private void productNameEdited(TableColumn.CellEditEvent<Component, String> event) {
        try {
            event.getRowValue().setProductName(event.getNewValue());
        } catch (IllegalArgumentException e) {
            Dialog.showErrorDialog("Ugyldig navn: " + e.getMessage());
        }
        tblViewComponent.refresh();
    }

    @FXML
    private void productDescriptionEdited(TableColumn.CellEditEvent<Component, String> event) {
        try {
            event.getRowValue().setProductDescription(event.getNewValue());
        } catch (IllegalArgumentException e) {
            Dialog.showErrorDialog("Ugyldig tegn i beskrivelse: " + e.getMessage());
        }
        tblViewComponent.refresh();
    }

    @FXML
    private void productPriceEdited(TableColumn.CellEditEvent<Component, Double> event) {
        try {
            if (doubleStrConverter.wasSuccessful()) {
                event.getRowValue().setProductPrice(event.getNewValue());
            }
        } catch (NumberFormatException e) {
            Dialog.showErrorDialog("Ugyldig pris: " + e.getMessage());
        }
        tblViewComponent.refresh();
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