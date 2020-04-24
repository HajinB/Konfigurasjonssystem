package org.programutvikling.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.programutvikling.App;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.io.FileSaverTxt;
import org.programutvikling.gui.utility.EndUserService;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.user.UserPreferences;

import java.io.IOException;
import java.nio.file.Paths;


public class EnduserController extends TabComponentsController {
    EndUserService endUserService = new EndUserService();
    Stage stage;
    private UserPreferences userPreferences = new UserPreferences("FileDirectory/Components/ComponentList.jobj");
    //sånn instansiering fungerer ikke likevel..blir statisk - bør lage en klasse som henter fresh data ut fra
    // contextmodel
    private ComponentRegister componentRegister = ContextModel.INSTANCE.getComponentRegister();

    @FXML
    private ListView<Component> shoppingListView;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private TableView<Component> tblProcessor;

    @FXML
    private TableView<Component> tblVideo;

    @FXML
    private TableView<Component> tblMemory;

    @FXML
    private TableView<Component> tblHardDisc;

    @FXML
    private TableView<Component> tblSSD;

    @FXML
    private TableView<Component> tblTastatur;

    @FXML
    private TableView<Component> tblMus;

    @FXML
    private TableView<Component> tblSkjerm;

    @FXML
    private TableView<Component> tblAnnet;

    @FXML
    void btnLogout(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void btnCashier(ActionEvent event) {

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
    }

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

    public void initItemFiles() {
        //computerRegister.addComponent();

    }

    private void loadElementsFromFile() {
        FileHandling.openSelectedComputerTxtFiles(ContextModel.INSTANCE.getCleanObjectList(), userPreferences.getStringPathToUser());
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


    public void btnSavePC(ActionEvent event) throws IOException {
        FileSaverTxt fileSaverTxt = new FileSaverTxt();
        String path = FileUtility.getFilePathFromSaveTXTDialog(stage);
        fileSaverTxt.save(ContextModel.INSTANCE.getComputer(), Paths.get(path));
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
