package org.programutvikling.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.programutvikling.App;
import org.programutvikling.gui.CustomTableColumn.CustomTextWrapCellFactory;
import org.programutvikling.gui.utility.FXMLGetter;
import org.programutvikling.logic.EndUserLogic;
import org.programutvikling.model.Model;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.io.FileOpenerTxt;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerValidator;
import org.programutvikling.gui.CustomTableColumn.PriceFormatCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.EndUserService;
import org.programutvikling.gui.utility.FileUtility;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.application.Platform.runLater;


public class EnduserController extends TabComponentsController {
    @FXML
    public BorderPane topLevelPaneEndUser;
    EndUserService endUserService = new EndUserService();
    Stage stage;
    ComputerValidator computerValidator = new ComputerValidator();
    @FXML
    TableView<Computer> tblCompletedComputers;
    @FXML
    TableColumn computerPriceCln;
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
    public void initialize() throws IOException {


        addTableViewsToList();

        //todo så og si alle metoder under her kan trekkes ut av controlleren
        endUserLogic = new EndUserLogic(topLevelPaneEndUser,tblViewList, tblColumnDescriptionList, tblColumnPriceList);
        initTextWrapCellFactory();
        updateComponentViews();
        updateList();
        setCellFactoryListView();
        setTblCellFactory();
        endUserService.updateEndUserRegisters();
        updateComputerListView();
        setTblCompletedComputersListener();
        computerPriceCln.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        //setDblClickEvent();
        //todo se på hvordan dblclick event er hentet ut av controlleren og gjør det på ALLE tablerows..
        //mulig man må finne en ny måte å update listene på -fra denne controlleren(?)
        ObjectProperty<TableRow<Component>> lastSelectedRow = new SimpleObjectProperty<>();
        setListenerToClearSelection(lastSelectedRow);
    }

    private void initTextWrapCellFactory() {

        //oppretter en Callback, som gjør at vi kan sette en klasse som extender tablecell på
        // en kolonne i tableview
        Callback<TableColumn, TableCell> customTextWrapCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new CustomTextWrapCellFactory();
                    }
                };
        for(TableColumn tc : tblColumnDescriptionList){
            tc.setCellFactory(customTextWrapCellFactory);
        }
    }

    private void setListenerToClearSelection(ObjectProperty<TableRow<Component>> lastSelectedRow) {
        for (TableView<Component> t : tblViewList) {
            //går gjennom tableviewlisten for å finne den raden som sist ble valgt, blant alle tables,
            // for å bruke den i eventfilteret på toplevel-panen.
            t.setRowFactory(tableView -> {
                TableRow<Component> row = new TableRow<Component>();
                row.selectedProperty().addListener((objects, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        lastSelectedRow.set(row);
                    }
                });
                return row;
            });
        }

        //listener på toplevel element for å cleare selection
        this.topLevelPaneEndUser.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (lastSelectedRow.get() != null) {

                    //legal bounds for the object that a method is trying to access.
                    Bounds lastSelectedRowBounds =
                            lastSelectedRow.get().localToScene(lastSelectedRow.get().getLayoutBounds());
                    if (!lastSelectedRowBounds.contains(event.getSceneX(), event.getSceneY())) {
                        for (TableView<Component> t : tblViewList) {
                            t.getSelectionModel().clearSelection();
                        }
                    }
                }
            }
        });
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
    private void updateCompletedComputers() {
        if (Model.INSTANCE.getComputerRegister().getObservableRegister().size() > 0)
            tblCompletedComputers.setItems(Model.INSTANCE.getComputerRegister().getObservableRegister());
    }

    private void updateList() {
        updateTotalPrice();
        tblCompletedComputers.setItems(Model.INSTANCE.getComputerRegister().getObservableRegister());
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
    void btnCashier(ActionEvent event) {

    }

    Computer getComputer() {
        return Model.INSTANCE.getComputer();
    }

    private void addComponentToCart(TableView<Component> tbl) {
        Component selectedComp = tbl.getSelectionModel().getSelectedItem();
        /**all adding av componenter må skje via enduserservice(?) - legg til en metode der som legger til*/

        if (selectedComp != null) {
            endUserLogic.addComponentToComputer(selectedComp);
            updateComputerListView();
        }
    }

    @FXML
    public void btnOpenComputer(ActionEvent event) throws IOException {
        String path = FileUtility.getFilePathFromOpenTxtDialog(stage);
        FileOpenerTxt fileOpenerTxt = new FileOpenerTxt();
        fileOpenerTxt.open(getComputer(), Paths.get(path));

        //kjører fileopenertxt her - trenger man fileopener factory da?? er det lurt å la det gå til samme metode?
        // for å redusere kopi ? da kan man ta bort (Computer) fra fileopenertxt - gjør det fra objects der også?
        //er det nødvendig å ha interface
       // FileHandling.openObjects(Model.INSTANCE.getCleanObjectList(), path);
        Model.INSTANCE.loadComputerIntoClass();
        updateTotalPrice();
    }

    @FXML
    public void btnSavePC(ActionEvent event) throws IOException {
        List<String> whatsMissing = computerValidator.listOfMissingComponentTypes(getComputer());
        if (FileHandling.validateCartListToSave(whatsMissing, stage)) return;
        updateCompletedComputers();
    }

    //kan definere denne i en egen klasse - se på de andre for å gjøre det. CustomListViewCellFactory
    private void setCellFactoryListView() {
        shoppingListView.setCellFactory(param -> new ListCell<Component>() {
            @Override
            protected void updateItem(Component c, boolean empty) {
                super.updateItem(c, empty);
                if (empty || c == null || c.getProductName() == null) {
                    setText("");
                } else {
                    //bruker cell factory for å sette toString i listviewen.
                    setText(c.getProductType() + "\n" + c.getProductName() + "\n" + String.format("%.0f", c.getProductPrice()) + " kr");
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
            String totalpris = String.format("%.0f",getComputer().calculatePrice()) + " kr";
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
            FileHandling.saveAll();
        }
    }

    private void deleteComponent(Component selectedComp) {
        getComputer().getComponentRegister().getRegister().remove(selectedComp);
        updateComputerListView();
    }

    //Fjerner forrige valgte produkt etter at du har trykket #Legg i handlekurv
    public void clearSelection() {
        for(TableView t : tblViewList) {
            runLater(() -> {
                t.getSelectionModel().clearSelection();
            });
        }
    }

    @FXML
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
            //sett en label her hvis det ikke er valgt noe.
            //lblComponentMsg.setText("Velg en rad for å legge til i handlekurven");
        }

        clearSelection();  //?? why gjøre dette her
    }


}
