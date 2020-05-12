package org.programutvikling.gui.customViews;

import javafx.geometry.Insets;
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
        }
        //har opprettet et Text object i klassen - binder dette til item-den faktiske verdien som er i cellen -
        //regner ut høyden på cellen ut i fra dette.
        //Text klassen oppfører seg mer som grafikk/bilde enn simplestring.
        setPrefHeight(Control.USE_COMPUTED_SIZE);

        //setter bredden til texten til å være kolonnen sin bredde.
        //text.wrappingWidthProperty().bind(getTableColumn().widthProperty());
        text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(20));

        text.setLineSpacing(5);
        text.setStyle("-fx-padding: 5px 30px 5px 5px;"
                + "-fx-text-alignment:justify;");

        //her binder vi text til faktisk verdien i cellen
        textProperty().bind(itemProperty());
        text.textProperty().bind(itemProperty());

        //setter litt padding
        Insets newInset = new Insets((double) 0, 20, 4, 2);
        setPadding(newInset);



        this.setGraphic(text);
    }

}
