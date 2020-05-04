package org.programutvikling.gui.CustomTableColumn;

import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

//denne klassen er forøvrig gjenbrukbar for alle celler som har String
public class CustomTextWrapCellFactory extends TableCell<Object, String> {
    private Text text = new Text();

    public CustomTextWrapCellFactory() {

    }
    //the updateItem method can be overridden to allow for complete customisation of the cell.
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && null == item) {
            item = "";
            //setGraphic(null);
        }
        //
        //har opprettet et Text object i klassen - binder dette til item-den faktiske verdien som er i cellen -
        // regner ut høyden på cellen ut i fra dette.
        //Text klassen oppfører seg mer som grafikk/bilde enn simplestring(?)
        setGraphic(text);
        //USE_COMPUTED_SIZE beregner høyden ut i fra computePrefHeight()
        setPrefHeight(Control.USE_COMPUTED_SIZE);
        text.wrappingWidthProperty().bind(widthProperty());
        //item property representerer raw data-verdien i cellen - så vi binder text-objectet til faktiske verdien som
        // blir satt via componentlisten.
        textProperty().bind(itemProperty());
        text.textProperty().bind(itemProperty());
    }
}
