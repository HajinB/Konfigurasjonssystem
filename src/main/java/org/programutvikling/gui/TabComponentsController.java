package org.programutvikling.gui;

import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.programutvikling.component.Component;
import org.programutvikling.component.ComponentRegister;
import org.programutvikling.component.ComponentTypes;
import org.programutvikling.gui.utility.Converter;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FileUtility;
import org.programutvikling.gui.utility.Search;
import org.programutvikling.gui.utility.TemporaryComponent;

import java.io.IOException;
import java.util.Objects;

public class TabComponentsController {
    MainController mainController;
    final Tooltip tooltip = new Tooltip("Dobbeltklikk en rad for å redigere");
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
    public Label lblComponentMsg;
    @FXML
    private ChoiceBox<String> cbType;

    @FXML
    private ComboBox<String> cbRecentFiles;
    @FXML
    private TextField componentSearch;
    @FXML
    private ChoiceBox<String> cbTypeFilter;
    @FXML
    private TableView<Component> tblViewComponent;
    @FXML
    private TableColumn<TableView<Component>, Double> productPriceColumn;



    @FXML
    public void initialize() throws IOException {
        initChoiceBoxes();
        registryComponentLogic = new RegistryComponentLogic(componentRegNode);
        updateView();
        productPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(doubleStrConverter));
        threadHandler = new ThreadHandler(this);
        tblViewComponent.setOnMouseClicked((MouseEvent event) -> tblViewComponent.sort());
        tblViewComponent.setTooltip(tooltip);
    }

    @FXML
    void dblClickTblRow(MouseEvent event) {
        tblViewComponent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TableRow row;
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
    }

    @FXML
    void search(KeyEvent event) {
        if (cbTypeFilter.getValue().equals("Ingen filter") || cbTypeFilter.getValue() == null) {
            setSearchedList();
        } else {
            tblViewComponent.setItems(getSearchedAndFilteredList());
        }
    }

    ObservableList<Component> getSearchedAndFilteredList(){
       return Search.getFilteredList(getComponentRegister()
                .filterByProductType(cbTypeFilter.getValue().toLowerCase()), componentSearch.getText());
    }

    @FXML
    void btnAddFromFile(ActionEvent event) throws IOException {
        openFileFromChooserWithThreadSleep();
        ContextModel.INSTANCE.loadComponentRegisterIntoModel();
        refreshTableAndSave();
    }

    @FXML
    void btnAddComponent(ActionEvent event) throws IOException {
        registerComponent();
        updateView();
        fileHandling.saveAll();
    }

    @FXML
    void btnOpenRecentFile(ActionEvent event) throws IOException {
        Alert alert = org.programutvikling.gui.utility.Dialog.getConfirmationAlert(
                "Åpne nylig fil",
                "Vil du åpne den valgte filen, og dermed " +
                        "overskrive den nåværende listen?",
                "Vil du åpne ",
                cbRecentFiles.getSelectionModel().getSelectedItem());
        alert.showAndWait();
        String chosenFile = cbRecentFiles.getSelectionModel().getSelectedItem();
        if (isFileSelectionEmpty(chosenFile)) {
            System.out.println("velg en fil fra listen");
            return;
        }
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            threadHandler.openInputThread(chosenFile);
            ContextModel.INSTANCE.loadComponentRegisterIntoModel();

            refreshTableAndSave();
        }
    }

    private boolean isFileSelectionEmpty(String chosenFile) {
        return chosenFile.equals(cbRecentFiles.getPromptText());
    }

    @FXML
    private void filterByTypeSelected() {
        filter();
    }


    @FXML
    void btnDelete(ActionEvent event) throws IOException {
        Alert alert = Dialog.getConfirmationAlert("Vil du slette valgt rad?", "Trykk ja for å slette", "Vil du slette ",
                tblViewComponent.getSelectionModel().getSelectedItems().get(0).getProductName());
        alert.showAndWait();
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            Component selectedComp = tblViewComponent.getSelectionModel().getSelectedItem();
            deleteComponent(selectedComp);
            saveAll();
        }
    }

    /**utility methods for controller*/
    private void handlePopUp(Stage stage, Component c) {
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
        popupController.initData(c, stage);
        stage.show();
        handlePopUp(stage, c);
    }

    private FXMLLoader getFxmlLoader(String fxml) throws IOException {
        FXMLGetter fxmlGetter = new FXMLGetter();
        FXMLLoader loader = fxmlGetter.getFxmlLoader("editPopup.fxml");
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
        cbRecentFiles.setItems(ContextModel.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths());
    }

    private void refreshTableAndSave() throws IOException {
        tblViewComponent.refresh();
        updateView();
        fileHandling.saveAll();
    }
    public void updateView() {
        getComponentRegister().attachTableView(tblViewComponent);
        tblViewComponent.refresh();
    }
    private void deleteComponent(Component selectedComp) {
        getComponentRegister().removeComponent(selectedComp);
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
        String chosenFile = FileUtility.getFilePathFromOpenDialog(stage);
        if (chosenFile == null) {
            return;
        }
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

    public void init(MainController mainController) {
        this.mainController=mainController;

    }
}