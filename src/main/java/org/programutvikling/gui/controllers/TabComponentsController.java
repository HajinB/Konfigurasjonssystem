package org.programutvikling.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.component.ComponentTypes;
import org.programutvikling.gui.customViews.CustomTextWrapCellFactory;
import org.programutvikling.gui.customViews.PriceFormatCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.Search;
import org.programutvikling.model.Model;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class TabComponentsController {
    final Tooltip tooltip = new Tooltip("Dobbeltklikk en celle for å redigere");
    @FXML
    AnchorPane topLevelPane;
    SuperUserController superUserController;
    ComponentTypes componentTypes = new ComponentTypes();
    ThreadHandler threadHandler;
    @FXML
    private Label lblComponentMsg, lblMsgType, lblMsgName, lblMsgDescription, lblMsgPrice;
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
    private TableColumn productPriceColumn, productDescriptionColumn;
    @FXML
    private ContextMenu cm;
    private RegistryComponentLogic registryComponentLogic;

    @FXML
    public void initialize() {
        initChoiceBoxes();
        updateRecentFiles();
        registryComponentLogic = new RegistryComponentLogic(componentRegNode, this, tblViewComponent);
        threadHandler = new ThreadHandler(this);
        initTableView();
        initTextWrapCellFactory();
        updateView();
    }

    /**
     * events
     */
    @FXML
    void btnAddComponent(ActionEvent event) {
        registryComponentLogic.registerComponent();
        updateView();
    }

    @FXML
    void btnOpenRecentFile(ActionEvent event) throws IOException {

        if ((cbRecentFiles.getSelectionModel().isEmpty())) {
            Dialog.showErrorDialog("velg en fil fra listen");
            return;
        }
        getConfirmationToOpenRecent();
    }

    @FXML
    void cmDeleteRow(ActionEvent event) throws IOException {
        registryComponentLogic.askForDeletion(tblViewComponent.getSelectionModel().getSelectedItem());
    }

    @FXML
    void search(KeyEvent event) {
        if (cbTypeFilter.getValue().equals("Ingen filter") || cbTypeFilter.getValue() == null) {
            setSearchedList();
        } else {
            tblViewComponent.setItems(getSearchedAndFilteredList());
        }
    }

    @FXML
    void dblClickTblRow(MouseEvent event) {
        registryComponentLogic.setTblViewEventHandler();
    }

    @FXML
    void btnDelete(ActionEvent event) throws IOException {
        registryComponentLogic.askForDeletion(tblViewComponent.getSelectionModel().getSelectedItem());
    }

    /**events slutt*/

    /**
     * utility views
     */
    private void initTextWrapCellFactory() {
        //oppretter en Callback, som gjør at vi kan sette en klasse som extender tablecell på
        // en kolonne i tableview
        Callback<TableColumn, TableCell> customTextWrapCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new CustomTextWrapCellFactory();
                    }
                };
        productDescriptionColumn.setCellFactory(customTextWrapCellFactory);
    }

    private void initTableView() {
        //tblViewComponent.setOnMouseClicked((MouseEvent event) -> tblViewComponent.sort());
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

    /***/

    ObservableList<Component> getSearchedAndFilteredList() {
        return Search.getFilteredList(getComponentRegister()
                .createListByType(cbTypeFilter.getValue().toLowerCase()), componentSearch.getText());
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
        result = getComponentRegister().createListByType(filterString);
        return result;
    }

    private void setSearchedList() {
        FilteredList<Component> filteredData = Search.getFilteredList(getObservableRegister(), componentSearch.getText());
        SortedList<Component> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblViewComponent.comparatorProperty());
        tblViewComponent.setItems(sortedData);
    }

    @FXML
    private void filterByTypeSelected() {
        filter();
    }

    private void getConfirmationToOpenRecent() throws IOException {
        String chosenFile = cbRecentFiles.getSelectionModel().getSelectedItem();
        if (getComponentRegister().getRegister().size() == 0) {
            registryComponentLogic.overWriteList(chosenFile);
        } else {
            openFileConfirmation(chosenFile);
        }
    }

    private void openFileConfirmation(String chosenFile) throws IOException {
        Alert alert = Dialog.getOpenOption(
                "Åpne fil",
                "Legg til i listen eller overskriv",
                "Vil du åpne '",
                cbRecentFiles.getSelectionModel().getSelectedItem() + "'?");
        alert.showAndWait();
        registryComponentLogic.handleOpenOptions(chosenFile, alert);
    }

    private ComponentRegister getComponentRegister() {
        return Model.INSTANCE.getComponentRegister();
    }

    private ObservableList<Component> getObservableRegister() {
        return Model.INSTANCE.getComponentRegister().getObservableRegister();
    }

    private void initChoiceBoxes() {
        cbTypeFilter.setValue("Ingen filter");
        Model.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths().add("AppFiles/Database/Backup/AppDataBackup" +
                ".jobj");
        cbType.setItems(componentTypes.getObservableTypeListName());
        cbType.setValue("");
        cbTypeFilter.setItems(componentTypes.getObservableTypeListNameForFilter());
    }

    void updateRecentFiles() {
        //cbRecentFiles.getItems().clear();
        Model.INSTANCE.getSavedPathRegister().removeDuplicates();
        cbRecentFiles.setItems((ObservableList<String>) Model.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths());
    }

    void updateView() {
        getComponentRegister().attachTableView(tblViewComponent);
    }

    void disableGUI() {
        topLevelPane.setDisable(true);
    }

    void enableGUI() {
        topLevelPane.setDisable(false);
    }

    void init(SuperUserController superUserController) {
        this.superUserController = superUserController;
    }

    void setResultLabelTimed(String s) {
        //må gjøre setText i en egen tråd - fordi Timer er swing (som kjører på egen Swing thread (Event Dispatch
        // Thread))
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblComponentMsg.setText(s);
            }
        });
        Timer timer = new Timer(3000, e -> setResultLabelTimed(""));
        timer.setRepeats(false);
        timer.start();
    }

    public void setLblComponentMsg(String s) {
        this.lblComponentMsg.setText(s);
    }

    public void setLblMsgDescription(String s) {
        this.lblMsgDescription.setText(s);
    }

    public void setLblMsgName(String s) {
        this.lblMsgName.setText(s);
    }

    public void setLblMsgType(String s) {
        this.lblMsgType.setText(s);
    }

    public void setLblMsgPrice(String s) {
        this.lblMsgPrice.setText(s);
    }

    public void clearLabels() {
        setLblMsgType("");
        setLblMsgDescription("");
        setLblMsgName("");
        setLblMsgPrice("");
    }

}