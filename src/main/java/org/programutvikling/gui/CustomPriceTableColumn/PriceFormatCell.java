package org.programutvikling.gui.CustomPriceTableColumn;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.programutvikling.component.Component;

public class PriceFormatCell extends TableCell<Object, Double> {

    public PriceFormatCell() {
    }

    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && null == item) {
            item = 0.0d;
        }

        // setter teksten som skal vises her
        setText(item == null ? "" : priceFormat(item));
    }

    private String priceFormat(Double item) {

        return String.format("%.2f",item ) + ",-";
    }

}
