package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.user.UserPreferences;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

//todo: få til å loade alle files fra components folderen - ikke bare forhåndsvalgt fil

public class SecondaryController {
    private Stage stage;
    Preferences prefs;

    String componentPath;// = Paths.get(("FileDirectory/Components/ComponentList.jobj"));
    FileHandling fileHandling;
    private RegistryComponentLogic registryComponentLogic;
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");

    @FXML
    private MenuBar menyBar;
    @FXML
    private Label tblOverskrift;
    @FXML
    private TableView<?> tblView;
    @FXML
    private TableColumn<?, ?> kolonneType;
    @FXML
    private TableColumn<?, ?> kolonneVNavn;
    @FXML
    private TableColumn<?, ?> kolonneBesk;
    @FXML
    private TableColumn<?, ?> kolonnePris;
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

    private ComponentRegister componentRegister = new ComponentRegister();

    private Converter.DoubleStringConverter doubleStrConverter
            = new Converter.DoubleStringConverter();

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
    public void initialize() throws IOException {
        //componentPath = userPreferences.getPathToUser();
        //Path userDirPath =
        Component component = new Component("cpu", "ffsaddfs", "asffsa", 299.00);
        componentRegister.addComponent(component);
        //System.out.println(directoryPath.toString());
            //bare lag en metode som gjør alt dette!


        //Path componentPath = Paths.get(("FileDirectory/Components/ComponentList.jobj"));
        //sender ut gridpane for å få tak i nodes i en annen class.
        registryComponentLogic = new RegistryComponentLogic(gridPane);

        System.out.println(componentRegister.toString());

        //test prints
        File file = new File(String.valueOf(userPreferences.getPathToUser()));
        String path = file.getAbsolutePath();
        System.out.println(file.isFile());
        System.out.println(userPreferences.getPathToUser());

        updateList();
        //åpne jobj (som forhåpentligvis har lagret seg) ved init.

                                              //her må det egentlig stå componentpath - når
                // userPreferences.getPathToUser(); fungerer
               // Filbehandling.loadAppConfigurationFile(componentRegister, "FileDirectory/Components/ComponentList" +
              //  ".jobj");
        if(file.exists()){
        FileHandling.loadAppConfigurationFile(componentRegister, userPreferences.getPathToUser());
        componentRegister.log();
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


    @FXML
    void btnFjern(ActionEvent event) {
        //fjern fra directory og array ?
    }


    private Component createComponentFromGUI() {
        return new Component(inputVaretype.getText(),
                inputVarenavn.getText(),
                inputBeskrivelse.getText(),
                doubleStrConverter.stringTilDouble(inputPris.getText()));
    }

    @FXML
    void btnFraFil(ActionEvent event) {

        Component komponent = new Component("2", "ffsaddfs", "asffsa", 299.00);
    }

    private void updateList() {

        componentRegister.attachTableView(tblView);
    }

    private void loadFromDirectory() {


    }


    int componentname = 1;
    Path directoryPath = Paths.get("FileDirectory");


    @FXML
    void btnSetDirectory(ActionEvent event) {
        userPreferences.setPreference(stage);
    }



    @FXML
        //Komponent(String type, String name, String description, double price)
    void btnLeggTil(ActionEvent event) throws IOException {
        // Komponent komponent = registrerKomponent.opprettKomponentFraGUIFelt();

        //todo denne folderen/directory path bør kunne bli satt av brukeren i settings elns(?)
        //File folder = new File("FileDirectory/");

        //directoryPath = Paths.("FileDirectory");
        //directoryPath = new File(folder.getPath());
        // componentRegister.getRegister().add(opprettKomponentFraGUI());
        componentRegister.addComponent(createComponentFromGUI());


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
    void kolonneBeskEdit(ActionEvent event) {

    }

    @FXML
    void kolonnePrisEdit(ActionEvent event) {

    }

    @FXML
    void kolonneTypeEdit(ActionEvent event) {

    }

    @FXML
    void kolonneVNavnEdit(ActionEvent event) {

    }

    @FXML
    void kolonneVNrEdit(ActionEvent event) {

    }

}