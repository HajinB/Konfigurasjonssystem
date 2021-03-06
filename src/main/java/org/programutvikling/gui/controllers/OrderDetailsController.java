package org.programutvikling.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.io.FileSaverTxt;
import org.programutvikling.domain.utility.ComputerFactory;
import org.programutvikling.gui.customViews.CustomTextWrapCellFactory;
import org.programutvikling.gui.customViews.PriceFormatCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.ModelEndUser;

import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class OrderDetailsController implements Initializable {

    Computer finishedComputer;
    Stage stage;
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
        if (txtNameForComputer.getText().isEmpty()) {
            Dialog.showErrorDialog("Gi datamaskinen et navn for å lagre den");
            return;
        }
        Alert alert = Dialog.getConfirmationAlert("Operasjon", "Lagre datamaskin",
                "Vil du tømme handlekurven etter datamaskinen blir lagret?", "");
        alert.showAndWait();

        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            try {
                saveComputer(fileSaverTxt);
            } catch (InvalidPathException | NoSuchFileException exception) {
                Dialog.showErrorDialog("Feil tegn i PC-navnet, prøv igjen!");
                return;
            }
            ModelEndUser.INSTANCE.getComputer().removeAll();
            stage.close();
        }
        if (alert.getResult() == alert.getButtonTypes().get(1)) {
            try {
                saveComputer(fileSaverTxt);
            } catch (InvalidPathException | NoSuchFileException invalidPathException){
                Dialog.showErrorDialog("Feil tegn i PC-navnet, prøv igjen!");
                return;
            }
            stage.close();
        }
    }

    private void saveComputer(FileSaverTxt fileSaverTxt) throws IOException {
        ComputerFactory computerFactory = new ComputerFactory();
        Computer computerWithName = computerFactory.computerFactory(finishedComputer.getComponentRegister(),
                txtNameForComputer.getText());
        try {
            fileSaverTxt.save(computerWithName,
                    Paths.get("AppFiles/Database/User/Computers/" + computerWithName.getProductName() + ".txt"));
            Dialog.showSuccessDialog(computerWithName.getProductName() + " ble lagret i 'AppFiles/Database/User/Computers/'");

        } catch (IOException i) {
            Dialog.showErrorDialog("Noe galt skjedde under lagring av " + computerWithName.getProductName() + "\n prøv et" +
                    " nytt navn");
            throw new IOException(i.getMessage());
        }
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
        if (price % 1 == 0) {
            return String.format("%.0f", price);
        } else {
            return String.format("%.2f", price);
        }
    }
}
