package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerFactory;
import org.programutvikling.gui.CustomViews.CustomTextWrapCellFactory;
import org.programutvikling.gui.CustomViews.PriceFormatCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.ModelEndUser;
import org.programutvikling.domain.io.FileSaverTxt;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    @FXML
    private TableView<Component> tblFinalViewComponent;

    @FXML
    private TableColumn productFinalDescriptionColumn;

    @FXML
    private TableColumn productFinalPriceColumn;

    @FXML
    private Button btnAddToComputersFolder;

    @FXML
    private Label lblTotalPriceDetails;

    @FXML
    private TextField txtNameForComputer;

    Computer finishedComputer;
    Stage stage;


    void initData(Computer c, Stage stage) {
        //tar inn stage for å kunne lukke når brukeren trykker endre
        this.stage = stage;
        this.finishedComputer = c;
        tblFinalViewComponent.setItems(c.getComponentRegister().getObservableRegister());
        setCellFactories();
        btnAddToComputersFolder.setDefaultButton(true);
        lblTotalPriceDetails.setText(computerPriceFormat(c.getProductPrice()));
    }

    @FXML
    void btnAddToComputersFolder(ActionEvent event) throws IOException {
        FileSaverTxt fileSaverTxt = new FileSaverTxt();
        if(txtNameForComputer.getText().isEmpty()){
            Dialog.showErrorDialog("Gi datamaskinen et navn for å lagre den");
            return;
        }

        ComputerFactory computerFactory = new ComputerFactory();
        Computer computerWithName = computerFactory.computerFactory(finishedComputer.getComponentRegister(),
                txtNameForComputer.getText());
        fileSaverTxt.save(computerWithName,
                Paths.get("AppFiles/Database/User/Computers/" + computerWithName.getProductName()+".txt"));
        Dialog.showSuccessDialog(computerWithName.getProductName() + " ble lagret i 'AppFiles/Database/User/Computers/'");
        //todo legg inn dialog for å spørre om å erstatte handlekurven?
        ModelEndUser.INSTANCE.getComputer().removeAll();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactories();
        btnAddToComputersFolder.setDefaultButton(true);
    }

    private void setCellFactories() {
        Callback<TableColumn, TableCell> priceCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new PriceFormatCell();
                    }
                };

        Callback<TableColumn, TableCell> customTextWrapCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new CustomTextWrapCellFactory();
                    }
                };

        productFinalDescriptionColumn.setCellFactory(customTextWrapCellFactory);

        productFinalPriceColumn.setCellFactory(priceCellFactory);
    }

    private String computerPriceFormat(Double price) {
        if(price % 1 == 0) {
            return String.format("%.0f",price);
        } else {
            return String.format("%.2f",price);
        }
    }
}
