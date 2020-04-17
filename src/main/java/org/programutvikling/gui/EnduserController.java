package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.utility.EndUserService;
import org.programutvikling.user.UserPreferences;
import java.io.IOException;


public class EnduserController extends TabComponentsController {
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    EndUserService endUserService = new EndUserService();
    @FXML
    private Label lblTotalpris;

    @FXML
    private ComboBox comboBoxHarddisk;


    @FXML
    private ListView<?> shoppingListView;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private TableView<Component> tblViewSkjermkort;

    @FXML
    private TableView<Component> tblViewHarddisk;

    @FXML
    void btnCashier(ActionEvent event) {

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

    private void initTblViews() {

    }

    public void initItemFiles(){
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
