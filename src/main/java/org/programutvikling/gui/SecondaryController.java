package org.programutvikling.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.gui.utility.RegisterLogic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SecondaryController implements Initializable {

/**
 *  https://stackoverflow.com/questions/32407666/javafx8-fxml-controller-injection
    https://stackoverflow.com/questions/32407666/javafx8-fxml-controller-injection
    https://stackoverflow.com/questions/32849277/javafx-controller-injection-does-not-work
 */
    FileHandling fileHandling = new FileHandling();
    Stage stage;

    @FXML private TabComponentsController tabComponentsController;

    @FXML
    void btnSaveToChosenPath(ActionEvent e) throws IOException {
        String chosenPath = FileUtility.getFilePathFromSaveJOBJDialog(this.stage);

        FileHandling.saveFileAs(chosenPath);
    }

    @FXML
    void btnLogOut(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }
    @FXML
    public void btnOpenJobj(ActionEvent actionEvent) throws IOException {
        tabComponentsController.openFileFromChooserWithThreadSleep();
        ContextModel.INSTANCE.loadComponentRegisterIntoModel();
        tabComponentsController.updateView();
        fileHandling.saveAll();
    }

    @FXML
    void btnSetDirectory(ActionEvent event) {
        fileHandling.getUserPreferences().setPreference(stage);
        System.out.println("Ny directory path: " + fileHandling.getUserPreferences().getStringPathToUser());
    }

    public void btnRemoveDuplicates(ActionEvent event) throws IOException {
        ObservableList<Component> list  = (ObservableList<Component>) ContextModel.INSTANCE.getComponentRegister().getRegister();
       //ArrayList<Component> listWithoutDuplicates = RegisterLogic.
        //System.out.println(listWithoutDuplicates.toString());
        /*ComponentRegister componentRegister = new ComponentRegister();
        componentRegister.getRegister().addAll(listWithoutDuplicates);
*/
        ContextModel.INSTANCE.getComponentRegister().removeDuplicates();

        tabComponentsController.updateView();
       // tabComponentsController.lblComponentMsg.setText("button pressed");

        //update controller

        //det er egentlig ikke meninga å update den andre controlleren fra her - hva bør man gjøre??
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabComponentsController.init(this);

    }
}
