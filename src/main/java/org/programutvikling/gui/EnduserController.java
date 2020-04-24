package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.FileOpenerTxt;
import org.programutvikling.component.io.FileSaverTxt;
import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerValidator;
import org.programutvikling.gui.utility.EndUserService;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.UserPreferences;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


public class EnduserController extends TabComponentsController {
    EndUserService endUserService = new EndUserService();
    Stage stage;
    ComputerValidator computerValidator = new ComputerValidator();
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    //sånn instansiering fungerer ikke likevel..blir statisk - bør lage en klasse som henter fresh data ut fra
    // contextmodel
    private ComponentRegister componentRegister = ContextModel.INSTANCE.getComponentRegister();
    @FXML
    private ListView<Component> shoppingListView;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private TableView<Component> tblProsessor;

    @FXML
    private TableView<Component> tblSkjermkort;

    @FXML
    private TableView<Component> tblMinne;

    @FXML
    private TableView<Component> tblHarddisk;

    @FXML
    private TableView<Component> tblSSD;

    @FXML
    private TableView<Component> tblTastatur;

    @FXML
    private TableView<Component> tblMus;

    @FXML
    private TableView<Component> tblSkjerm;

    @FXML
    private TableView<Component> tblAnnet;

    @FXML
    void btnLogout(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void btnCashier(ActionEvent event) {

    }

    Computer getComputer() {
        return ContextModel.INSTANCE.getComputer();
    }

    @FXML
    void btnAddProsessorToCart(ActionEvent event) {
        if (computerValidator.prosessorValidator(getComputer())) {
            addComponentToCart(tblProsessor);
        } else {
            replaceComponentInCart("prosessor", tblProsessor);
        }
    }

    @FXML
    void btnAddHarddiskToCart(ActionEvent event) {
        if (computerValidator.harddiskValidator(getComputer())) {
            addComponentToCart(tblHarddisk);
        } else {
            replaceComponentInCart("harddisk", tblHarddisk);
        }
    }

    @FXML
    void
    btnAddMinneToCart(ActionEvent event) {
        if (computerValidator.minneValidator(getComputer())) {
            addComponentToCart(tblMinne);
        } else {
            replaceComponentInCart("minne", tblMinne);
        }

    }

    @FXML
    void btnAddSkjermToCart(ActionEvent e) {
        if (computerValidator.skjermValidator(getComputer())) {
            addComponentToCart(tblSkjerm);
        } else {
            replaceComponentInCart("skjerm", tblSkjerm);
        }

    }

    @FXML
    void btnAddAnnetToCart(ActionEvent event) {
        if (computerValidator.annetValidator(getComputer()))
            addComponentToCart(tblAnnet);
        else {
            replaceComponentInCart("annet", tblAnnet);
        }
    }

    @FXML
    void btnAddTastaturToCart(ActionEvent event) {
        if(computerValidator.tastaturValidator(getComputer()))
        addComponentToCart(tblTastatur);
        else{
            replaceComponentInCart("tastatur", tblAnnet);
        }
    }

    @FXML
    void
    btnAddMusToCart(ActionEvent event) {
        if(computerValidator.musValidator(getComputer()))
        addComponentToCart(tblMus);
        else{
            replaceComponentInCart("mus", tblMus);
        }
    }

    @FXML
    void btnAddSkjermkortToCart(ActionEvent event) {
        if(computerValidator.skjermkortValidator(getComputer()))
        addComponentToCart(tblSkjermkort);
        else{
            replaceComponentInCart("skjermkort", tblSkjermkort);
        }
    }

    @FXML
    public void btnOpenComputer(ActionEvent event) throws IOException {
        String path = FileUtility.getFilePathFromOpenDialog(stage);
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        fileOpenerTxt.open(ContextModel.INSTANCE.getComputer(), Paths.get(path));
    }

    @FXML
    public void btnSavePC(ActionEvent event) throws IOException {
        FileSaverTxt fileSaverTxt = new FileSaverTxt();
        String path = FileUtility.getFilePathFromSaveTXTDialog(stage);
        fileSaverTxt.save(ContextModel.INSTANCE.getComputer(), Paths.get(path));
    }

    @FXML
    public void initialize() throws IOException {
        endUserService.updateEndUserRegisters();
        System.out.println(endUserService.getHarddiskRegister().toString());
        updateComponentViews();
        initItemFiles();
        loadElementsFromFile();
        updateList();
        updateComputerListView();
    }

    //By default, a ListView calls the toString()
    // method of its items and it displays the string in its cell.
    // In the updateItem() method of your custom ListCell, you can populate the text and graphic for the cell to
    // display anything you want in the cell based on the item in that cell.
    //https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
    void updateComputerListView() {
        if (ContextModel.INSTANCE.getComputer() != null)
            shoppingListView.setItems(ContextModel.INSTANCE.getComputer().getComponentRegister().getObservableRegister());
            updateTotalPrice();
    }

    private void updateComponentViews() {
        endUserService.updateEndUserRegisters();
        ComponentRegister harddiskRegister = new ComponentRegister();
        harddiskRegister.getRegister().addAll(endUserService.getHarddiskRegister().getRegister());
        System.out.println(harddiskRegister.toString());
        //System.out.println(endUserService.getHarddiskRegister().toString());
        //endUserService.getHarddiskRegister().attachTableView(tblViewHarddisk);
        //tblViewHarddisk.setItems(endUserService.getHarddiskRegister().getObservableRegister());
        //hvorfor NPE her?
        //tblViewComponent.refresh();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        String totalpris = ContextModel.INSTANCE.getComputer().calculatePrice() + ",-";
        lblTotalPrice.setText(totalpris);
    }

    void addComponentToComputer() {

    }

    private void updateList() {
        updateTotalPrice();
        setTblProsessor(tblProsessor);
        setTblHarddisk(tblHarddisk);
        setTblSkjermkort(tblSkjermkort);
        setTblMinne(tblMinne);
        setTblAnnet(tblAnnet);
        setTblTastatur(tblTastatur);
        //fortsett for alle her

    }

    /**
     * går via endUserService for å hente lister som er filtrert på produkttype
     * mangler kabinett tableview og hovedkort tableview
     */
    private void setTblMinne(TableView<Component> tblMinne) {
        tblMinne.setItems(endUserService.getMinneRegister().getObservableRegister());
        this.tblMinne = tblMinne;
    }

    private void setTblSkjermkort(TableView<Component> tblSkjermkort) {
        tblSkjermkort.setItems(endUserService.getSkjermkortRegister().getObservableRegister());
        this.tblSkjermkort = tblSkjermkort;
    }

    public void setTblProsessor(TableView<Component> tblProsessor) {
        tblProsessor.setItems(endUserService.getProsessorRegister().getObservableRegister());
        this.tblProsessor = tblProsessor;
    }

    public void setTblHarddisk(TableView<Component> tblHarddisk) {
        tblHarddisk.setItems(endUserService.getHarddiskRegister().getObservableRegister());
        this.tblHarddisk = tblHarddisk;
    }

    public void setTblTastatur(TableView<Component> tblTastatur) {
        tblTastatur.setItems(endUserService.getTastaturRegister().getObservableRegister());
        this.tblTastatur = tblTastatur;
    }

    public void setTblAnnet(TableView<Component> tblAnnet) {
        tblAnnet.setItems(endUserService.getAnnetRegister().getObservableRegister());
        this.tblAnnet = tblAnnet;
    }

    public void initItemFiles() {
        //computerRegister.addComponent();
    }

    private void loadElementsFromFile() {
        FileHandling.openSelectedComputerTxtFiles(ContextModel.INSTANCE.getCleanObjectList(), userPreferences.getStringPathToUser());
    }


    private void addComponentToCart(TableView<Component> tbl) {
        Component selectedComp = tbl.getSelectionModel().getSelectedItem();
        /**all adding av componenter må skje via enduserservice(?) - legg til en metode der som legger til*/
        if (selectedComp != null) {
            ContextModel.INSTANCE.getComputer().addComponent(selectedComp);
            updateComputerListView();
        }
        updateComputerListView();
    }

    private void replaceComponentInCart(String s, TableView<Component> tblProsessor) {
        Component selectedComp = tblProsessor.getSelectionModel().getSelectedItem();
        if (selectedComp != null) {
            /**FJERN EN AV DE AV DEN GITTE TYPEN - så legg til*/
            /**evt legg til confirmation alert her*/
            List<Component> list = getComputer().getComponentRegister().filterByProductType(s);
            if (list.size() > 0) {
                getComputer().getComponentRegister().getRegister().remove(list.get(0));
            }
            ContextModel.INSTANCE.getComputer().addComponent(selectedComp);
            updateComputerListView();
        }
    }


    private ComponentRegister getComputerComponentRegister() {
        return ContextModel.INSTANCE.getComputer().getComponentRegister();
    }


}
