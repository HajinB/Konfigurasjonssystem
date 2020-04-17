package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.programutvikling.user.UserPreferences;
import java.io.IOException;


public class EnduserController extends TabComponentsController {
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");

    @FXML
    private Label lblTotalpris;

    @FXML
    private ComboBox comboBoxHarddisk;

    @FXML
    void btnCashier(ActionEvent event){

    }

    @FXML
    public void initialize() throws IOException {
        initItemFiles();
        loadElementsFromFile();

        updateList();
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
