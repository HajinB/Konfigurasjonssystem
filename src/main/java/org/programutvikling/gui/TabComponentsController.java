package org.programutvikling.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;
import org.programutvikling.domain.component.ComponentTypes;
import org.programutvikling.gui.CustomTableColumn.CustomTextWrapCellFactory;
import org.programutvikling.gui.CustomTableColumn.PriceFormatCell;
import org.programutvikling.gui.utility.Converter;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.Search;
import org.programutvikling.gui.utility.WindowHandler;
import org.programutvikling.model.Model;
import org.programutvikling.model.TemporaryComponent;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class TabComponentsController {
    final Tooltip tooltip = new Tooltip("Dobbeltklikk en celle for å redigere");
    @FXML
    public Label lblComponentMsg;
    public Label lblMsgType;
    public Label lblMsgName;
    public Label lblMsgDescription;
    public Label lblMsgPrice;

    SecondaryController secondaryController;
    @FXML
    AnchorPane topLevelPane;
    ComponentTypes componentTypes = new ComponentTypes();
    ThreadHandler threadHandler;
    WindowHandler windowHandler = new WindowHandler();

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
    private TableColumn productPriceColumn, productDescriptionColumn;
    @FXML
    private ContextMenu cm;

    @FXML
    public void initialize() throws IOException {
        System.out.println("hei fra init tabcomponents");
        Task<Boolean> task = ThreadHandler.getTask();
        ThreadHandler.loadInThread(task);
        initChoiceBoxes();
        updateRecentFiles();
        registryComponentLogic = new RegistryComponentLogic(componentRegNode);
        threadHandler = new ThreadHandler(this);
        initTableView();
        initTextWrapCellFactory();
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
        productDescriptionColumn.setCellFactory(customTextWrapCellFactory);
    }

    private void initTableView() {
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
        registryComponentLogic.askForDeletion(tblViewComponent.getSelectionModel().getSelectedItem());
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
                if (getComponentRegister().getRegister().size() > 0) {
                    tblViewComponent.getSelectionModel().setCellSelectionEnabled(false);
                    //tblViewComponent.getSelectionModel().setCellSelectionEnabled(false);
                    TableRow<? extends Component> row;
                    if (isDoubleClick(event)) {
                        Node node = ((Node) event.getTarget()).getParent();
                        if (node instanceof TableRow) {
                            row = (TableRow<Component>) node;
                        } else {
                            //hvis man trykker på tekst
                            row = (TableRow<Component>) node.getParent();
                        }
                        try {
                            windowHandler.openEditWindow(row, componentRegNode);
                            updateView();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
        selectedCells.addListener((ListChangeListener) c -> {
            if (selectedCells.size() != 0) {
                TemporaryComponent.INSTANCE.setColumnIndex(selectedCells.get(0).getColumn());
            }
        });
    }

    /**
     * Search
     */
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

    @FXML
    private void filterByTypeSelected() {
        filter();
    }

    /**
     * Search end
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
        getConfirmationThenOpenRecent();
    }

    private void getConfirmationThenOpenRecent() throws IOException {
        String chosenFile = cbRecentFiles.getSelectionModel().getSelectedItem();
        openFileConfirmation(chosenFile);
    }

    private void openFileConfirmation(String chosenFile) throws IOException {
        Alert alert = Dialog.getOpenOption(
                "Åpne fil",
                "Legg til i listen eller overskriv. Under «Verktøy» kan du fjerne eventuelle duplikater.",
                "Vil du åpne '",
                cbRecentFiles.getSelectionModel().getSelectedItem() + "'?");
        alert.showAndWait();
        handleOpenOptions(chosenFile, alert);
    }

    void handleOpenOptions(String chosenFile, Alert alert) throws IOException {
        //button.get(2) == avbryt
        if (alert.getResult() == alert.getButtonTypes().get(2)) {
            return;
        }
        //button.get(1) == overskriv
        if (alert.getResult() == alert.getButtonTypes().get(1)) {
            overWriteList(chosenFile);
        }
        //button.get(0) == legg til
        if (alert.getResult() == alert.getButtonTypes().get(0)) {
            openThread(chosenFile);
            Model.INSTANCE.appendComponentRegisterIntoModel();
            //getComponentRegister().removeDuplicates();
            //refreshTableAndSave();
            updateView();
        }
    }

    public void overWriteList(String chosenFile) throws IOException {
        openThread(chosenFile);
        Model.INSTANCE.loadComponentRegisterIntoModel();
        updateView();
    }

    private boolean isFileSelectionEmpty(String chosenFile) {
        return chosenFile.equals("Åpne nylige filer");
    }

    @FXML
    void btnDelete(ActionEvent event) throws IOException {
        registryComponentLogic.askForDeletion(tblViewComponent.getSelectionModel().getSelectedItem());
    }

    private ComponentRegister getComponentRegister() {
        return Model.INSTANCE.getComponentRegister();
    }

    private ObservableList<Component> getObservableRegister() {
        return Model.INSTANCE.getComponentRegister().getObservableRegister();
    }

    //todo denne metoden bør/kan flyttes ut av controlleren - opprett id for disse og gjør det i RegistrtComponentLogic
    private void initChoiceBoxes() {
        cbTypeFilter.setValue("Ingen filter");
        Model.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths().add("AppFiles/Database/Backup/AppDataBackup" +
                ".jobj");
        cbRecentFiles.setOnMouseClicked((MouseEvent event) -> updateRecentFiles());
        updateRecentFiles();
        cbType.setItems(componentTypes.getObservableTypeListName());
        cbTypeFilter.setItems(componentTypes.getObservableTypeListNameForFilter());
    }

    private void updateRecentFiles() {
        cbRecentFiles.setItems((ObservableList<String>) Model.INSTANCE.getSavedPathRegister().getListOfSavedFilePaths());
    }

    public void updateView() {
        //hvis man attacher tableview på nytt ( mer enn en gang  ) - resettes cellfactories litt(?)
        getComponentRegister().attachTableView(tblViewComponent);
        //tblViewComponent.refresh();
    }

    void disableGUI() {
        topLevelPane.setDisable(true);
    }

    void enableGUI() {
        topLevelPane.setDisable(false);
    }

    public void openThread(String chosenFile) {
        threadHandler.openInputThread(chosenFile);
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