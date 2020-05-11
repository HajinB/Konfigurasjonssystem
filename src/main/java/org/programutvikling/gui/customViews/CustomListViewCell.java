package org.programutvikling.gui.customViews;


import javafx.scene.control.ListCell;
import org.programutvikling.domain.component.Component;

public class CustomListViewCell extends ListCell<Component> {

    public CustomListViewCell() {

    }
    public void updateItem(Component component, boolean empty) {
        super.updateItem(component, empty);

        if (component == null) {
            setText(null);
            setGraphic(null);
        } else if(component.getProductPrice() % 1 == 0) {
            this.setWrapText(true);
            setText(component.getProductType() + "\n" + component.getProductName() + "\n" + String.format("%.0f",
                    component.getProductPrice()));
        } else {
            this.setWrapText(true);
            setText(component.getProductType() + "\n" + component.getProductName() + "\n" + String.format("%.2f",
                    component.getProductPrice()));
        }

        //metode for å sette kalkulert verdi i en Listview

    }
}



