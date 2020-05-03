package org.programutvikling.logic;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.programutvikling.domain.component.Component;
import org.programutvikling.gui.CustomPriceTableColumn.CustomTextWrapCellFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EndUserLogic {

    private final ArrayList<TableView<Component>> tblViewList;
    private BorderPane borderPane;

    public EndUserLogic(BorderPane borderPane, ArrayList<TableView<Component>> tblViewList) {
        this.borderPane = borderPane;
        this.tblViewList = tblViewList;
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
