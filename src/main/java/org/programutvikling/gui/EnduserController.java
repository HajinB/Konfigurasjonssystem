package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerValidator;
import org.programutvikling.gui.CustomViews.CustomListViewCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.EndUserService;
import org.programutvikling.gui.utility.FXMLGetter;
import org.programutvikling.logic.EndUserLogic;
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

    EndUserService endUserService = new EndUserService();
    Stage stage;
    ComputerValidator computerValidator = new ComputerValidator();

    ArrayList<TableView<Component>> tblViewList = new ArrayList<>();
    ArrayList<TableColumn> tblColumnPriceList = new ArrayList<>();
    ArrayList<TableColumn> tblColumnDescriptionList = new ArrayList<>();

    private EndUserLogic endUserLogic;

    //todo hver dag fra nå av : prøv å få ut all denne koden fra kontrolleren. alt man trenger er å definere
    // kolonnenen, send de til EndUserLogic, og gjør alt som trengs der!!!!

    @FXML
    public void initialize() throws IOException {
        addTableViewsToList();
        System.out.println(ModelEndUser.INSTANCE);
        //todo så og si alle metoder under her kan trekkes ut av controlleren
        endUserLogic = new EndUserLogic(this, topLevelPaneEndUser, tblViewList, tblColumnDescriptionList,
                tblColumnPriceList, shoppingListView);
        updateComponentViews();
        updateList();
        endUserService.updateEndUserRegisters();
        updateComputerListView();
        setTblCompletedComputersListener();
        final Tooltip tooltipCompletedComputers = new Tooltip("Dobbeltklikk på en datamaskin for å se detaljer");
        tblCompletedComputers.setTooltip(tooltipCompletedComputers);
    }

    private void addTableViewsToList() {
        List<TableView<Component>> componentsViews = Arrays.asList(tblProcessor, tblVideoCard, tblScreen,
                tblOther, tblMemory, tblMouse, tblMotherBoard, tblCabinet, tblHardDisc, tblKeyboard);
        tblViewList.addAll(componentsViews);

        List<TableColumn> priceColumns = Arrays.asList(processorPriceCln, videoCardPriceCln, screenPriceCln, otherPriceCln, memoryPriceCln,
                mousePriceCln, motherBoardPriceCln, cabinetPriceCln, hardDiscPriceCln, keyboardPriceCln);
        tblColumnPriceList.addAll(priceColumns);

        List<TableColumn> descriptionColumns = Arrays.asList(processorDescriptionColumn, memoryDescriptionColumn,
                cabinetDescriptionColumn, mouseDescriptionColumn,
                otherDescriptionColumn, graphicDescriptionColumn, motherboardDescriptionColumn, harddiskDescriptionColumn,
                keyboardDescriptionColumn, screenDescriptionColumn);
        tblColumnDescriptionList.addAll(descriptionColumns);
    }

    private void setTblCompletedComputersListener() {
        /**detecter tablerow, for å hente ut component*/
        //skal åpne en fxml, og sende cell-content til initmetoden til controlleren til denne fxmln
        tblCompletedComputers.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //setCellSelectionEnabled er om man kan velge en enkelt celle eller en hel rad.
                tblCompletedComputers.getSelectionModel().setCellSelectionEnabled(false);
                TableRow row;
                if (isDoubleClick(event)) {
                    Node node = ((Node) event.getTarget()).getParent();
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        //hvis man trykker på noe inne i cellen.
                        row = (TableRow) node.getParent();
                    }
                    try {
                        //åpne
                        openDetailedView(row);
                        updateTotalPrice();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean isDoubleClick(MouseEvent event) {
        return event.isPrimaryButtonDown() && event.getClickCount() == 2;
    }

    FXMLLoader getFxmlLoader(String fxml) throws IOException {
        FXMLGetter fxmlGetter = new FXMLGetter();
        FXMLLoader loader = fxmlGetter.getFxmlLoader(fxml);
        return loader;
    }

    //kan lett trekkes ut (se på tabcomponents for fasit)
    private void openDetailedView(TableRow row) throws IOException {
        //henter popup fxml
        System.out.println("her er vi i openDetailedView");
        FXMLLoader loader = getFxmlLoader("computerPopup.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        Computer c = (Computer) row.getItem();
        ComputerPopupController computerPopupController =
                loader.<ComputerPopupController>getController();
        computerPopupController.initData(c, stage);
        stage.show();
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
        App.setRoot("primary");
    }

    @FXML
    void btnCashier(ActionEvent event) throws IOException {
        List<String> whatsMissing = computerValidator.listOfMissingComponentTypes(getComputer());
        if (FileHandling.showDialogIfComponentsAreMissing(whatsMissing, stage)) return;
        openFinalDetails();
    }

    //todo legg dette i egen windowhandler? 0o
    private void openFinalDetails() throws IOException {

        System.out.println("her er vi i openDetailedView");
        FXMLLoader loader = getFxmlLoader("detailsPopup.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        DetailsController detailsController =
                loader.<DetailsController>getController();
        detailsController.initData(getComputer(), stage);
        stage.show();
    }

    Computer getComputer() {
        return ModelEndUser.INSTANCE.getComputer();
    }


    //denne kan også skrives om og flyttes til enduserlogic
    private void addComponentToCart(TableView<Component> tbl) {
        Component selectedComp = tbl.getSelectionModel().getSelectedItem();
        if (selectedComp != null) {
            endUserLogic.addComponentToComputer(selectedComp);
            updateComputerListView();
        }
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

    void updateComputerListView() {
        if (getComputer() != null)
            shoppingListView.setItems(getComputer().getComponentRegister().getObservableRegister());
        else {
            System.out.println(getComputer() + "computer er tom");
        }
        updateTotalPrice();
    }

    private void updateComponentViews() {
        endUserService.updateEndUserRegisters();
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        if (getComputer() != null) {
            String totalpris = String.format("%.0f", getComputer().calculatePrice()) + " kr";
            lblTotalPrice.setText(totalpris);
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

    //denne kan også flyttes ut lett.
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
}
