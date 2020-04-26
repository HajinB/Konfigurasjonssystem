package org.programutvikling.gui;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.FileOpenerTxt;
import org.programutvikling.component.io.FileSaverTxt;
import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerValidator;
import org.programutvikling.gui.CustomPriceTableColumn.PriceFormatCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.EndUserService;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.UserPreferences;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


public class EnduserController extends TabComponentsController {

    EndUserService endUserService = new EndUserService();
    Stage stage;
    ComputerValidator computerValidator = new ComputerValidator();
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    @FXML
    private ListView<Component> shoppingListView;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private TableView<Component> tblProcessor, tblScreen, tblOther,
            tblMemory, tblMouse, tblVideoCard, tblMotherBoard, tblCabinet, tblHardDisc, tblKeyboard;
    @FXML
    private TableColumn videoPriceCln;

    @FXML
    public void initialize() throws IOException {
        endUserService.updateEndUserRegisters();
        updateComponentViews();
        initItemFiles();
        loadElementsFromFile();
        updateList();
        updateComputerListView();
        setTblCellFactory();
        setCellFactoryListView();
    }

    @FXML
    void btnLogout(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void btnCashier(ActionEvent event) {

    }

    Computer getComputer() {
        return ContextModel.INSTANCE.getComputer();
    }

    public void initItemFiles() {
        //computerRegister.addComponent();
    }


    private void addComponentToCart(TableView<Component> tbl) {
        Component selectedComp = tbl.getSelectionModel().getSelectedItem();
        /**all adding av componenter må skje via enduserservice(?) - legg til en metode der som legger til*/
        if (selectedComp != null) {
            ContextModel.INSTANCE.getComputer().addComponent(selectedComp);
            updateComputerListView();
        }
        updateComputerListView();
    }

    private void replaceComponentInCart(String s, TableView<Component> tblProsessor) {
        Component selectedComp = tblProsessor.getSelectionModel().getSelectedItem();
        if (selectedComp != null) {
            /**FJERN EN AV DE AV DEN GITTE TYPEN - så legg til*/
            /**evt legg til confirmation alert her*/
            List<Component> list = getComputer().getComponentRegister().filterByProductType(s);
            if (list.size() > 0) {
                getComputer().getComponentRegister().getRegister().remove(list.get(0));
            }
            ContextModel.INSTANCE.getComputer().addComponent(selectedComp);
            updateComputerListView();
        }
    }


    @FXML
    public void btnOpenComputer(ActionEvent event) throws IOException {
        String path = FileUtility.getFilePathFromOpenDialog(stage);
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        fileOpenerTxt.open(ContextModel.INSTANCE.getComputer(), Paths.get(path));
    }

    @FXML
    public void btnSavePC(ActionEvent event) throws IOException {
        FileSaverTxt fileSaverTxt = new FileSaverTxt();
        String path = FileUtility.getFilePathFromSaveTXTDialog(stage);
        fileSaverTxt.save(ContextModel.INSTANCE.getComputer(), Paths.get(path));
    }


    private void setTblCellFactory() {
        Callback<TableColumn, TableCell> priceCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new PriceFormatCell();
                    }
                };

