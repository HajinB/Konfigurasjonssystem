package org.programutvikling.gui;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.FileOpenerTxt;
import org.programutvikling.component.io.FileSaverTxt;
import org.programutvikling.computer.Computer;
import org.programutvikling.computer.ComputerFactory;
import org.programutvikling.computer.ComputerValidator;
import org.programutvikling.gui.CustomPriceTableColumn.PriceFormatCell;
import org.programutvikling.gui.CustomPriceTableColumn.TotalPriceFormatCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.EndUserService;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.UserPreferences;

import java.io.File;
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
    TableView<Computer> tblCompletedComputers;

    @FXML
    private TableView<Component>
            tblProcessor, tblVideoCard, tblScreen,
            tblOther, tblMemory, tblMouse, tblMotherBoard, tblCabinet, tblHardDisc, tblKeyboard;

    @FXML
    TableColumn<Computer, Computer> computerPriceCln;

    @FXML
    private TableColumn
            processorPriceCln, videoCardPriceCln, screenPriceCln, otherPriceCln,
            memoryPriceCln, mousePriceCln, motherBoardPriceCln, cabinetPriceCln, hardDiscPriceCln, keyboardPriceCln;
    @FXML
    public void initialize() throws IOException {
        endUserService.updateEndUserRegisters();
        loadElementsFromFile();
        updateComponentViews();
        updateList();
        updateComputerListView();
        setTblCellFactory();
        setCellFactoryListView();
        /*
         <cellValueFactory>
                                <PropertyValueFactory property="productPrice"/>
                            </cellValueFactory>*/
        //computerPriceCln.setCellValueFactory(new PropertyValueFactory<Computer, Computer>("productPrice"));
        //computerPriceCln.setCellFactory(new TotalPriceFormatCell<Computer>());
       computerPriceCln.setCellValueFactory(new PropertyValueFactory<>("productPrice"));


        //tblcomputer trenger en override av update items - som inni settexten bruker en annen metode for å hente
        // navn og totalpris.
    }

    private void setTblCellFactory() {
        Callback<TableColumn, TableCell> priceCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new PriceFormatCell();
                    }
                };
        PriceFormatCell priceFormatCell = new PriceFormatCell();
