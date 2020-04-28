package org.programutvikling.gui;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.programutvikling.component.Component;
import org.programutvikling.computer.Computer;

import java.net.URL;
import java.util.ResourceBundle;

public class ComputerPopupController extends EnduserController implements Initializable {
    @FXML private Label lblComputerName;
    @FXML private ListView<Component> listContent;
    @FXML private Label lblComputerPrice;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listContent.setCellFactory(param -> new ListCell<Component>() {
            @Override
            protected void updateItem(Component c, boolean empty) {
                super.updateItem(c, empty);
                if (empty || c == null || c.getProductName() == null) {
                    setText("");
                } else {
                    setText(c.getProductName() + "\n" + String.format("%.2f", c.getProductPrice()) + ",-");
                    //Change listener implemented.
                    listContent.getSelectionModel().selectedItemProperty().addListener((ObservableValue<?
                            extends Component> observable, Component oldValue, Component newValue) -> {
                        if (listContent.isFocused()) {
                        }
                    });
                }

            }
        });

    }

    void initData(Computer c, Stage stage) {
        //tar inn stage for å kunne lukke når brukeren trykker endre
        this.stage = stage;

        //System.out.println(componentEditNode);
        lblComputerName.setText(c.getProductName());
        lblComputerPrice.setText(Double.toString(c.calculatePrice()));
        listContent.setItems(c.getComponentRegister().getObservableRegister());

    }
}
