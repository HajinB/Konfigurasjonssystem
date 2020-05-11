package org.programutvikling.gui.logic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerValidator;
import org.programutvikling.gui.controllers.CompletedComputerPopupController;
import org.programutvikling.gui.controllers.EnduserController;
import org.programutvikling.gui.controllers.OrderDetailsController;
import org.programutvikling.gui.customViews.CustomListViewCell;
import org.programutvikling.gui.customViews.CustomTextWrapCellFactory;
import org.programutvikling.gui.customViews.PriceFormatCell;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.gui.utility.FXMLGetter;
import org.programutvikling.model.ModelEndUser;

import java.io.IOException;
import java.util.ArrayList;

public class EndUserLogic {
    Tooltip tooltipEndUser = new Tooltip("Dobbeltklikk en rad for å legge til i handlekurven");
    EnduserController endUserController;
    OrderDetailsController orderDetailsController;
    private ArrayList<TableView<Component>> tblViewList;
    private ArrayList<TableColumn> tblColumnPriceList;
    private ArrayList<TableColumn> tblColumnDescriptionList;
    private ListView<Component> shoppingListView;
    private TableView<Computer> tblCompletedComputers;
    private BorderPane borderPane;

    public EndUserLogic(EnduserController endUserController, BorderPane topLevelPaneEndUser,
                        ArrayList<TableView<Component>> tblViewList, ArrayList<TableColumn> tblColumnDescriptionList,
                        ArrayList<TableColumn> tblColumnPriceList, ListView shoppingListView,
                        TableView<Computer> tblCompletedComputers) {
        this.endUserController = endUserController;
        this.borderPane = topLevelPaneEndUser;
        this.tblViewList = new ArrayList<>(tblViewList);
        this.tblColumnPriceList = new ArrayList<>(tblColumnPriceList);
        this.tblColumnDescriptionList = new ArrayList<>(tblColumnDescriptionList);
        this.shoppingListView = shoppingListView;
        this.tblCompletedComputers = tblCompletedComputers;
        initView();
    }

    public EndUserLogic(OrderDetailsController orderDetailsController,
                        TableColumn descriptionTblColumn) {
        this.orderDetailsController = orderDetailsController;
        initView();
    }

    private void initView() {
        ObjectProperty<TableRow<Component>> lastSelectedRow = new SimpleObjectProperty<>();
        setListenerToClearSelection(lastSelectedRow);
        setTblCellFactory();
        setDblClickEvent();
        initTextWrapCellFactory();
        shoppingListView.setCellFactory(lv -> new CustomListViewCell());
        setCompletedComputersEvents();
    }

