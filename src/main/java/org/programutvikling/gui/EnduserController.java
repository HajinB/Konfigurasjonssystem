package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.FileSaverTxt;
import org.programutvikling.computer.Computer;
import org.programutvikling.gui.utility.EndUserService;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.UserPreferences;

import java.io.IOException;
import java.nio.file.Paths;


public class EnduserController extends TabComponentsController {
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    EndUserService endUserService = new EndUserService();
    Stage stage;

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
    void btnLogout (ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void btnCashier(ActionEvent event){

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

    void updateComputerListView(){

        if(ContextModel.INSTANCE.getComputer()!=null)
            shoppingListView.setItems(ContextModel.INSTANCE.getComputer().getComponentRegister().getObservableRegister());

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
    }

    void addComponentToComputer(){

    }
    private void updateList() {
        setTblProsessor(tblProsessor);
        setTblHarddisk(tblHarddisk);
        setTblSkjermkort(tblSkjermkort);
        setTblMinne(tblMinne);

    }
    /**går via endUserService for å hente lister som er filtrert på produkttype*/
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

    public void initItemFiles() {
        //computerRegister.addComponent();

    }

    private void loadElementsFromFile() {
        FileHandling.openSelectedComputerTxtFiles(ContextModel.INSTANCE.getCleanObjectList(), userPreferences.getStringPathToUser());
    }


    @FXML
    void btnAddProsessorToCart(ActionEvent event) {
       // tblProsessor.getSelectionModel().getSelectedCells();
        /**all adding av componenter må skje via enduserservice - legg til en metode der som legger til*/
        Component selectedComp = tblProsessor.getSelectionModel().getSelectedItem();
        ContextModel.INSTANCE.getComputer().addComponent(selectedComp);
        updateComputerListView();
    }

    @FXML
    void btnAddSkjermkortToCart(ActionEvent event) {
        Component selectedComp = tblSkjermkort.getSelectionModel().getSelectedItem();
        /**all adding av componenter må skje via enduserservice(?) - legg til en metode der som legger til*/
        ContextModel.INSTANCE.getComputer().addComponent(selectedComp);
        if (selectedComp != null) {
            getComputerComponentRegister().addComponent(selectedComp);
            updateComputerListView();
        }
        updateComputerListView();
    }

    private ComponentRegister getComputerComponentRegister() {
        //dette gir NPE fordi computer ikke er instansiert i contextmodel(?)
        return ContextModel.INSTANCE.getComputer().getComponentRegister();
    }


    @FXML
    void btnSaveComputer(ActionEvent event) throws IOException {
        FileSaverTxt fileSaverTxt = new FileSaverTxt();
        String path = FileUtility.getFilePathFromSaveDialog(stage);
        fileSaverTxt.save(ContextModel.INSTANCE.getComputer(), Paths.get(path));
        }

    public void btnSavePC(ActionEvent event) {

    }
}
