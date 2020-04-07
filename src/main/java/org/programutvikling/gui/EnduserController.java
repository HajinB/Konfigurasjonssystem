package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.computer.ComputerRegister;
import org.programutvikling.user.UserPreferences;
import java.io.IOException;


public class EnduserController extends SecondaryController {
    private UserPreferences superUserPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");

    ComputerRegister computerRegister = new ComputerRegister();
    ComponentRegister componentRegister = new ComponentRegister();
    FileHandling fileHandling = new FileHandling();

    @FXML
    private Label lblTotalpris;

    @FXML
    private ComboBox comboBoxHarddisk;

    @FXML
    public void initialize() throws IOException {
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

    private void updateList() {
    }

    private void loadElementsFromFile() {
        fileHandling.openFile(componentRegister, userPreferences.getPathToUser());

    }


    @FXML
    void btnSaveComputer(ActionEvent event) {


        }

    }
