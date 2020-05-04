package org.programutvikling.gui.CustomTableColumn;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

//denne klassen er forøvrig gjenbrukbar for alle celler som har String
public class CustomTextWrapCellFactory extends TableCell<Object, String> {
    private Text text = new Text();
    //private Label label = new Label();

    public CustomTextWrapCellFactory() {
        setStyle("-fx-padding-right: 20px !important;");
        setStyle("-fx-end-margin: 20px !important;;");
        text.setStyle("-fx-padding-right: 20px !important;");
        text.setStyle("-fx-end-margin-right: 20px !important;");
        setStyle("-fx-alignment: CENTER-RIGHT;");
    }
    //the updateItem method can be overridden to allow for complete customisation of the cell.
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && null == item) {
            item = "";
            //setGraphic(null);
        }

        //todo kan vi skrive om denne til å bruke label istedenfor txt?
        //har opprettet et Text object i klassen - binder dette til item-den faktiske verdien som er i cellen -
        // regner ut høyden på cellen ut i fra dette.
        //Text klassen oppfører seg mer som grafikk/bilde enn simplestring(?)
    /*
        text.setWrappingWidth(getPrefWidth()-10);
        setPrefHeight(Control.USE_COMPUTED_SIZE);
        text.wrappingWidthProperty().bind(widthProperty());
        text.setStyle("-fx-padding: 5px 30px 5px 5px;"
                + "-fx-text-alignment:justify;");
        textProperty().bind(itemProperty());
        text.textProperty().bind(itemProperty());


        //this.setPrefHeight(text.getLayoutBounds().getHeight()+10);


        setStyle("-fx-padding: 0 20 0 0 !important;");

        text.setStyle("-fx-padding: 0 20 0 0 !important;");
        setGraphic(text);
        */
        text.setStyle(" -fx-opacity: 1;" +
                " -fx-font-family: \"verdena\";" +
                " -fx-font-size: 12pt;" +
                " -fx-fill: #1398c8;" +
                " -fx-text-wrap: true;" +
                " -fx-font-weight: bold;;" +
                " -fx-padding: 5px 30px 5px 5px;" +
                " -fx-text-alignment:left;");
       // text.setWrappingWidth(getPrefWidth()-10);
        setPrefHeight(Control.USE_COMPUTED_SIZE);
        text.wrappingWidthProperty().bind(widthProperty());
        text.setStyle("-fx-padding: 5px 30px 5px 5px;"
                + "-fx-text-alignment:justify;");
        textProperty().bind(itemProperty());
        text.textProperty().bind(itemProperty());
        this.setGraphic(text);
        /*
        text.setWrappingWidth(getPrefWidth() - 35);
        this.setPrefHeight(text.getLayoutBounds().getHeight()+10);
        this.setGraphic(text);
        setGraphic(text);*/
    }
/*
    setGraphic(label);
    //USE_COMPUTED_SIZE beregner høyden ut i fra computePrefHeight()
    setPrefHeight(Control.USE_COMPUTED_SIZE);
    //label.bind(widthProperty());
        label.textProperty().bind(itemProperty());
    setStyle("-fx-padding-right: 20px;");

    //item property representerer raw data-verdien i cellen - så vi binder text-objectet til faktiske verdien som
    // blir satt via componentlisten.
        label.setWrapText(true);
    textProperty().bind(itemProperty());
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.textProperty().bind(itemProperty());*/
}
