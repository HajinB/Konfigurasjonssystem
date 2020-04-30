package org.programutvikling.gui;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.EndUserService;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.gui.utility.TemporaryComponent;
import org.programutvikling.user.UserPreferences;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


public class EnduserController extends TabComponentsController {

    EndUserService endUserService = new EndUserService();
    Stage stage;
    ComputerValidator computerValidator = new ComputerValidator();
    @FXML
    TableView<Computer> tblCompletedComputers;
    @FXML
    TableColumn computerPriceCln;
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Database/AppData.jobj");
    @FXML
    private ListView<Component> shoppingListView;
    @FXML
    private Label lblTotalPrice;
    @FXML
    private TableView<Component>
            tblProcessor, tblVideoCard, tblScreen,
            tblOther, tblMemory, tblMouse, tblMotherBoard, tblCabinet, tblHardDisc, tblKeyboard;
    @FXML
    private TableColumn
            processorPriceCln, videoCardPriceCln, screenPriceCln, otherPriceCln,
            memoryPriceCln, mousePriceCln, motherBoardPriceCln, cabinetPriceCln, hardDiscPriceCln, keyboardPriceCln;

    @FXML
    public void initialize() throws IOException {
        endUserService.updateEndUserRegisters();
        //loadElementsFromFile();
        updateComponentViews();
        updateList();
        updateComputerListView();
        setTblCellFactory();
        setCellFactoryListView();
        setTblCompletedComputersListener();
        computerPriceCln.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        tblScreen.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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

                    Component c = (Component) row.getItem();
                    addComponentToComputer(c);
                }
            }
        });
    }



    private void setTblCompletedComputersListener() {
        /**detecter tablerow, for å hente ut component*/
        //skal åpne en fxml, og sende cell-content til initmetoden til controlleren til denne fxmln
        tblCompletedComputers.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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

    private void openDetailedView(TableRow row) throws IOException {
        //henter popup fxml
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

    private void setTblCellFactory() {

        //oppretter en cellfactory object for pris kolonnene
        Callback<TableColumn, TableCell> priceCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new PriceFormatCell();
                    }
                };
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

    }

    private double getPrice() {
        //hvordan skal man få prisen i tableview?
        return getComputer().calculatePrice();
    }

    private void updateCompletedComputers() {
        if (ContextModel.INSTANCE.getComputerRegister().getObservableRegister().size() > 0)
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

        List<String> whatsMissing = computerValidator.listOfMissingComponentTypes(getComputer());

        if (whatsMissing.size() > 0) {
            /**Kan lage en bra tostring av whatsMissing - evt en utility method - FileUtility.*/
            Dialog.showErrorDialog("Legg til " + whatsMissing.toString() + " for å " +
                    "lagre");
            return;
        }
        FileSaverTxt fileSaverTxt = new FileSaverTxt();
        String path = FileUtility.getFilePathFromSaveTXTDialog(stage);
        if (path == null) {
            return;
        }

        fileSaverTxt.save(getComputer(), Paths.get(path));
        System.out.println(path);

        File file = new File(path);
        ComputerFactory computerFactory = new ComputerFactory();
        String name = FileUtility.getNameFromFilePath(file);
        Computer computer = computerFactory.computerFactory(getComputer().getComponentRegister(), name);
        ContextModel.INSTANCE.getComputerRegister().addComputer(computer);
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
        if (getComputer() != null) {
            String totalpris = getComputer().calculatePrice() + ",-";
            lblTotalPrice.setText(totalpris);
        }
    }

    void addComponentToComputer(Component component) {
        if(true) // validering aka sjekk type også run liste-test
        getComputer().addComponent(component);
        updateComputerListView();
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


    private void loadElementsFromFile() {
        System.out.println("rett før load elements from file er det jobj?" + userPreferences.getStringPathToUser());
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


    public void btnDeleteFromCart(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "Trykk ja for å slette", "Vil du slette ",
                shoppingListView.getSelectionModel().getSelectedItems().get(0).getProductName());
        alert.showAndWait();
        if (shoppingListView.getSelectionModel().isEmpty()) {
            return;
        }
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            Component selectedComp = shoppingListView.getSelectionModel().getSelectedItem();
            deleteComponent(selectedComp);
            fileHandling.saveAll();
        }
    }

    private void deleteComponent(Component selectedComp) {
        getComputer().getComponentRegister().getRegister().remove(selectedComp);
        updateComputerListView();
    }

    //Fjerner forrige trykk på en rad i tblview. Problem: Du får ikke lagt til noe i handlekurven..
    private void removeSelection (){
        tblProcessor.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblProcessor.getSelectionModel().clearSelection();
            }
        });
        tblMotherBoard.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblMotherBoard.getSelectionModel().clearSelection();
            }
        });
        tblVideoCard.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblVideoCard.getSelectionModel().clearSelection();
            }
        });
        tblScreen.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblScreen.getSelectionModel().clearSelection();
            }
        });
        tblHardDisc.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblHardDisc.getSelectionModel().clearSelection();
            }
        });
        tblMemory.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblMemory.getSelectionModel().clearSelection();
            }
        });
        tblCabinet.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblCabinet.getSelectionModel().clearSelection();
            }
        });
        tblMouse.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblMouse.getSelectionModel().clearSelection();
            }
        });
        tblKeyboard.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblKeyboard.getSelectionModel().clearSelection();
            }
        });
        tblOther.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblOther.getSelectionModel().clearSelection();
            }
        });
        tblCompletedComputers.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                tblCompletedComputers.getSelectionModel().clearSelection();
            }
        });

    }
    //Fjerner forrige valgte produkt etter at du har trykket #Legg i handlekurv
    public void clearSelection() {
        Platform.runLater( ()-> {  tblProcessor.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblMotherBoard.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblMouse.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblMemory.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblHardDisc.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblScreen.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblVideoCard.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblOther.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblCompletedComputers.getSelectionModel().clearSelection();  });
        Platform.runLater( ()-> {  tblCabinet.getSelectionModel().clearSelection();  });

    }

    public void btnAddToCart(ActionEvent event) {
        clearSelection();

        if (computerValidator.videoCardListValidator(getComputer()))
            addComponentToCart(tblProcessor);
        else {
            replaceComponentInCart("prosessor", tblProcessor);
        }

        if (computerValidator.videoCardListValidator(getComputer()))
            addComponentToCart(tblVideoCard);
        else {
            replaceComponentInCart("skjermkort", tblVideoCard);
        }

        if (computerValidator.screenListValidator(getComputer())) {
            addComponentToCart(tblScreen);
        } else {
            replaceComponentInCart("skjerm", tblScreen);
        }

        if (computerValidator.memoryListValidator(getComputer())) {
            addComponentToCart(tblMemory);
        } else {
            replaceComponentInCart("minne", tblMemory);
        }

        if (computerValidator.hardDiscListValidator(getComputer())) {
            addComponentToCart(tblHardDisc);
        } else {
            replaceComponentInCart("harddisk", tblHardDisc);
        }


        if (computerValidator.cabinetListValidator(getComputer())) {
            addComponentToCart(tblCabinet);
        } else {
            replaceComponentInCart("kabinett", tblCabinet);
        }

        if (computerValidator.keyboardListValidator(getComputer()))
            addComponentToCart(tblKeyboard);
        else {
            replaceComponentInCart("tastatur", tblKeyboard);
        }
        if (computerValidator.mouseListValidator(getComputer()))
            addComponentToCart(tblMouse);
        else {
            replaceComponentInCart("mus", tblMouse);
        }

        if (computerValidator.otherListValidator(getComputer()))
            addComponentToCart(tblOther);
        else {
            replaceComponentInCart("annet", tblOther);
        }

        if (computerValidator.motherboardListValidator(getComputer()))
            addComponentToCart(tblMotherBoard);
        else {
            replaceComponentInCart("hovedkort", tblMotherBoard);
        }
    }

}
