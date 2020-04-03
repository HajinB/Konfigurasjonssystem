package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.bruker.UserPreferences;
import org.programutvikling.komponent.Komponent;
import org.programutvikling.komponent.KomponentRegister;

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
    Filbehandling filbehandling;
    RegistrerKomponent registrerKomponent;
    UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
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
    private GridPane gridPaneSuperbruker;

    private KomponentRegister komponentRegister = new KomponentRegister();

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
        //Komponent komponent = new Komponent("cpu", "ffsaddfs", "asffsa", 299.00);
        //komponentRegister.addKomponent(komponent);
        //System.out.println(directoryPath.toString());
            //bare lag en metode som gjør alt dette!


        //Path componentPath = Paths.get(("FileDirectory/Components/ComponentList.jobj"));
        //sender ut gridpane for å få tak i nodes i en annen class.
        registrerKomponent = new RegistrerKomponent(gridPaneSuperbruker);
        updateList();
        System.out.println(komponentRegister.toString());

        //test prints
        File file = new File(String.valueOf(userPreferences.getPathToUser()));
        String path = file.getAbsolutePath();
        System.out.println(file.isFile());


        //åpne jobj (som forhåpentligvis har lagret seg) ved init.

                                              //her må det egentlig stå componentpath - når
                // userPreferences.getPathToUser(); fungerer
               // Filbehandling.loadAppConfigurationFile(komponentRegister, "FileDirectory/Components/ComponentList" +
              //  ".jobj");

        Filbehandling.loadAppConfigurationFile(komponentRegister, userPreferences.getPathToUser());


    }

        //todo delete filen componentlist etter den er loada inn i initialize?
        // slik at man lager en ny hver gang? auto save..

        //bør man delete den rett før man kjører savejobj? er det det som er greia?

        //todo det som skjer er at den prøver å appende på filen som er der, slik at den ikke er riktig
        // format, altså den appender binary filene til en arraylist til en eksisterende fil  - da finner
        // ikke openjobj filen..

        //http://javafxportal.blogspot.com/2012/03/java-deleting-file-or-directory.html

        //    System.out.println(komponentRegister.getRegister().get(1));

                /*filbehandling.loadJobjFromDirectory(stage, komponentRegister, Paths.get("FileDirectory/ConfigMain" +
                        ".jobj"));*/

    @FXML
    void btnOpenJob(ActionEvent event) {
        Filbehandling.openFile(komponentRegister, "FileDirectory/Components/ComponentList.jobj");
    }


    @FXML
    void btnFjern(ActionEvent event) {
        //fjern fra directory og array ?
    }


    private Komponent opprettKomponentFraGUI() {
        Komponent komponent = new Komponent(inputVaretype.getText(),
                inputVarenavn.getText(),
                inputBeskrivelse.getText(),
                doubleStrConverter.stringTilDouble(inputPris.getText()));
        return komponent;
    }

    @FXML
    void btnFraFil(ActionEvent event) {

        Komponent komponent = new Komponent("2", "ffsaddfs", "asffsa", 299.00);
    }

    private void updateList() {

        komponentRegister.attachTableView(tblView);
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
        // komponentRegister.getRegister().add(opprettKomponentFraGUI());
        komponentRegister.addKomponent(opprettKomponentFraGUI());


        //todo sjekk om dette faktisk sletter filen at runtime??
        //deletefile("FileDirectory/Components/ComponentList.jobj");


        //kan gjøres mer åpen/generalisert, denne saveFileJobj funksjonen, sånn at man bare kan legge på extension i
        // egen metode.. Denne er nå bare åpen for jobj ish
        Filbehandling.saveFileJobj(komponentRegister,
                Paths.get("FileDirectory/Components/ComponentList.jobj"));


        System.out.println("FileDirectory/Components/" + "ComponentList" + ".jobj" + " was autosaved");
        //Filbehandling.saveFileJobj(komponentRegister, Paths.get("FileDirectory/ConfigMain.jobj"));

                /*
                Komponent komponent = registerKomponent.RegistrerKomponent();
                komponentRegister.addKomponent(komponent);
                System.out.println(komponent.toString());
                */
        System.out.println(komponentRegister.toString());

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