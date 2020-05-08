package org.programutvikling.gui.CustomViews;


import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.programutvikling.domain.component.Component;

public class CustomListViewCell extends ListCell<Component> {

    public CustomListViewCell() {

    }
    public void updateItem(Component component, boolean empty) {
        super.updateItem(component, empty);

        if (component == null) {
            setText(null);
            setGraphic(null);
        } else {

            this.setWrapText(true);
            setText(component.getProductType() + "\n" + component.getProductName() + "\n" + String.format("%.0f",
                    component.getProductPrice()) + " kr");


        }

        //metode for å sette kalkulert verdi i en Listview

    }
}



