package org.programutvikling.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.programutvikling.App;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerRegister;
import org.programutvikling.domain.computer.ComputerValidator;
import org.programutvikling.gui.customViews.PriceFormatCell;
import org.programutvikling.gui.logic.EndUserLogic;
import org.programutvikling.gui.logic.EndUserService;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FXMLGetter;
import org.programutvikling.gui.utility.RegisterUtility;
import org.programutvikling.model.Model;
import org.programutvikling.model.ModelEndUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.application.Platform.runLater;


public class EnduserController {
    @FXML
    public BorderPane topLevelPaneEndUser;
    @FXML
    TableView<Computer> tblCompletedComputers;
    @FXML
    TableColumn computerPriceCln;
    EndUserService endUserService = new EndUserService();
    Stage stage;
    ComputerValidator computerValidator = new ComputerValidator();
    ArrayList<TableView<Component>> tblViewList = new ArrayList<>();
    ArrayList<TableColumn> tblColumnPriceList = new ArrayList<>();
    ArrayList<TableColumn> tblColumnDescriptionList = new ArrayList<>();
    @FXML
    private Label lblTotalPrice;
    @FXML
    private ListView<Component> shoppingListView;
    @FXML
    private TableView<Component>
            tblProcessor, tblVideoCard, tblScreen,
            tblOther, tblMemory, tblMouse, tblMotherBoard, tblCabinet, tblHardDisc, tblKeyboard;
    @FXML
    private TableColumn processorDescriptionColumn, memoryDescriptionColumn, cabinetDescriptionColumn, mouseDescriptionColumn,
            otherDescriptionColumn, graphicDescriptionColumn, motherboardDescriptionColumn, harddiskDescriptionColumn,
            keyboardDescriptionColumn, screenDescriptionColumn,
            processorPriceCln, videoCardPriceCln, screenPriceCln, otherPriceCln, memoryPriceCln,
            mousePriceCln, motherBoardPriceCln, cabinetPriceCln, hardDiscPriceCln, keyboardPriceCln;
    private EndUserLogic endUserLogic;


    @FXML
    public void initialize() {
        Model.INSTANCE.setEndUserLoggedIn(true);
        ModelEndUser.INSTANCE.loadCompleteComputers();
        addTableViewsToList();
        endUserLogic = new EndUserLogic(this, topLevelPaneEndUser, tblViewList, tblColumnDescriptionList,
                tblColumnPriceList, shoppingListView, tblCompletedComputers);
        updateComponentViews();
        updateList();
        endUserService.updateEndUserRegisters();
        updateComputerListView();
        setTooltipForCompletedComputers();
    }


    private void setTooltipForCompletedComputers() {
        final Tooltip tooltipCompletedComputers = new Tooltip("Dobbeltklikk på en datamaskin for å se detaljer");
        tblCompletedComputers.setTooltip(tooltipCompletedComputers);
    }

    @FXML
    public void btnOpenComputer(ActionEvent event) throws IOException {
        FileHandling.openCartFromSelectedFile(getComputer(), this.stage);
        updateTotalPrice();
    }

    @FXML
    public void btnSavePC(ActionEvent event) throws IOException {
        List<String> whatsMissing = computerValidator.listOfMissingComponentTypes(getComputer());
        if (FileHandling.validateCartListToSave(whatsMissing, stage)) return;
        updateCompletedComputers();
    }

