package org.programutvikling.gui;

//todo sett opp nested fxml controllers? altså sånn at user-tabben får en egen fxml? man kan dele opp controlleren
// veldig lett da (?)   ((main controlleren blir så full)) - how to avoid - beste er å enten hente input fra en annen
// class eller sende input fra controller til andre steder på en bra måte.

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.programutvikling.gui.utility.UserSearch;
import org.programutvikling.gui.utility.UserWindowHandler;
import org.programutvikling.model.Model;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.user.UserRegister;
import org.programutvikling.model.TemporaryUser;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.util.stream.Collectors.toCollection;

public class TabUsersController implements Initializable {

    UserWindowHandler userWindowHandler = new UserWindowHandler();
    final Tooltip tooltip = new Tooltip("Dobbeltklikk en celle for å redigere");

    @FXML
    private CheckBox cbAdmin;
    @FXML
    private GridPane userReg;
    @FXML
    private Label lblUserMsg;
    @FXML
    private TextField userSearch;
    @FXML
    private ChoiceBox<String> cbAdminFilter;
    @FXML
    private TableView<User> tblViewUser;

    private RegistryUserLogic registryUserLogic;

    public void btnAddUser(ActionEvent actionEvent) {
    registryUserLogic.registerUser();
    updateView();
    }

    public void btnDeleteUser(ActionEvent actionEvent) throws IOException {
        registryUserLogic.askForDeletion(tblViewUser.getSelectionModel().getSelectedItem());
        updateView();
    }

    public void cbAdmin(ActionEvent event) {

    }

    public void userName(TableColumn.CellEditEvent cellEditEvent) {

    }
    public void userPassword(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userMail(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userAddress(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userZipCode(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void userCity(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void updateView() {
        getUserRegister().attachTableView(tblViewUser);
    }

    @FXML
    void dblClickTblRow(MouseEvent event) {
        handlePopup();
    }

    private void handlePopup() {
        /**detecter tablerow, for å hente ut users*/
        tblViewUser.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getUserRegister().getRegister().size() > 0) {
                    tblViewUser.getSelectionModel().setCellSelectionEnabled(false);
                    TableRow<? extends User> row;
                    if (isDoubleClick(event)) {
                        Node node = ((Node) event.getTarget()).getParent();
                        if (node instanceof TableRow) {
                            row = (TableRow<User>) node;
                        } else {
                            //hvis man trykker på tekst
                            row = (TableRow<User>) node.getParent();
                        }
                        try {
                            userWindowHandler.openEditWindow(row, userReg);
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
        final ObservableList<TablePosition> selectedCells = tblViewUser.getSelectionModel().getSelectedCells();
        //gjør det mulig å detecte cell på første klikk:
        tblViewUser.getSelectionModel().setCellSelectionEnabled(true);
        selectedCells.addListener((ListChangeListener) u -> {
            if (selectedCells.size() != 0) {
                TemporaryUser.INSTANCE.setColumnIndex(selectedCells.get(0).getColumn());
            }
        });
    }

    @FXML
    void search(KeyEvent event) {
        if(cbAdminFilter.getValue().equals("Ingen filter") || cbAdminFilter.getValue() == null) {
            setSearchedList();
            System.out.println(cbAdminFilter.getValue());
        } else {
            tblViewUser.setItems(getSearchedAndFilteredList());
        }
    }

    ObservableList<User> getSearchedAndFilteredList() {
        return UserSearch.getFilteredList(getUserRegister()
                .filterByAdmin(cbAdminFilter.getValue()), userSearch.getText());
    }


    private void setSearchedList() {
        FilteredList<User> filteredData = UserSearch.getFilteredList(getUserRegister().getRegister(), userSearch.getText());
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblViewUser.comparatorProperty());
        tblViewUser.setItems(sortedData);
    }

    @FXML
    private void filterByAdmin() {
        filter();
    }

    private ObservableList<User> filter() {
        if (cbAdminFilter.getValue().equals("Ingen filter")) {
            updateView();
            return getUserRegister().getRegister();
        }
        ObservableList<User> result = getResultFromTypeFilter();
        tblViewUser.setItems(Objects.requireNonNullElseGet(result, FXCollections::observableArrayList));
        return result;
    }

    private ObservableList<User> getResultFromTypeFilter() {
        ObservableList<User> result = null;
        String filterString = cbAdminFilter.getValue();
        result = getUserRegister().filterByAdmin(filterString);
        return result;
    }

    public UserRegister getUserRegister(){
        return Model.INSTANCE.getUserRegister();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //user lagd av inputfields
        //getUserRegister().addBruker();
        registryUserLogic = new RegistryUserLogic(userReg, this);
        initChoiceBox();
        updateView();
        tblViewUser.setOnMouseClicked((MouseEvent event) -> tblViewUser.sort());
        tblViewUser.setTooltip(tooltip);
    }

    private void initChoiceBox() {
        cbAdminFilter.setValue("Ingen filter");
        cbAdminFilter.getItems().addAll("Ingen filter","Admin","Bruker");
    }

}