    private void setCompletedComputersEvents() {

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
                        endUserController.updateTotalPrice();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    FXMLLoader getFxmlLoader(String fxml) throws IOException {
        FXMLGetter fxmlGetter = new FXMLGetter();
        FXMLLoader loader = fxmlGetter.getFxmlLoader(fxml);
        return loader;
    }

    //kan lett trekkes ut (se på tabcomponents for fasit)
    private void openDetailedView(TableRow row) throws IOException {
        //henter popup fxml
        System.out.println("her er vi i openDetailedView");
        FXMLLoader loader = getFxmlLoader("completedComputersPopup.fxml");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(
                new Scene((Pane) loader.load())     //for å loade inn fxml og sende parameter må man loade ikke-statisk
        );
        Computer c = (Computer) row.getItem();

        CompletedComputerPopupController completedComputerPopupController =
                loader.<CompletedComputerPopupController>getController();
        completedComputerPopupController.initData(c, stage, endUserController);
        stage.show();
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
        for (TableColumn tc : tblColumnDescriptionList) {
            tc.setCellFactory(customTextWrapCellFactory);
        }
    }

    private void setTblCellFactory() {

        //oppretter en cellfactory object for pris kolonnene
        Callback<TableColumn, TableCell> priceCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new PriceFormatCell();
                    }
                };
        for (TableColumn tc : tblColumnPriceList) {
            tc.setCellFactory(priceCellFactory);
        }
    }

    private void setDblClickEvent() {
        EventHandler<MouseEvent> tblViewDblClickEvent = new EventHandler<>() {
            public void handle(MouseEvent mouseEvent) {
                TableRow row;
                if (isDoubleClick(mouseEvent)) {
                    Node node = ((Node) mouseEvent.getTarget()).getParent();
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        //hvis man trykker på noe inne i cellen.
                        row = (TableRow) node.getParent();
                    }
                    Component c = (Component) row.getItem();
                    addComponentToComputer(c);
                    endUserController.updateTotalPrice();
                    // clearSelection();
                }
            }
        };
        for (TableView<Component> t : tblViewList) {
            //looper gjennom tableviews i tblviewlist for å legge til tooltip og eventhandler
            t.setTooltip(tooltipEndUser);
            t.setOnMousePressed(tblViewDblClickEvent);
        }
    }

    public void addComponentToComputer(Component component) {
        // validering aka sjekk type også run liste-test
        if (ComputerValidator.isComponentValidForList(component)) {
            getComputer().addComponent(component);
            //updateComputerListView();
        } else {
            Alert alert = Dialog.getConfirmationAlert("Erstatter komponent", "",
                    "Handlekurven har allerede nok antall av typen:'" + component.getProductType() + "'." +
                            " Vil du erstatte den som ligger i handlekurven?", "");
            alert.showAndWait();
            //trykker ja = replace
            if (alert.getResult() == alert.getButtonTypes().get(0)) {
                replaceFirstComponentByType(component);
                // updateComputerListView();
            }
        }
    }

    public void replaceFirstComponentByType(Component dblClickedComponent) {

        //går igjennom hele componentregisteret i computer for å finne en component å replace

        //starter loopen fra starten for å fjerne den som ble tatt bort først


        for (Component c : getComputer().getComponentRegister().getRegister()) {

            //finner en(den første den finner) komponent som har samme produkttype
            if (dblClickedComponent.getProductType().equalsIgnoreCase(c.getProductType())) {

                //finner indeksen til denne.
                int index = getComputer().getComponentRegister().getRegister().indexOf(c);

                System.out.println(index);
                getComputer().getComponentRegister().getRegister().set(index, dblClickedComponent);
                break;
            }
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
        borderPane.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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


    private Computer getComputer() {
        return ModelEndUser.INSTANCE.getComputer();
    }

    private boolean isDoubleClick(MouseEvent event) {
        return event.isPrimaryButtonDown() && event.getClickCount() == 2;
    }

    public void setListeners() {

  /*TableColumn t1 = (TableColumn) tblViewList.get(0).getColumns().get(1);
        TableColumn t2 = (TableColumn) tblViewList.get(1).getColumns().get(1);
        TableColumn t3 = (TableColumn) tblViewList.get(2).getColumns().get(1);

        TableColumn t4 = (TableColumn) tblViewList.get(3).getColumns().get(1);
        TableColumn t5 = (TableColumn) tblViewList.get(4).getColumns().get(1);*/
        TableColumn t6 = (TableColumn) borderPane.lookupAll("#memoryDescriptionColumn");
        TableColumn t7 = (TableColumn) borderPane.lookupAll("#cabinetDescriptionColumn");

        Callback<TableColumn, TableCell> customTextWrapCellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new CustomTextWrapCellFactory();
                    }
                };
       /* t1.setCellFactory(customTextWrapCellFactory);
        t2.setCellFactory(customTextWrapCellFactory);
        t3.setCellFactory(customTextWrapCellFactory);
        t4.setCellFactory(customTextWrapCellFactory);*/
        t6.setCellFactory(customTextWrapCellFactory);
        t7.setCellFactory(customTextWrapCellFactory);


    }
}
