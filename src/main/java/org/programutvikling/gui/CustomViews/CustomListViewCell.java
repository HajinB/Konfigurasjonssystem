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

    @Override
    protected void updateItem(Component item, boolean empty) {
        super.updateItem(item, empty);

        Pane pane = null;
        if (!empty) {
            pane = new Pane();

            final Text productName = new Text(item.getProductName());

            productName.setTextOrigin(VPos.TOP);
            productName.relocate(0, 0);


            setMinWidth(getWidth());
            setMaxWidth(getWidth());
            setPrefWidth(getWidth()-10);


            setWrapText(true);
            final Text productDescription = new Text("\n"+item.getProductDescription());
            productDescription.wrappingWidthProperty().bind(widthProperty().subtract(50));


            productDescription.setTextOrigin(VPos.TOP);
            final double em = productDescription.getLayoutBounds().getHeight();
            productDescription.relocate(0, 0);

            final Text productPrice = new Text("\n"+"\n"+String.format("%.0f",
                    item.getProductPrice()) + " kr");
            productPrice.setTextOrigin(VPos.BOTTOM);
            final double width = productPrice.getLayoutBounds().getWidth();
            final double height = productDescription.getLayoutBounds().getHeight();
            productPrice.relocate(7.1 * em - width, productDescription.getY());
            pane.getChildren().addAll(productName, productDescription, productPrice);
        }

        setText("");
        setGraphic(pane);
    }
}



