package org.programutvikling.gui.CustomPriceTableColumn;

import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;


public class CustomTextWrapCellFactory extends TableCell<Object, String> {
    private Text text = new Text();

    public CustomTextWrapCellFactory() {

    }


    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && null == item) {
            item = "";
        }
        //
        //har opprettet et Text object i klassen - binder dette til item-den faktiske verdien som er i cellen -
        // regner ut høyden på cellen ut i fra dette.
        //Text klassen oppfører seg mer som grafikk/bilde enn simplestring(?)
        setGraphic(text);
        //USE_COMPUTED_SIZE beregner høyden ut i fra computePrefHeight()
        setPrefHeight(Control.USE_COMPUTED_SIZE);
        text.wrappingWidthProperty().bind(widthProperty());
        textProperty().bind(itemProperty());
        text.textProperty().bind(itemProperty());
    }
}
