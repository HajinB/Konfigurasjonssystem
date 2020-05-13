package org.programutvikling.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.model.Model;
import org.programutvikling.model.ModelUserRegister;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SuperUserController implements Initializable {

    public Tab tabComponentRegister;
    public Tab tabUserRegister;
    public AnchorPane tabUsers;
    public AnchorPane tabComponents;
    public BorderPane topLevelPane;
    RegistryComponentLogic registryComponentLogic;

    Stage stage;

    @FXML
    private TabComponentsController tabComponentsController;

    @FXML
    private TabUsersController tabUsersController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabComponentsController.init(this);
        tabUsersController.init(this);
        registryComponentLogic = new RegistryComponentLogic(tabComponentsController);
    }

    @FXML
    void btnOpenComputerAndAddComponents(ActionEvent e) {
        registryComponentLogic.dialogForComputerTxtOpen();
    }

    @FXML
    void btnSaveToChosenPath(ActionEvent a) throws IOException {
        String chosenPath = FileUtility.getFilePathFromSaveJOBJDialog(this.stage);
        if (chosenPath != null) {
            FileHandling.saveFileAs(chosenPath);
            //Model.INSTANCE.getSavedPathRegister().addPathToListOfSavedFilePaths(chosenPath);
            //tabComponentsController.updateRecentFiles();
        } else {
            return;
        }
        tabComponentsController.setResultLabelTimed("Lagring ferdig!");
    }

    @FXML
    void btnLogOut(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void btnOpenJobj(ActionEvent actionEvent) throws IOException {
        //open filefromchooser lagrer componentregister i index 0 og savedpath i index 2
        String chosenFile = FileUtility.getStringPathFromOpenJobjDialog(stage);
        if (chosenFile == null) {
            return;
        }
        if (getComponentRegister().getRegister().size() == 0) {
            registryComponentLogic.overWriteList(chosenFile);
        } else {

            Alert alert = Dialog.getOpenOption(
                    "Åpne fil",
                    "Legg til i listen eller overskriv. Under «Verktøy» kan du fjerne eventuelle duplikater",
                    "Vil du åpne ",
                    chosenFile + "?");
            alert.showAndWait();
            registryComponentLogic.handleOpenOptions(chosenFile, alert);
        }
    }

    private ComponentRegister getComponentRegister() {
        return Model.INSTANCE.getComponentRegister();
    }

    public void btnRemoveAllComponents(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Operasjon", "Tømme komponentregisteret",
                "Vil du slette alle komponenter i registeret?", "");
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            Model.INSTANCE.getComponentRegister().getRegister().clear();
            tabComponentsController.updateView();
        }
    }

    public void btnRemoveAllUsers(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Operasjon", "Tømme brukerregisteret",
                "Vil du slette alle admin og brukere i registeret?", "");
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            ModelUserRegister.INSTANCE.getUserRegister().getRegister().clear();
            tabUsersController.updateView();
            Dialog.showInformationDialog("Hvis brukerregisteret er tomt ved utlogging,\n" +
                    "vil standardbrukere gjenopprettes.");
        }
    }
}
