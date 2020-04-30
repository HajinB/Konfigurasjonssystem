package org.programutvikling.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.UserPreferences;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {

    public Tab tabComponentRegister;
    public Tab tabUserRegister;
    public AnchorPane tabUsers;
    public AnchorPane tabComponents;
    public BorderPane topLevelPane;

    /**
 *  https://stackoverflow.com/questions/32407666/javafx8-fxml-controller-injection
    https://stackoverflow.com/questions/32407666/javafx8-fxml-controller-injection
    https://stackoverflow.com/questions/32849277/javafx-controller-injection-does-not-work
 */
    FileHandling fileHandling = new FileHandling();
    Stage stage;

    @FXML private TabComponentsController tabComponentsController;

    @FXML
    void btnSaveToChosenPath(ActionEvent a) throws IOException {
        String chosenPath = FileUtility.getFilePathFromSaveJOBJDialog(this.stage);
        if(chosenPath != null) {
                FileHandling.saveFileAs(chosenPath);
        }
        tabComponentsController.setResultLabelTimed("Lagring ferdig!");
    }

    @FXML
    void btnLogOut(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    public void btnOpenJobj(ActionEvent actionEvent) throws IOException {
        //open filefromchooser lagrer componentregister i index 0 og savedpath i index 2
        String chosenFile = FileUtility.getStringPathFromOpenJobjDialog(stage);
        if (chosenFile == null) {
            return;
        }
        if(getComponentRegister().getRegister().size()==0){
            tabComponentsController.overwriteList(chosenFile);
        }

        Alert alert = Dialog.getOpenOption(
                "Åpne fil",
                "Legg til i listen eller overskriv. Under «Verktøy» kan du fjerne eventuelle duplikater",
                "Vil du åpne ",
                chosenFile + "?");
        alert.showAndWait();
        tabComponentsController.handleOpenOptions(chosenFile, alert);

    }

    private ComponentRegister getComponentRegister() {
        return ContextModel.INSTANCE.getComponentRegister();
    }

    @FXML
    void btnSetDirectory(ActionEvent event) {
        fileHandling.getUserPreferences().setPreference(stage);
        System.out.println("Ny directory path: " + fileHandling.getUserPreferences().getStringPathToUser());
    }

    public void btnRemoveDuplicates(ActionEvent event) throws IOException {
        ObservableList<Component> list  = (ObservableList<Component>) ContextModel.INSTANCE.getComponentRegister().getRegister();
        ContextModel.INSTANCE.getComponentRegister().removeDuplicates();
        tabComponentsController.updateView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabComponentsController.init(this);
    }
}