    @FXML
    public void btnDeleteFromCart(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "Trykk ja for å slette.", "Vil du slette ",
                shoppingListView.getSelectionModel().getSelectedItems().get(0).getProductName());
        alert.showAndWait();
        if (shoppingListView.getSelectionModel().isEmpty()) {
            return;
        }
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            Component selectedComp = shoppingListView.getSelectionModel().getSelectedItem();
            deleteComponent(selectedComp);
            FileHandling.saveAllAdminFiles();
        }
    }

    @FXML
    //denne metoden kan hvertfall kjøres fra enduserlogic
    public void btnAddToCart(ActionEvent event) {
        boolean isSomethingSelected = false;

        for (TableView<Component> t : tblViewList) {
            if (!t.getSelectionModel().isEmpty()) {
                addComponentToCart(t);
                isSomethingSelected = true;
            }
        }
        if (!isSomethingSelected) {
            System.out.println("Velg en rad for å legge til i handlekurven");
        }
        clearSelection();
    }

    private void addTableViewsToList() {
        List<TableView<Component>> componentsViews = Arrays.asList(tblProcessor, tblVideoCard, tblScreen,
                tblOther, tblMemory, tblMouse, tblMotherBoard, tblCabinet, tblHardDisc, tblKeyboard);
        tblViewList.addAll(componentsViews);

        List<TableColumn> priceColumns = Arrays.asList(processorPriceCln, videoCardPriceCln, screenPriceCln, otherPriceCln, memoryPriceCln,
                mousePriceCln, motherBoardPriceCln, cabinetPriceCln, hardDiscPriceCln, keyboardPriceCln, computerPriceCln);
        tblColumnPriceList.addAll(priceColumns);

        List<TableColumn> descriptionColumns = Arrays.asList(processorDescriptionColumn, memoryDescriptionColumn,
                cabinetDescriptionColumn, mouseDescriptionColumn,
                otherDescriptionColumn, graphicDescriptionColumn, motherboardDescriptionColumn, harddiskDescriptionColumn,
                keyboardDescriptionColumn, screenDescriptionColumn);
        tblColumnDescriptionList.addAll(descriptionColumns);
    }

    private void updateCompletedComputers() {
        if (ModelEndUser.INSTANCE.getComputerRegister().getObservableRegister().size() > 0)
            tblCompletedComputers.setItems(ModelEndUser.INSTANCE.getComputerRegister().getObservableRegister());
    }

    private void updateList() {
        updateTotalPrice();
        tblCompletedComputers.setItems(ModelEndUser.INSTANCE.getComputerRegister().getObservableRegister());
        setTblProcessor(tblProcessor);
        setTblVideoCard(tblVideoCard);
        setTblScreen(tblScreen);
        setTblOther(tblOther);
        setTblMemory(tblMemory);
        setTblCabinet(tblCabinet);
        setTblMotherBoard(tblMotherBoard);
        setTblMouse(tblMouse);
        setTblHardDisc(tblHardDisc);
        setTblKeyboard(tblKeyboard);
    }

    @FXML
    void btnLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void btnCashier(ActionEvent event) throws IOException {
        List<String> whatsMissing = computerValidator.listOfMissingComponentTypes(getComputer());
        if (FileHandling.showDialogIfComponentsAreMissing(whatsMissing, stage)) return;
        openFinalDetails();
    }

    private void openFinalDetails() throws IOException {
        System.out.println("her er vi i openDetailedView");
        FXMLLoader loader = FXMLGetter.fxmlLoaderFactory("orderDetailsPopup.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        OrderDetailsController orderDetailsController =
                loader.<OrderDetailsController>getController();
        orderDetailsController.initData(getComputer(), stage);
        stage.show();
    }

    Computer getComputer() {
        return ModelEndUser.INSTANCE.getComputer();
    }

    private void addComponentToCart(TableView<Component> tbl) {
        Component selectedComp = tbl.getSelectionModel().getSelectedItem();
        if (selectedComp != null) {
            endUserLogic.addComponentToComputer(selectedComp);
            updateComputerListView();
        }
    }

    void updateComputerListView() {
        if (getComputer() != null)
            shoppingListView.setItems(getComputer().getComponentRegister().getObservableRegister());
        else {
            System.out.println(getComputer() + "computer er tom");
        }
        updateTotalPrice();
    }

    private void updateComponentViews() {
        Callback<TableColumn, TableCell> priceCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new PriceFormatCell();
                    }
                };
        computerPriceCln.setCellFactory((priceCellFactory));
        endUserService.updateEndUserRegisters();
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        if (getComputer() != null) {
            double doubleTotalpris = getComputer().calculatePrice();
            if (doubleTotalpris % 1 == 0) {
                String totalpris = String.format("%.0f", getComputer().calculatePrice()) + " kr";
                lblTotalPrice.setText(totalpris);
            } else {
                String totalpris = String.format("%.2f", getComputer().calculatePrice()) + " kr";
                lblTotalPrice.setText(totalpris);
            }
        }
    }
    /**
     * går via endUserService for å hente lister som er filtrert på produkttype
     */
    private void setTblMemory(TableView<Component> tblMemory) {
        tblMemory.setItems(endUserService.getMemoryRegister().getObservableRegister());
        this.tblMemory = tblMemory;
    }

    private void setTblVideoCard(TableView<Component> tblVideoCard) {
        tblVideoCard.setItems(endUserService.getVideoCardRegister().getObservableRegister());
        this.tblVideoCard = tblVideoCard;
    }

    public void setTblProcessor(TableView<Component> tblProcessor) {
        tblProcessor.setItems(endUserService.getProcessorRegister().getObservableRegister());
        this.tblProcessor = tblProcessor;
    }

    public void setTblHardDisc(TableView<Component> tblHardDisc) {
        this.tblHardDisc = tblHardDisc;
        tblHardDisc.setItems(endUserService.getHardDiscRegister().getObservableRegister());
    }

    public void setTblKeyboard(TableView<Component> tblKeyboard) {
        tblKeyboard.setItems(endUserService.getKeyboardRegister().getObservableRegister());
        this.tblKeyboard = tblKeyboard;
    }

    public void setTblMouse(TableView<Component> tblMouse) {
        this.tblMouse = tblMouse;
        tblMouse.setItems(endUserService.getMouseRegister().getObservableRegister());
    }

    private void setTblMotherBoard(TableView<Component> tblMotherBoard) {
        this.tblMotherBoard = tblMotherBoard;
        tblMotherBoard.setItems(endUserService.getMotherboardRegister().getObservableRegister());
    }

    private void setTblCabinet(TableView<Component> tblCabinet) {
        this.tblCabinet = tblCabinet;
        tblCabinet.setItems(endUserService.getCabinetRegister().getObservableRegister());
    }

    private void setTblOther(TableView<Component> tblOther) {
        this.tblOther = tblCabinet;
        tblOther.setItems(endUserService.getOtherRegister().getObservableRegister());
    }

    private void setTblScreen(TableView<Component> tblScreen) {
        this.tblScreen = tblScreen;
        tblScreen.setItems(endUserService.getScreenRegister().getObservableRegister());
    }


    private void deleteComponent(Component selectedComp) {
        getComputer().getComponentRegister().getRegister().remove(selectedComp);
        updateComputerListView();
    }

    //Fjerner forrige valgte produkt etter at du har trykket #Legg i handlekurv
    public void clearSelection() {
        for (TableView t : tblViewList) {
            runLater(() -> {
                t.getSelectionModel().clearSelection();
            });
        }
    }


}
