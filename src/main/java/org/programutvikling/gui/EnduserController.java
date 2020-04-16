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
    public void initialize() throws IOException {
        initItemFiles();
        //componentPath = userPreferences.getPathToUser();
        //Path userDirPath =
        //System.out.println(directoryPath.toString());
        //bare lag en metode som gjør alt dette!
        loadElementsFromFile();
        //Path componentPath = Paths.get(("FileDirectory/Components/ComponentList.jobj"));
        //sender ut gridpane for å få tak i nodes i en annen class.
        //System.out.println(componentRegister.toString());
        updateList();
    }

    public void initItemFiles(){
        //computerRegister.addComponent();

    }

    private void updateList() {
    }

    private void loadElementsFromFile() {
        FileHandling.openSelectedComputerTxtFiles(ContextModel.INSTANCE.getCleanObjectList(), userPreferences.getPathToUser());
    }


    @FXML
    void btnSaveComputer(ActionEvent event) {


        }

    }
