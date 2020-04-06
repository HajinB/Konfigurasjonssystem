package org.programutvikling.gui;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.component.io.iothread.InputThread;
import org.programutvikling.user.UserPreferences;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.prefs.Preferences;

//todo: få til å loade alle nødvendige files fra components folderen - ikke bare forhåndsvalgt fil

//todo:

public class SecondaryController {
    private Stage stage;
    Preferences prefs;
    String componentPath;// = Paths.get(("FileDirectory/Components/ComponentList.jobj"));
    FileHandling fileHandling;
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
    @FXML
    private GridPane gridPane;
    @FXML
    private Button btnLeggTil;

    private ComponentRegister componentRegister = new ComponentRegister();

    private Converter.StringDoubleConverter strDoubleConverter
            = new Converter.StringDoubleConverter();

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
        choiceBoxVaretype.setItems(componentTypes.getConcreteTypeListName());
        updateList();
        kolonneType.setCellValueFactory(new PropertyValueFactory<Component, String>("type"));
        kolonneVNavn.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        kolonneBesk.setCellValueFactory(new PropertyValueFactory<Component, String>("description"));
        kolonnePris.setCellValueFactory(new PropertyValueFactory<Component, Double>("price"));

        kolonneType.setCellFactory(TextFieldTableCell.forTableColumn());
        kolonneVNavn.setCellFactory(TextFieldTableCell.forTableColumn());
        kolonneBesk.setCellFactory(TextFieldTableCell.forTableColumn());
        kolonnePris.setCellFactory(TextFieldTableCell.forTableColumn(new Converter.StringDoubleConverter()));


        //componentPath = userPreferences.getPathToUser();
        //Path userDirPath =
        //System.out.println(directoryPath.toString());
            //bare lag en metode som gjør alt dette!

        loadRegisterFromFile();
        //Path componentPath = Paths.get(("FileDirectory/Components/ComponentList.jobj"));
        //sender ut gridpane for å få tak i nodes i en annen class.
        registryComponentLogic = new RegistryComponentLogic(gridPane);
        kolonnePris.setCellFactory(TextFieldTableCell.forTableColumn(strDoubleConverter));
        //System.out.println(componentRegister.toString());
        updateList();
        refreshTable();
    }




    @FXML
    public void refreshTable() {
        tblView.refresh();
    }


    @FXML
    void btnOpen(ActionEvent event) {
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


    private void openFileWithThreadSleep() {
        InputThread task = new InputThread(componentRegister, userPreferences.getPathToUser());
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        startThread(task);
    }

    private void startThread(InputThread task) {
        Thread th = new Thread(task);
        th.setDaemon(true);
        gridPane.setDisable(true);//prøver å slå av hele gridpane
        th.start();
        task.call();  //call bruker filepathen fra konstruktøren til InputThread til å åpne/laste inn
    }

    private void threadDone(WorkerStateEvent e) {
        Dialog.showSuccessDialog("Opening complete");
        //btnLeggTil.getclass.setDisable(false);
        gridPane.setDisable(false);
        //btnSaveID.setDisable(false);
    }


    private void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        Dialog.showErrorDialog("Avviket sier: " + e.getMessage());
        gridPane.setDisable(false);
        gridPane.setDisable(false);
    }

    private void loadRegisterFromFile() throws IOException {
        File file = new File((userPreferences.getPathToUser()));
        if(file.exists()){
            FileHandling.loadAppFiles(componentRegister, userPreferences.getPathToUser());
        }
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

    @FXML
    void btnOpenJobj(ActionEvent event) {
        openFileWithThreadSleep();
        //FileHandling.openFile(componentRegister, "FileDirectory/Components/ComponentList.jobj");
    }


    @FXML
    void btnFjern(ActionEvent event) {
        //fjern fra directory og array ?
        if (tblView.getSelectionModel().isEmpty()) {
            lblBekreftelse.setText("Velg en rad for å slette den"); //bytt til tblview label
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slette rad");
            alert.setHeaderText("Vil du slette den valgte raden?");
            //gjør en gang til - "vil du slette for godt"?

            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                Component selectedComponent = tblView.getSelectionModel().getSelectedItem();

                componentRegister.removeComponent((selectedComponent));
                //etter man har sletta fra lista burde man vi brukeren valget mellom å slette for godt eller slette
                // fra viewen? elns?
                updateList();
                lblBekreftelse.setText("");
            }
        }
    }


    private Component createComponentFromGUI() {
        return new Component(choiceBoxVaretype.getValue(),
                inputVarenavn.getText(),
                inputBeskrivelse.getText(),
                strDoubleConverter.stringTilDouble(inputPris.getText()));
    }

    @FXML
    void btnFraFil(ActionEvent event) {

        Component komponent = new Component("2", "ffsaddfs", "asffsa", 299.00);
    }

    private void updateList() {
        componentRegister.attachTableView(tblView);
    }

    @FXML
    void btnSetDirectory(ActionEvent event) {
        userPreferences.setPreference(stage);
    }

    @FXML
        //Komponent(String type, String name, String description, double price)
    void btnLeggTil(ActionEvent event) throws IOException {
        componentRegister.addComponent(createComponentFromGUI());
        SaveRegister();
    }

    private void SaveRegister() throws IOException {
        FileHandling.saveFileJobj(componentRegister,
                Paths.get(userPreferences.getPathToUser()));
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
    void kolonneAntEdit(ActionEvent event) {

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



    @FXML
    void kolonneVNrEdit(ActionEvent event) {

    }

}