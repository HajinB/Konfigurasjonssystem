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
    //sånn instansiering fungerer ikke likevel..blir statisk - bør lage en klasse som henter fresh data ut fra
    // contextmodel
    private ComponentRegister componentRegister = ContextModel.INSTANCE.getComponentRegister();

    @FXML
    private ListView<Component> shoppingListView;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private TableView<Component> tblProsessor,tblProcessor, tblVideo,tblMemory, tblHardDisc, tblSkjermkort,
            tblHarddisk,tblTastatur, tblSSD,
            tblMinne, tblMus, tblSkjerm, tblAnnet;

    @FXML
    private TableColumn skjermkortClmPrice, prosessorClmPrice, harddiskClmPrice, tastaturClmPrice, minneClmPrice,
            musClmPrice, skjermClmPrice, AnnetClmPrice;


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
    void btnAddProsessorToCart(ActionEvent event) {
        if (computerValidator.prosessorValidator(getComputer())) {
            addComponentToCart(tblProsessor);
        } else {
            replaceComponentInCart("prosessor", tblProsessor);
        }
    }

    @FXML
    void btnAddHarddiskToCart(ActionEvent event) {
        if (computerValidator.harddiskValidator(getComputer())) {
            addComponentToCart(tblHarddisk);
        } else {
            replaceComponentInCart("harddisk", tblHarddisk);
        }
    }

    @FXML
    void
    btnAddMinneToCart(ActionEvent event) {
        if (computerValidator.minneValidator(getComputer())) {
            addComponentToCart(tblMinne);
        } else {
            replaceComponentInCart("minne", tblMinne);
        }

    }

    @FXML
    void btnAddSkjermToCart(ActionEvent e) {
        if (computerValidator.skjermValidator(getComputer())) {
            addComponentToCart(tblSkjerm);
        } else {
            replaceComponentInCart("skjerm", tblSkjerm);
        }

    }

    @FXML
    void btnAddAnnetToCart(ActionEvent event) {
        if (computerValidator.annetValidator(getComputer()))
            addComponentToCart(tblAnnet);
        else {
            replaceComponentInCart("annet", tblAnnet);
        }
    }

    @FXML
    void btnAddTastaturToCart(ActionEvent event) {
        if(computerValidator.tastaturValidator(getComputer()))
            addComponentToCart(tblTastatur);
        else{
            replaceComponentInCart("tastatur", tblAnnet);
        }
    }

    @FXML
    void
    btnAddMusToCart(ActionEvent event) {
        if(computerValidator.musValidator(getComputer()))
            addComponentToCart(tblMus);
        else{
            replaceComponentInCart("mus", tblMus);
        }
    }

    @FXML
    void btnAddSkjermkortToCart(ActionEvent event) {
        if(computerValidator.skjermkortValidator(getComputer()))
            addComponentToCart(tblSkjermkort);
        else{
            replaceComponentInCart("skjermkort", tblSkjermkort);
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
    @FXML
    public void initialize() throws IOException {
        endUserService.updateEndUserRegisters();
        System.out.println(endUserService.getHarddiskRegister().toString());
        updateComponentViews();
        initItemFiles();
        loadElementsFromFile();
        updateList();
        updateComputerListView();
        setCellFactoryListView();

        setTblCellFactory();

        //clmPrice.setCellValueFactory(new PropertyValueFactory<Component, Double>("Price"));


        //tblSkjermkort.getColumns().get(2).setCellFactory(priceCellFactory);


        //må vi sette opp column for price?

       // må alle tableviews arve fra en ting for å kunne ha samme setcellfactory ??? altså hvis jeg vil ha "kr" bak
        // alle prisfelt - må man da ha det for alle ?
        //må gjøre cellfactory på en column!!!!!!
    }
    private void loadElementsFromFile() {
        FileHandling.openSelectedComputerTxtFiles(ContextModel.INSTANCE.getCleanObjectList(), userPreferences.getStringPathToUser());
    }

    private void setTblCellFactory() {
        Callback<TableColumn, TableCell> priceCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new PriceFormatCell();
                    }
                };

        PriceFormatCell priceFormatCell = new PriceFormatCell();
        skjermkortClmPrice.setCellFactory(priceCellFactory);
    }

    private void setCellFactoryListView() {
        shoppingListView.setCellFactory(param -> new ListCell<Component>() {
            @Override
            protected void updateItem(Component c, boolean empty){
                super.updateItem(c, empty);
                if(empty || c == null || c.getProductName() == null){
                    setText("");
                } else{
                    setText(c.getProductName()+ "\n" +String.format("%.2f",c.getProductPrice()) + ",-");
                    //Change listener implemented.
                    shoppingListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<?
                                                                    extends Component> observable, Component oldValue, Component newValue) -> {
                        if(shoppingListView.isFocused()){
                            //prøver å displaye hele componenten hvis selectedrow er targeted ( kan gjøres på samme
                            // måte som tableview)
                            lblComponentMsg.setText(selectedProperty().toString());
                        }
                    });
                }

            }
        });
    }

    //By default, a ListView calls the toString()
    // method of its items and it displays the string in its cell.
    // In the updateItem() method of your custom ListCell, you can populate the text and graphic for the cell to
    // display anything you want in the cell based on the item in that cell.
    //https://examples.javacodegeeks.com/desktop-java/javafx/listview-javafx/javafx-listview-example/
    void updateComputerListView() {

        if (ContextModel.INSTANCE.getComputer() != null)
            shoppingListView.setItems(ContextModel.INSTANCE.getComputer().getComponentRegister().getObservableRegister());
        updateTotalPrice();
    }

    private void updateComponentViews() {
        endUserService.updateEndUserRegisters();
        ComponentRegister hardDiscRegister = new ComponentRegister();
        hardDiscRegister.getRegister().addAll(endUserService.getHarddiskRegister().getRegister());
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
        setTblVideo(tblVideo);
        setTblMemory(tblMemory);
        setTblProcessor(tblProsessor);
        setTblHardDisc(tblHarddisk);
        setTblAnnet(tblAnnet);
        setTblTastatur(tblTastatur);
        //fortsett for alle her

    }

    /**
     * går via endUserService for å hente lister som er filtrert på produkttype
     */
    private void setTblMemory(TableView<Component> tblMemory) {
        tblMemory.setItems(endUserService.getMinneRegister().getObservableRegister());
        this.tblMemory = tblMemory;
    }

    private void setTblVideo(TableView<Component> tblVideo) {
        tblVideo.setItems(endUserService.getSkjermkortRegister().getObservableRegister());
        this.tblVideo = tblVideo;
    }

    public void setTblProcessor(TableView<Component> tblProcessor) {
        tblProcessor.setItems(endUserService.getProsessorRegister().getObservableRegister());
        this.tblProcessor = tblProcessor;
    }

    public void setTblHardDisc(TableView<Component> tblHardDisc) {
        tblHardDisc.setItems(endUserService.getHarddiskRegister().getObservableRegister());
        this.tblHardDisc = tblHardDisc;
    }

    public void setTblTastatur(TableView<Component> tblTastatur) {
        tblTastatur.setItems(endUserService.getTastaturRegister().getObservableRegister());
        this.tblTastatur = tblTastatur;
    }

    public void setTblAnnet(TableView<Component> tblAnnet) {
        tblAnnet.setItems(endUserService.getAnnetRegister().getObservableRegister());
        this.tblAnnet = tblAnnet;
    }

    @FXML
    void btnBuyProcessor(ActionEvent event) {
        // tblProcessor.getSelectionModel().getSelectedCells();
        /**all adding av componenter må skje via enduserservice - legg til en metode der som legger til*/
        Component selectedComp = tblProcessor.getSelectionModel().getSelectedItem();
        ContextModel.INSTANCE.getComputer().addComponent(selectedComp);
        updateComputerListView();
    }

    @FXML
    void btnBuyVideo(ActionEvent event) {
        Component selectedComp = tblVideo.getSelectionModel().getSelectedItem();
        /**all adding av componenter må skje via enduserservice(?) - legg til en metode der som legger til*/
        if (selectedComp != null) {
            ContextModel.INSTANCE.getComputer().addComponent(selectedComp);
            updateComputerListView();
        }
        updateComputerListView();
    }

    private ComponentRegister getComputerComponentRegister() {
        //dette gir NPE fordi computer ikke er instansiert i contextmodel(?)
        return ContextModel.INSTANCE.getComputer().getComponentRegister();
    }


    public void btnBuyComputer(ActionEvent event) {

    }

    public void btnBuyScreen(ActionEvent event) {

    }

    public void btnBuyMemory(ActionEvent event) {

    }

    public void btnBuyHardDisc(ActionEvent event) {

    }

    public void btnBuyCabinet(ActionEvent event) {

    }

    public void btnBuyKeyBoard(ActionEvent event) {

    }

    public void btnBuyMouse(ActionEvent event) {

    }

    public void btnBuyOther(ActionEvent event) {

    }

    public void btnBuyMotherBoard(ActionEvent event) {

    }

    public void btnDeleteFromCart(ActionEvent event) {

    }

}
