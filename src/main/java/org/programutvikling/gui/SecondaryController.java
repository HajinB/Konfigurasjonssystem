package org.programutvikling.gui;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.component.ItemUsable;
import org.programutvikling.component.io.iothread.InputThread;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.user.UserPreferences;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.user.UserRegister;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.prefs.Preferences;

//todo: få til å loade alle nødvendige files fra components folderen - ikke bare forhåndsvalgt fil

//todo:

public class SecondaryController {
    private Stage stage;
    private RegistryComponentLogic registryComponentLogic;
    //default path:
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    @FXML
    private MenuBar menyBar;
    @FXML
    private Label tblOverskrift;
    @FXML
    private TextField inputVaretype;
    @FXML
    private TextField inputVarenavn;
    @FXML
    private TextArea inputBeskrivelse;
    @FXML
    private TextField inputPris;
    //private RegistrerKomponent registerKomponent;
    UserRegister userRegister = new UserRegister();
    ComputerRegister computerRegister;

    ArrayList<Object> objectsForSaving = new ArrayList<>();
    @FXML
    private GridPane gridPaneSuperbruker;
    @FXML
    private Button btnLeggTil;

    private ComponentRegister componentRegister = new ComponentRegister();

    private Converter.StringDoubleConverter strDoubleConverter
            = new Converter.StringDoubleConverter();

    ThreadHandler threadHandler;

    @FXML
    private Label lblBekreftelse;
    @FXML
    private TextField inputSok;

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
    private ChoiceBox<String> choiceBoxVaretype;
    @FXML
    private TableView<Component> tblView;
    @FXML
    private TableColumn<Component, String> kolonneType;
    @FXML
    private TableColumn<Component, String> kolonneVNavn;
    @FXML
    private TableColumn<Component, String> kolonneBesk;
    @FXML
    private TableColumn<Component, Double> kolonnePris;
    @FXML
    ComponentTypes componentTypes = new ComponentTypes();



    public void initialize() throws IOException {
        initChoiceBox();
        initColumns();
        loadRegisterFromDirectory();
        //sender ut gridpane for å få tak i nodes i en annen class.
        registryComponentLogic = new RegistryComponentLogic(gridPaneSuperbruker);
        updateList();
        refreshTable();
        threadHandler = new ThreadHandler(stage, gridPaneSuperbruker);
        tblView.refresh();
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
        kolonnePris.setCellFactory(TextFieldTableCell.forTableColumn(new Converter.StringDoubleConverter()));
        kolonnePris.setCellFactory(TextFieldTableCell.forTableColumn(strDoubleConverter));
    }

    @FXML
    public void refreshTable() {
        tblView.refresh();
    }

