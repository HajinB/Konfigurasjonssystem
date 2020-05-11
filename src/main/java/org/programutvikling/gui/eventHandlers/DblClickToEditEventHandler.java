package org.programutvikling.gui.eventHandlers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.component.ComponentRegister;

public class DblClickToEditEventHandler implements EventHandler<MouseEvent> {
    ComponentRegister register;
    TableView tableView;
    TableRow<? extends Component> tableRow;
    Component component;

    public DblClickToEditEventHandler(TableView tableView, ComponentRegister register){
        this.register = register;
        this.tableView = tableView;
    }

    public TableRow<? extends Component> getRow(){
        return tableRow;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public void handle(MouseEvent event) {
        if (register.getObservableRegister().size() > 0) {
            //tblViewComponent.getSelectionModel().setCellSelectionEnabled(false);
            TableRow<? extends Component> row;
            if (isDoubleClick(event)) {
                Node node = ((Node) event.getTarget()).getParent();
                if (node instanceof TableRow) {
                    this.tableRow = (TableRow<Component>) node;
                    System.out.println("handler");
                    component = (Component) tableRow.getItem();
                } else {
                    //hvis man trykker på tekst
                    this.tableRow = (TableRow<Component>) node.getParent();
                    System.out.println("handler");
                    component = (Component) tableRow.getItem();
                }
            }
        }
    }
            private boolean isDoubleClick(MouseEvent event) {
                return event.isPrimaryButtonDown() && event.getClickCount() == 2;
            }
}
