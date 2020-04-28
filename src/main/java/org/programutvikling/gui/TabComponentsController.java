package org.programutvikling.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.gui.CustomPriceTableColumn.PriceFormatCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.*;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class TabComponentsController {
    final Tooltip tooltip = new Tooltip("Dobbeltklikk en rad for å redigere");
    @FXML
    public Label lblComponentMsg;
    SecondaryController secondaryController;
    @FXML
    AnchorPane topLevelPane;
    ComponentTypes componentTypes = new ComponentTypes();
    ThreadHandler threadHandler;
    FileHandling fileHandling = new FileHandling();
    private Stage stage;
    private RegistryComponentLogic registryComponentLogic;
    private Converter.DoubleStringConverter doubleStrConverter
            = new Converter.DoubleStringConverter();
    @FXML
    private GridPane componentRegNode;
    @FXML
    private ChoiceBox<String> cbType, cbTypeFilter;
    @FXML
    private ComboBox<String> cbRecentFiles;
    @FXML
    private TextField componentSearch;
    @FXML
    private TableView<Component> tblViewComponent;
    @FXML
    private TableColumn productPriceColumn;
    @FXML
    private ContextMenu cm;

    @FXML
    public void initialize() throws IOException {
        System.out.println("hei fra init tabcomponents");
        FileUtility.populateRecentFiles();
        initChoiceBoxes();
        registryComponentLogic = new RegistryComponentLogic(componentRegNode);
        threadHandler = new ThreadHandler(this);
        initTableView();
    }

    private void initTableView() {
        //productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(doubleStrConverter));
        tblViewComponent.setOnMouseClicked((MouseEvent event) -> tblViewComponent.sort());
        tblViewComponent.setTooltip(tooltip);
        setTblCellFactory();
        setContextMenu();
        updateView();
    }

    private void setContextMenu() {
        tblViewComponent.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.SECONDARY) {
                    cm.show(tblViewComponent, t.getScreenX(), t.getScreenY());
                }
            }
        });
    }

    @FXML
    void cmDeleteRow(ActionEvent event) throws IOException {
        askForDeletion(tblViewComponent.getSelectionModel().getSelectedItem());
        //deleteComponent();
    }

    private void setTblCellFactory() {
        Callback<TableColumn, TableCell> priceCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new PriceFormatCell();
                    }
                };
        PriceFormatCell priceFormatCell = new PriceFormatCell();
        productPriceColumn.setCellFactory((priceCellFactory));
    }



    @FXML
    void dblClickTblRow(MouseEvent event) {
        handlePopup();
    }

    private void handlePopup() {
        /**detecter tablerow, for å hente ut component*/
        tblViewComponent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tblViewComponent.getSelectionModel().setCellSelectionEnabled(false);

                //tblViewComponent.getSelectionModel().setCellSelectionEnabled(false);
                TableRow row;
                TableColumn column;
                if (isDoubleClick(event)) {
                    Node node = ((Node) event.getTarget()).getParent();
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        //hvis man trykker på tekst
                        row = (TableRow) node.getParent();
                    }
                    try {
                        openEditWindow(row);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private boolean isDoubleClick(MouseEvent event) {
                return event.isPrimaryButtonDown() && event.getClickCount() == 2;
            }
        });

        /**detecter tablecolumn, for å kunne fokusere på riktig celle i popupvindu*/
        final ObservableList<TablePosition> selectedCells = tblViewComponent.getSelectionModel().getSelectedCells();
        //gjør det mulig å detecte cell på første klikk:
        tblViewComponent.getSelectionModel().setCellSelectionEnabled(true);
        selectedCells.addListener(new ListChangeListener() {

            @Override
            public void onChanged(Change c) {

                if(selectedCells.size()!=0) {
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);

                    Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                    TemporaryComponent.INSTANCE.setColumnIndex(selectedCells.get(0).getColumn());
                }
                //System.out.println(selectedCells.get(0).getColumn());
            }
        });
    }

    void handlePopUp(Stage stage, Component c) {
        /**Detecter om brukeren trykket "endre" eller krysset ut vinduet*/
        stage.setOnHidden(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent we) {
                System.out.println("detected");
                if (TemporaryComponent.INSTANCE.getIsEdited()) {
                    getObservableRegister().set(getObservableRegister().indexOf(c),
                            TemporaryComponent.INSTANCE.getTempComponent());
                    TemporaryComponent.INSTANCE.resetTemps();
                    updateView();
                    tblViewComponent.refresh();
                    try {
                        saveAll();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void openEditWindow(TableRow row) throws IOException {

        FXMLLoader loader = getFxmlLoader("editPopup.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        Component c = (Component) row.getItem();
        EditPopupController popupController =
                loader.<EditPopupController>getController();
        popupController.initData(c, stage, TemporaryComponent.INSTANCE.getColumnIndex());
        stage.show();
        handlePopUp(stage, c);


    }


    @FXML
    void search(KeyEvent event) {
        if (cbTypeFilter.getValue().equals("Ingen filter") || cbTypeFilter.getValue() == null) {
            setSearchedList();
        } else {
            tblViewComponent.setItems(getSearchedAndFilteredList());
        }
    }

    ObservableList<Component> getSearchedAndFilteredList() {
        return Search.getFilteredList(getComponentRegister()
                .filterByProductType(cbTypeFilter.getValue().toLowerCase()), componentSearch.getText());
    }


    @FXML
    void btnAddComponent(ActionEvent event) throws IOException {
        registerComponent();
        updateView();
        fileHandling.saveAll();
    }

    @FXML
    void btnOpenRecentFile(ActionEvent event) throws IOException {
        if ((cbRecentFiles.getSelectionModel().isEmpty())) {
            Dialog.showErrorDialog("velg en fil fra listen");
            //System.out.println("velg en fil fra listen");
            return;
        }
        getConfirmationThenOpenRecent();
    }

    private void getConfirmationThenOpenRecent() throws IOException {
        String chosenFile = cbRecentFiles.getSelectionModel().getSelectedItem();
        openFileConfirmation(chosenFile);
    }

    public void openFileConfirmation(String chosenFile) throws IOException {
        Alert alert = Dialog.getOpenOption(
                "Åpne fil",
                "Legg til i listen eller overskriv. Under «Verktøy» kan du fjerne eventuelle duplikater",
                "Vil du åpne ",
                cbRecentFiles.getSelectionModel().getSelectedItem() + "?");
        alert.showAndWait();
        handleOpenOptions(chosenFile, alert);
    }

    public void handleOpenOptions(String chosenFile, Alert alert) throws IOException {
        //button.get(2) == avbryt
        if(alert.getResult() == alert.getButtonTypes().get(2)){
            return;
        }
        //button.get(1) == overskriv
        if (alert.getResult() == alert.getButtonTypes().get(1)) {
            openThread(chosenFile);
            ContextModel.INSTANCE.loadComponentRegisterIntoModel();
            refreshTableAndSave();
        }
        //button.get(0) == legg til
        if(alert.getResult() == alert.getButtonTypes().get(0)){
            openThread(chosenFile);
            ContextModel.INSTANCE.appendComponentRegisterIntoModel();
            //getComponentRegister().removeDuplicates();
            refreshTableAndSave();
        }

        /*
        //button.get(2) == avbryt
        if (alert.getResult() == alert.getButtonTypes().get(2)){
            return;
        }
        //button.get(1) == overskriv
        if (alert.getResult() == alert.getButtonTypes().get(1)) {
            tabComponentsController.openThread(chosenFile);
            ContextModel.INSTANCE.loadComponentRegisterIntoModel();
            tabComponentsController.refreshTableAndSave();
        }
        //button.get(0) == legg til
        if(alert.getResult() == alert.getButtonTypes().get(0)){
            tabComponentsController.openThread(chosenFile);
            ContextModel.INSTANCE.appendComponentRegisterIntoModel();
            //kan ta bort duplikater her
            //getComponentRegister().removeDuplicates();
            tabComponentsController.refreshTableAndSave();
        }
        fileHandling.saveAll();
        */
    }

    private boolean isFileSelectionEmpty(String chosenFile) {
        return chosenFile.equals("Åpne nylige filer");
    }

    @FXML
    private void filterByTypeSelected() {
        filter();
    }

    @FXML
    void btnDelete(ActionEvent event) throws IOException {
        askForDeletion(tblViewComponent.getSelectionModel().getSelectedItem());
    }

    private void askForDeletion(Component selectedItem) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "Trykk ja for å slette", "Vil du slette ",
                selectedItem.getProductName());
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            deleteComponent(selectedItem);
            saveAll();
        }
    }

    /**
     * utility methods for controller
     */



/*
    private void openEditWindow(TableRow row) throws IOException {
        FXMLLoader loader = getFxmlLoader("editPopup.fxml");
        Popup popup = new Popup();
        Component c = (Component) row.getItem();
        EditPopupController popupController =
                loader.<EditPopupController>getController();
        popupController.initData(c, stage);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
        popup.getContent().add((Parent)loader.load());
       // popup.show();
        //handlePopUp(stage, c);
    }
*/
    FXMLLoader getFxmlLoader(String fxml) throws IOException {
        FXMLGetter fxmlGetter = new FXMLGetter();
        FXMLLoader loader = fxmlGetter.getFxmlLoader(fxml);
        return loader;
    }

    private ComponentRegister getComponentRegister() {
        return ContextModel.INSTANCE.getComponentRegister();
    }

    private void saveAll() throws IOException {
        fileHandling.saveAll();
    }

    private ObservableList<Component> getObservableRegister() {
        return ContextModel.INSTANCE.getComponentRegister().getObservableRegister();
    }

    private void initChoiceBoxes() {
        cbTypeFilter.setValue("Ingen filter");
        cbRecentFiles.setOnMouseClicked((MouseEvent event) -> updateRecentFiles());
        updateRecentFiles();
        cbType.setItems(componentTypes.getObservableTypeListName());
        cbTypeFilter.setItems(componentTypes.getObservableTypeListNameForFilter());
    }

    private void updateRecentFiles() {
        cbRecentFiles.setItems((ObservableList<String>) ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths());
    }

    void refreshTableAndSave() throws IOException {
        tblViewComponent.refresh();
        updateView();
        fileHandling.saveAll();
    }

    public void updateView() {
        getComponentRegister().attachTableView(tblViewComponent);
        tblViewComponent.refresh();
    }

    private void deleteComponent(Component selectedComp) {
        getComponentRegister().getRegister().remove(selectedComp);
        updateView();
    }

    private void registerComponent() {
        Component newComponent = registryComponentLogic.createComponentsFromGUIInputIFields();
        if (newComponent != null) {
            getComponentRegister().addComponent(newComponent);
            updateView();
        }
    }

    void disableGUI() {
        topLevelPane.setDisable(true);
    }

    void enableGUI() {
        topLevelPane.setDisable(false);
    }

    void openFileFromChooserWithThreadSleep() throws IOException {
        String chosenFile = FileUtility.getFilePathFromOpenJobjDialog(stage);
        if (chosenFile == null) {
            return;
        }

        openThread(chosenFile);
    }

    public void openThread(String chosenFile) {
        threadHandler.openInputThread(chosenFile);
    }

    private ObservableList<Component> filter() {
        if (cbTypeFilter.getValue().equals("Ingen filter")) {
            updateView();
            return getObservableRegister();
        }
        ObservableList<Component> result = getResultFromTypeFilter();
        tblViewComponent.setItems(Objects.requireNonNullElseGet(result, FXCollections::observableArrayList));
        return result;
    }

    private ObservableList<Component> getResultFromTypeFilter() {
        ObservableList<Component> result = null;
        String filterString = cbTypeFilter.getValue().toLowerCase();
        result = getComponentRegister().filterByProductType(filterString);
        return result;
    }

    private void setSearchedList() {
        FilteredList<Component> filteredData = Search.getFilteredList(getObservableRegister(), componentSearch.getText());
        SortedList<Component> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblViewComponent.comparatorProperty());
        tblViewComponent.setItems(sortedData);
    }

    public void init(SecondaryController secondaryController) {
        this.secondaryController = secondaryController;

    }

    public void setResultLabelTimed(String s) {

        //må gjøre setText i en egen tråd - fordi Timer er swing (som kjører på egen Swing thread (Event Dispatch
        // Thread))
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblComponentMsg.setText(s);
            }
        });

        Timer timer = new Timer(2000, e -> setResultLabelTimed(""));
        timer.setRepeats(false);
        timer.start();
    }
}