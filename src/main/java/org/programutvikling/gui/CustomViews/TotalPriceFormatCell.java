package org.programutvikling.gui.CustomViews;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.programutvikling.domain.computer.Computer;

public class TotalPriceFormatCell <T> implements Callback<TableColumn<T, Computer>, TableCell<T, Computer>> {
        //metode for å sette kalkulert verdi i en Listview
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
                    if(item.calculatePrice() % 1 == 0) {
                        setText(String.format("%.0f", item.calculatePrice()));
                    } else {
                        setText(String.format("%.2f", item.calculatePrice()));
                    }
                }

            };
        }

    }
