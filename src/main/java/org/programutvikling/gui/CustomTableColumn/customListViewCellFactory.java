package org.programutvikling.gui.CustomTableColumn;


/***
public class customListViewCellFactory {
    private void setCellFactoryListView(TableView<Component> t) {
        t.setCellFactory(param -> new ListCell<Component>() {
            @Override
            protected void updateItem(Component c, boolean empty) {
                super.updateItem(c, empty);
                if (empty || c == null || c.getProductName() == null) {
                    setText("");
                } else {
                    //bruker cell factory for å sette toString i listviewen.
                    setText(c.getProductType() + "\n" + c.getProductName() + "\n" + String.format("%.2f", c.getProductPrice()) +
                            ",-");
                }
            }
        });
    }

}
*/