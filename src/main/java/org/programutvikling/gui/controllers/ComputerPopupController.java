package org.programutvikling.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.gui.customViews.CustomListViewCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.ModelEndUser;

import java.net.URL;
import java.util.ResourceBundle;

public class ComputerPopupController extends EnduserController implements Initializable {
    @FXML private Label lblComputerName;
    @FXML private ListView<Component> listContent;
    @FXML private Label lblComputerPrice;
    private Computer computer;
    EnduserController enduserController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellFactory();
    }

    //todo tror denne er helt lik den i computer listview - lag en generisk?
    private void setCellFactory() {
        listContent.setCellFactory(lv-> new CustomListViewCell());
    }

    public void initData(Computer c, Stage stage, EnduserController enduserController) {
        this.stage = stage;
        lblComputerName.setText(c.getProductName());
        lblComputerPrice.setText(lblComputerPriceFormat(c.getProductPrice()));
        listContent.setItems(c.getComponentRegister().getObservableRegister());
        this.enduserController=enduserController;
        this.computer = c;
    }

    @FXML
    public void btnAddComputer(ActionEvent event) {
        //todo evt spørre om de vil erstatte her.
        Alert alert = Dialog.getConfirmationAlert("Operasjon", "Legge til i handlekurv", "Vil du erstatte " +
                "komponentene i " +
                "handlekurven med komponentene fra den valgte datamaskinen?", "");
        alert.showAndWait();

        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            ModelEndUser.INSTANCE.getComputer().removeAll();
            ModelEndUser.INSTANCE.getComputer().getComponentRegister()
                    .getObservableRegister().addAll(computer.getComponentRegister().getRegister());
            enduserController.updateTotalPrice();
            stage.close();
        }
    }
    String lblComputerPriceFormat(double price) {
        if(price % 1 == 0) {
            return String.format("%.0f",price) + " kr";
        } else {
            return String.format("%.2f",price) + " kr";
        }
    }
}
