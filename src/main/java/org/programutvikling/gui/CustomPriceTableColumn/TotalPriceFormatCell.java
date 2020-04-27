package org.programutvikling.gui.CustomPriceTableColumn;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.programutvikling.computer.Computer;

public class TotalPriceFormatCell <T> implements Callback<TableColumn<T, Computer>, TableCell<T, Computer>> {

        @Override
        public TableCell<T, Computer> call(TableColumn<T, Computer> col) {
            return new TableCell<T, Computer>() {

                @Override
                protected void updateItem(Computer item, boolean empty) {
                    super.updateItem(item, empty);
                    if ((item == null) || empty) {
                        setText(null);
                        return;
                    }

                    setText(Double.toString(item.calculatePrice()) + ",-");
                }

            };
        }

    }
