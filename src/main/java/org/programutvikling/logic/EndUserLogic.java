package org.programutvikling.logic;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.computer.Computer;
import org.programutvikling.domain.computer.ComputerValidator;
import org.programutvikling.gui.CustomTableColumn.CustomTextWrapCellFactory;
import org.programutvikling.gui.EnduserController;
import org.programutvikling.gui.utility.Dialog;
import org.programutvikling.model.Model;
import org.programutvikling.model.ModelEndUser;

import java.util.ArrayList;

public class EndUserLogic {
    Tooltip tooltipEndUser = new Tooltip("Dobbeltklikk en rad for å legge til i handlekurven");

    private final ArrayList<TableView<Component>> tblViewList;
    private BorderPane borderPane;
    EnduserController endUserController;

    public EndUserLogic(EnduserController endUserController, BorderPane topLevelPaneEndUser,
                        ArrayList<TableView<Component>> tblViewList, ArrayList<TableColumn> tblColumnDescriptionList, ArrayList<TableColumn> tblColumnPriceList) {
        this.endUserController = endUserController;
        this.borderPane = topLevelPaneEndUser;
        this.tblViewList = tblViewList;
        initView();
    }

    private void initView() {
        setDblClickEvent();
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
                    "Handlekurven har allerede nok antall av typen:'" + component.getProductType()+"'." +
                            " Vil du erstatte den som ligger i handlekurven?", "");
            alert.showAndWait();
            //trykker ja = replace
            if (alert.getResult() == alert.getButtonTypes().get(0)) {
                replaceFirstComponentByType(component.getProductType(), component);
               // updateComputerListView();
            }
        }
    }

    public void replaceFirstComponentByType(String productType, Component component) {
        for (Component c : getComputer().getComponentRegister().getRegister()) {
            if (productType.equalsIgnoreCase(c.getProductType())) {
                int index = getComputer().getComponentRegister().getRegister().indexOf(c);
                getComputer().getComponentRegister().getRegister().set(index, component);
            }
        }
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
