package org.programutvikling.gui;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.programutvikling.App;
import org.programutvikling.component.io.InputThread;
import org.programutvikling.component.io.InvalidComponentFormatException;
import org.programutvikling.user.UserPreferences;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private ChoiceBox<String> cbType;

    @FXML
    private TextField componentSearch;

    @FXML
    private ChoiceBox<?> cpTypeFilter;

    @FXML
    private TableView<Component> tblViewComponent;

    @FXML
    private TableColumn<Component, Double> productPriceColumn;


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

        if(file.exists()){
            FileHandling.loadAppConfigurationFile(componentRegister, userPreferences.getPathToUser());
            System.out.println(componentRegister.toString());
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
        FileHandling.openFile(componentRegister, "FileDirectory/Components/ComponentList.jobj");
    }

    //https://www.youtube.com/watch?v=EVEiePe_UVw hvordan slette fra tableview
    @FXML
    void btnDelete(ActionEvent event) {
        ObservableList <Component> allProduct, SingleProduct;
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

    int componentname = 1;
    Path directoryPath = Paths.get("FileDirectory");


    @FXML
    void btnSetDirectory(ActionEvent event) {
        userPreferences.setPreference(stage);
    }

    private void registerComponent() {

        Component newComponent = registryComponentLogic.createComponentsFromGUIInputIFields();
        if (newComponent != null) {
            componentRegister.addComponent(newComponent);
        }
    }


   @FXML
        //Komponent(String type, String name, String description, double price)
    void btnAddComponent(ActionEvent event) throws IOException {
        registerComponent();
        updateComponentList();
        // Komponent komponent = registrerKomponent.opprettKomponentFraGUIFelt();

        //todo denne folderen/directory path bør kunne bli satt av brukeren i settings elns(?)
        //File folder = new File("FileDirectory/");
        //directoryPath = Paths.("FileDirectory");
        //directoryPath = new File(folder.getPath());
        // componentRegister.getRegister().add(opprettKomponentFraGUI());
        // componentRegister.addComponent(createComponentFromGUI());


        //todo sjekk om dette faktisk sletter filen at runtime??
        //deletefile("FileDirectory/Components/ComponentList.jobj");

        //kan gjøres mer åpen/generalisert, denne saveFileJobj funksjonen, sånn at man bare kan legge på extension i
        // egen metode.. Denne er nå bare åpen for jobj ish
        FileHandling.saveFileJobj(componentRegister,
                Paths.get(userPreferences.getPathToUser()));

        System.out.println("FileDirectory/Components/" + "ComponentList" + ".jobj" + " was autosaved");
        //Filbehandling.saveFileJobj(componentRegister, Paths.get("FileDirectory/ConfigMain.jobj"));

                /*
                Komponent komponent = registerKomponent.RegistrerKomponent();
                componentRegister.addKomponent(komponent);
                System.out.println(komponent.toString());
                */
        System.out.println(componentRegister.toString());

    }

    @FXML
    void btnLogOut(ActionEvent event) throws IOException {
        App.setRoot("primary");
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
    private void productDescriptionEdited (TableColumn.CellEditEvent<Component, String> event) {
        try {
            event.getRowValue().setProductDescription(event.getNewValue());
        } catch (IllegalArgumentException e) {
            Dialog.showErrorDialog("Ugyldig tegn i beskrivelse: " + e.getMessage());
        }
        tblViewComponent.refresh();
    }

    @FXML
    private void productPriceEdited (TableColumn.CellEditEvent<Component, Double> event) {
        try {
            if(doubleStrConverter.wasSuccessful()){
                event.getRowValue().setProductPrice(event.getNewValue());}
        } catch (NumberFormatException e) {
            Dialog.showErrorDialog("Ugyldig pris: " + e.getMessage());
        }
        tblViewComponent.refresh();
    }

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

    //user-fane

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