    private void updateList() {
        componentRegister.attachTableView(tblView);
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

    private void openFileWithThreadSleep(ComponentRegister componentRegister, String s) {
        //componentRegister.getRegister();
        ;
        openInputThread(componentRegister.objectArrayListAdapter(), s);
    }

    private void loadRegisterFromDirectory() throws IOException {
        File file = new File((userPreferences.getPathToUser()));
        if (file.exists()) {
            FileHandling.loadAppFiles(objectsForSaving, userPreferences.getPathToUser());
            loadObjectsIntoClasses();
        }
    }

    @FXML
    void btnOpenJobj(ActionEvent event) {

        openFileWithThreadSleep(componentRegister, userPreferences.getPathToUser());
        //FileHandling.openFile(componentRegister, "FileDirectory/Components/ComponentList.jobj");
    }


    private boolean checkAlertForOk(Alert alert) {
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

    @FXML
    void btnFjern(ActionEvent event) {
        //fjern fra directory og array ?
        if (tblView.getSelectionModel().isEmpty()) {
            lblBekreftelse.setText("Velg en rad for å slette den"); //bytt til tblview label
        } else {
           Alert alert = Dialog.getAlert("Slette rad", "Vil du slette den valgte raden?" );
            //gjør en gang til - "vil du slette for godt"?

            if(checkAlertForOk(alert)){
                slettRad();
            }

        }
    }

    private void slettRad() {
        Component selectedComponent = tblView.getSelectionModel().getSelectedItem();
        componentRegister.removeComponent((selectedComponent));
        //etter man har sletta fra lista burde man vi brukeren valget mellom å slette for godt eller slette
        // fra viewen? elns?
        updateList();
        lblBekreftelse.setText("");
    }

    private Component createComponentFromGUI() {
        return new Component(choiceBoxVaretype.getValue(),
                inputVarenavn.getText(),
                inputBeskrivelse.getText(),
                strDoubleConverter.stringTilDouble(inputPris.getText()));
    }

    @FXML
    void btnFraFil(ActionEvent event) {
        openFileFromChooserWithThreadSleep(componentRegister);
    }

    boolean inputValidated(){
        //send inputfields til en metode - valider de en egen klasse.
        return true;
    }

    @FXML
    void btnSetDirectory(ActionEvent event) {
        userPreferences.setPreference(stage);
    }

    @FXML
        //Komponent(String type, String name, String description, double price)
    void btnLeggTil(ActionEvent event) throws IOException {
        if(inputValidated()) {
            componentRegister.addComponent(createComponentFromGUI());
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
        openInputThread(objects, chosenPath);
    }

    private void startThread(InputThread task) {
        Thread th = new Thread(task);
        th.setDaemon(true);
        disableGUI();
        th.start();
        task.call();  //call bruker filepathen fra konstruktøren til InputThread til å åpne/laste inn
    }

    private void disableGUI() {
        gridPaneSuperbruker.setDisable(true);//prøver å slå av hele gridpane
    }
    private void enableGUI() {
        gridPaneSuperbruker.setDisable(false);//kan ikke gjøres her
    }


    private void threadDone(WorkerStateEvent e) {
        Dialog.showSuccessDialog("Opening complete");
        //btnLeggTil.getclass.setDisable(false);
        enableGUI();
        //btnSaveID.setDisable(false);
    }



    private void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        Dialog.showErrorDialog("Avviket sier: " + e.getMessage());
        enableGUI();
    }

    //skal det åpnes bare componentregister også??? hvordan kan man gjøre det?
    //altså superbruker må jo kunne legge til Componentregister fra fil???
    //openInputThread bør jo da være kun for componentregister ? altså threadinga er bare for componentregister??
    void openInputThread(ArrayList<Object> componentRegister, String s) {
        InputThread task = new InputThread(componentRegister, s);
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        startThread(task);
    }

    private void SaveAll() throws IOException {

        //lager en SVÆR arraylist som holder alle de objektene vi trenger for ikke la data gå tapt.
        ArrayList<Object> objects = createObjectList(componentRegister, userRegister, computerRegister);


        FileHandling.saveFileJobj(objects,
                Paths.get(userPreferences.getPathToUser()));
    }

    private void readObjectList(){

    }
//1 - lagrer en svær object liste - det er denne som blir lagret som jobj.
    //2 på init, eller ved oppstart, blir alt lastet inn i minnet.
    private ArrayList<Object> createObjectList(ComponentRegister componentRegister, UserRegister user,
                                               ComputerRegister computerRegister ){
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(componentRegister);
        objects.add(user);
        objects.add(computerRegister);

        return objects;
    }

    void loadObjectsIntoClasses(){
        //første index er componentregister
        //2. = userregister
        //3 = computerregister      disse tre er egentlig alt man trenger (for auto-load all files).
        componentRegister = (ComponentRegister) (objectsForSaving.get(0));
        userRegister = (UserRegister) objectsForSaving.get(1);
        computerRegister = (ComputerRegister) objectsForSaving.get(2);
    }

    @FXML
    void btnLeggTilBruker(ActionEvent event) throws IOException {

    }

    @FXML
    void btnLoggUt(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void inputSok(KeyEvent event) {

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


}