package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.utility.EndUserService;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.user.UserPreferences;

import java.io.IOException;



public class EnduserController extends TabComponentsController {
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    EndUserService endUserService = new EndUserService();

    private ComponentRegister componentRegister = ContextModel.INSTANCE.getComponentRegister();

    @FXML
    private ListView<?> shoppingListView;

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
    private ListView<?> shoppingListView;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private TableView<Component> tblViewSkjermkort;

    @FXML
    private TableView<Component> tblViewHarddisk;

    @FXML
    void btnCashier(ActionEvent event){

    }

    @FXML
    public void initialize() throws IOException {
        endUserService.updateEndUserRegisters();
        System.out.println(endUserService.getHarddiskRegister().toString());
        updateComponentViews();
        initTblViews();
        initItemFiles();
        loadElementsFromFile();

        updateList();
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

    }
    public void setTblProsessor(TableView<Component> tblProsessor) {
        tblProsessor.setItems(componentRegister.getObservableRegister());
        this.tblProsessor = tblProsessor;

    }

    private void initTblViews() {

    }


    public void setTblProsessor(TableView<Component> tblProsessor) {
            tblProsessor.setItems(componentRegister.getObservableRegister());
            this.tblProsessor = tblProsessor;

    }

    public void initItemFiles() {
        //computerRegister.addComponent();

    }

    private void updateList() {
    }

    private void loadElementsFromFile() {
        FileHandling.openSelectedComputerTxtFiles(ContextModel.INSTANCE.getCleanObjectList(), userPreferences.getStringPathToUser());
    }




    @FXML
    void btnSaveComputer(ActionEvent event) {


        }

    }