/*
        computerPriceCln.setCellFactory(list -> new TableCell<Computer, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
//todo tableviewen til completed computers finner ikke calculate price - bytt den til tableview?
                    //hva skal man sette her for å få til å vise pris?
                    //hvorfor får jeg ikke tak i Computeren fra her?????
                    setText(Double.toString(getPrice()));
                }
            }
        });*/

        processorPriceCln.setCellFactory(priceCellFactory);
        videoCardPriceCln.setCellFactory(priceCellFactory);
        screenPriceCln.setCellFactory(priceCellFactory);
        otherPriceCln.setCellFactory(priceCellFactory);
        memoryPriceCln.setCellFactory(priceCellFactory);
        cabinetPriceCln.setCellFactory(priceCellFactory);
        motherBoardPriceCln.setCellFactory(priceCellFactory);
        mousePriceCln.setCellFactory(priceCellFactory);
        hardDiscPriceCln.setCellFactory(priceCellFactory);
        keyboardPriceCln.setCellFactory(priceCellFactory);



        //tblHardDisc.getSelectionModel().getTableView().getColumns().get(2);
        //tblHardDisc.getSelectionModel().getTableView().getColumns().get(2).setCellFactory(priceCellFactory);
    }

    private double getPrice() {
        //hvordan skal man få prisen i tableview?
        return getComputer().calculatePrice();
    }

    private void updateCompletedComputers(){
        if(ContextModel.INSTANCE.getComputerRegister().getObservableRegister().size()>0)
        tblCompletedComputers.setItems(ContextModel.INSTANCE.getComputerRegister().getObservableRegister());
    }

    private void updateList() {
        updateTotalPrice();
        tblCompletedComputers.setItems(ContextModel.INSTANCE.getComputerRegister().getObservableRegister());
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
        //setTblAnnet(tblAnnet);
        // setTblTastatur(tblTastatur);
        //fortsett for alle her
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
            getComputer().addComponent(selectedComp);
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
            getComputer().addComponent(selectedComp);
            updateComputerListView();
        }
    }


    @FXML
    public void btnOpenComputer(ActionEvent event) throws IOException {
        String path = FileUtility.getFilePathFromOpenTxtDialog(stage);
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        fileOpenerTxt.open(getComputer(), Paths.get(path));
        updateTotalPrice();
    }

    @FXML
    public void btnSavePC(ActionEvent event) throws IOException {
        FileSaverTxt fileSaverTxt = new FileSaverTxt();
        String path = FileUtility.getFilePathFromSaveTXTDialog(stage);
        if(path==null){
            return;
        }
        fileSaverTxt.save(getComputer(), Paths.get(path));
        System.out.println(path);

        File file = new File(path);
        ComputerFactory computerFactory = new ComputerFactory();

        String name = FileUtility.getNameFromFilePath(file);
        Computer computer =  computerFactory.computerFactory(getComputer().getComponentRegister(), name);
        //lag ny pc og legg den i computerregisteret!!!!
        //pcFactoryMethod ? tar inn navn og komponentregister?
        ContextModel.INSTANCE.getComputerRegister().addComputer(computer);
        System.out.println(getComputer());
        updateCompletedComputers();
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
        if (getComputer() != null)
            shoppingListView.setItems(getComputer().getComponentRegister().getObservableRegister());
        updateTotalPrice();
    }

    private void updateComponentViews() {
        endUserService.updateEndUserRegisters();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        if(getComputer()!=null) {
            String totalpris = getComputer().calculatePrice() + ",-";
            lblTotalPrice.setText(totalpris);
        }
    }

    void addComponentToComputer() {

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
        this.tblHardDisc = tblHardDisc;
        tblHardDisc.setItems(endUserService.getHardDiscRegister().getObservableRegister());
    }

    public void setTblKeyboard(TableView<Component> tblKeyboard) {
        tblKeyboard.setItems(endUserService.getKeyboardRegister().getObservableRegister());
        this.tblKeyboard=tblKeyboard;
    }

    public void setTblMouse(TableView<Component> tblMouse) {
        this.tblMouse = tblMouse;
        tblMouse.setItems(endUserService.getMouseRegister().getObservableRegister());
    }

    private void setTblMotherBoard(TableView<Component> tblMotherBoard) {
        this.tblMotherBoard=tblMotherBoard;
        tblMotherBoard.setItems(endUserService.getMotherboardRegister().getObservableRegister());
    }

    private void setTblCabinet(TableView<Component> tblCabinet) {
        this.tblCabinet=tblCabinet;
        tblCabinet.setItems(endUserService.getCabinetRegister().getObservableRegister());
    }

    private void setTblOther(TableView<Component> tblOther) {
        this.tblOther=tblCabinet;
        tblOther.setItems(endUserService.getOtherRegister().getObservableRegister());
    }

    private void setTblScreen(TableView<Component> tblScreen) {
        this.tblScreen=tblScreen;
        tblScreen.setItems(endUserService.getScreenRegister().getObservableRegister());
    }




    private void loadElementsFromFile() {
        System.out.println("rett før load elements from file er det jobj?"+userPreferences.getStringPathToUser());
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
        return getComputer().getComponentRegister();
    }


    public void btnBuyComputer(ActionEvent event) {

    }

    @FXML
    void btnBuyProcessor(ActionEvent event) {
        if (computerValidator.processorListValidator(getComputer())) {
            addComponentToCart(tblProcessor);
        } else {
            replaceComponentInCart("prosessor", tblProcessor);
        }
    }

    @FXML
    void btnBuyVideo(ActionEvent event) {
        if (computerValidator.videoCardListValidator(getComputer()))
            addComponentToCart(tblVideoCard);
        else {
            replaceComponentInCart("skjermkort", tblVideoCard);
        }
    }

    public void btnBuyScreen(ActionEvent event) {
        if (computerValidator.screenListValidator(getComputer())) {
            addComponentToCart(tblScreen);
        } else {
            replaceComponentInCart("skjerm", tblScreen);
        }
    }

    public void btnBuyMemory(ActionEvent event) {
        if (computerValidator.memoryListValidator(getComputer())) {
            addComponentToCart(tblMemory);
        } else {
            replaceComponentInCart("minne", tblMemory);
        }
    }

    public void btnBuyHardDisc(ActionEvent event) {
        if (computerValidator.hardDiscListValidator(getComputer())) {
            addComponentToCart(tblHardDisc);
        } else {
            replaceComponentInCart("harddisk", tblHardDisc);
        }

    }

    public void btnBuyCabinet(ActionEvent event) {
        if (computerValidator.cabinetListValidator(getComputer())) {
            addComponentToCart(tblCabinet);
        } else {
            replaceComponentInCart("harddisk", tblCabinet);
        }

    }

    public void btnBuyKeyBoard(ActionEvent event) {
        if (computerValidator.keyboardListValidator(getComputer()))
            addComponentToCart(tblKeyboard);
        else {
            replaceComponentInCart("tastatur", tblKeyboard);
        }
    }

    public void btnBuyMouse(ActionEvent event) {
        if (computerValidator.mouseListValidator(getComputer()))
            addComponentToCart(tblMouse);
        else {
            replaceComponentInCart("mus", tblMouse);
        }

    }

    public void btnBuyOther(ActionEvent event) {
        if (computerValidator.otherListValidator(getComputer()))
            addComponentToCart(tblOther);
        else {
            replaceComponentInCart("annet", tblOther);
        }

    }

    public void btnBuyMotherBoard(ActionEvent event) {
        if (computerValidator.motherboardListValidator(getComputer()))
            addComponentToCart(tblMotherBoard);
        else {
            replaceComponentInCart("annet", tblMotherBoard);
        }

    }

    public void btnDeleteFromCart(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "Trykk ja for å slette", "Vil du slette ",
                shoppingListView.getSelectionModel().getSelectedItems().get(0).getProductName());
        alert.showAndWait();
        if(shoppingListView.getSelectionModel().isEmpty()){
            return;
        }
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