        PriceFormatCell priceFormatCell = new PriceFormatCell();
        videoPriceCln.setCellFactory(priceCellFactory);
    }

    private void setCellFactoryListView() {
        shoppingListView.setCellFactory(param -> new ListCell<Component>() {
            @Override
            protected void updateItem(Component c, boolean empty) {
                super.updateItem(c, empty);
                if (empty || c == null || c.getProductName() == null) {
                    setText("");
                } else {
                    setText(c.getProductName() + "\n" + String.format("%.2f", c.getProductPrice()) + ",-");
                    //Change listener implemented.
                    shoppingListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<?
                            extends Component> observable, Component oldValue, Component newValue) -> {
                        if (shoppingListView.isFocused()) {
                            //prøver å displaye hele componenten hvis selectedrow er targeted ( kan gjøres på samme
                            // måte som tableview)
                            //lblComponentMsg.setText(selectedProperty().toString());
                        }
                    });
                }

            }
        });
    }

    void updateComputerListView() {

        if (ContextModel.INSTANCE.getComputer() != null)
            shoppingListView.setItems(ContextModel.INSTANCE.getComputer().getComponentRegister().getObservableRegister());
        updateTotalPrice();
    }

    private void updateComponentViews() {
        endUserService.updateEndUserRegisters();
        ComponentRegister hardDiscRegister = new ComponentRegister();
        hardDiscRegister.getRegister().addAll(endUserService.getHardDiscRegister().getRegister());
        System.out.println(hardDiscRegister.toString());
        //System.out.println(endUserService.getHarddiskRegister().toString());
        //endUserService.getHarddiskRegister().attachTableView(tblViewHarddisk);
        //tblViewHarddisk.setItems(endUserService.getHarddiskRegister().getObservableRegister());
        //hvorfor NPE her?
        //tblViewComponent.refresh();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        String totalpris = ContextModel.INSTANCE.getComputer().calculatePrice() + ",-";
        lblTotalPrice.setText(totalpris);
    }

    void addComponentToComputer() {

    }

    private void updateList() {
        updateTotalPrice();
        setTblProcessor(tblProcessor);
        setTblHardDisc(tblHardDisc);
        setTblVideoCard(tblVideoCard);
        setTblMemory(tblMemory);
        //setTblAnnet(tblAnnet);
        // setTblTastatur(tblTastatur);
        //fortsett for alle her
    }

    /**
     * går via endUserService for å hente lister som er filtrert på produkttype
     */
    private void setTblMemory(TableView<Component> tblMemory) {
        tblMemory.setItems(endUserService.getMemoryRegister().getObservableRegister());
        this.tblMemory = tblMemory;
    }

    private void setTblVideoCard(TableView<Component> tblVideoCard) {
        tblVideoCard.setItems(endUserService.getVideoRegister().getObservableRegister());
        this.tblVideoCard = tblVideoCard;
    }

    public void setTblProcessor(TableView<Component> tblProcessor) {
        tblProcessor.setItems(endUserService.getProcessorRegister().getObservableRegister());
        this.tblProcessor = tblProcessor;
    }

    public void setTblHardDisc(TableView<Component> tblHardDisc) {
        tblHardDisc.setItems(endUserService.getHardDiscRegister().getObservableRegister());
        this.tblHardDisc = tblHardDisc;

    }

    public void setTblKeyboard(TableView<Component> tblTastatur) {
        tblTastatur.setItems(endUserService.getKeyboardRegister().getObservableRegister());
        this.tblKeyboard = tblTastatur;
    }


    private void loadElementsFromFile() {
        FileHandling.openSelectedComputerTxtFiles(ContextModel.INSTANCE.getCleanObjectList(), userPreferences.getStringPathToUser());
    }



    /*
        public void setTblAnnet(TableView<Component> tblAnnet) {
            tblAnnet.setItems(endUserService.getAnnetRegister().getObservableRegister());
            this.tblAnnet = tblAnnet;
        }
    */
    private ComponentRegister getComputerComponentRegister() {
        //dette gir NPE fordi computer ikke er instansiert i contextmodel(?)
        return ContextModel.INSTANCE.getComputer().getComponentRegister();
    }


    public void btnBuyComputer(ActionEvent event) {

    }

    @FXML
    void btnBuyProcessor(ActionEvent event) {
        if (computerValidator.processorValidator(getComputer())) {
            addComponentToCart(tblProcessor);
        } else {
            replaceComponentInCart("prosessor", tblProcessor);
        }
    }

    @FXML
    void btnBuyVideo(ActionEvent event) {
        if (computerValidator.videoValidator(getComputer()))
            addComponentToCart(tblVideoCard);
        else {
            replaceComponentInCart("skjermkort", tblVideoCard);
        }
    }

    public void btnBuyScreen(ActionEvent event) {
        if (computerValidator.screenValidator(getComputer())) {
            addComponentToCart(tblScreen);
        } else {
            replaceComponentInCart("skjerm", tblScreen);
        }
    }

    public void btnBuyMemory(ActionEvent event) {
        if (computerValidator.memoryValidator(getComputer())) {
            addComponentToCart(tblMemory);
        } else {
            replaceComponentInCart("minne", tblMemory);
        }
    }

    public void btnBuyHardDisc(ActionEvent event) {

    }

    public void btnBuyCabinet(ActionEvent event) {

    }

    public void btnBuyKeyBoard(ActionEvent event) {
        if (computerValidator.keyboardValidator(getComputer()))
            addComponentToCart(tblKeyboard);
        else {
            replaceComponentInCart("tastatur", tblKeyboard);
        }
    }

    public void btnBuyMouse(ActionEvent event) {
        if (computerValidator.mouseValidator(getComputer()))
            addComponentToCart(tblMouse);
        else {
            replaceComponentInCart("mus", tblMouse);
        }

    }

    public void btnBuyOther(ActionEvent event) {

    }

    public void btnBuyMotherBoard(ActionEvent event) {

    }

    public void btnDeleteFromCart(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "Trykk ja for å slette", "Vil du slette ",
                shoppingListView.getSelectionModel().getSelectedItems().get(0).getProductName());
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            Component selectedComp = shoppingListView.getSelectionModel().getSelectedItem();
            deleteComponent(selectedComp);
            fileHandling.saveAll();
        }
    }

    private void deleteComponent(Component selectedComp) {
        getComputer().getComponentRegister().removeComponent(selectedComp);
        updateComputerListView();
    }
}
