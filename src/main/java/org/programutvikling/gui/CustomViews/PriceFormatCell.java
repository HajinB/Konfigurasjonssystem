package org.programutvikling.gui.CustomViews;

import javafx.scene.control.TableCell;


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
        if(item % 1 == 0) {
            return String.format("%.0f",item);
        } else {
            return String.format("%.2f", item);
        }
    }

}
