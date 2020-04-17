package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.user.UserPreferences;

import java.io.IOException;



public class EnduserController extends TabComponentsController {
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");

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
    void btnCashier(ActionEvent event){

    }

    @FXML
    public void initialize() throws IOException {
        initItemFiles();
        loadElementsFromFile();

        updateList();
    }


    public void setTblProsessor(TableView<Component> tblProsessor) {
            tblProsessor.setItems(componentRegister.getObservableRegister());
            this.tblProsessor = tblProsessor;

    }

    public void initItemFiles() {
        //computerRegister.addComponent();

        }


    private void updateList() {
        setTblProsessor(tblProsessor);

    }

    private void loadElementsFromFile() {
        FileHandling.openSelectedComputerTxtFiles(ContextModel.INSTANCE.getCleanObjectList(), userPreferences.getStringPathToUser());
    }


    @FXML
    void btnSaveComputer(ActionEvent event) {


    }

}
