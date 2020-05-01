package org.programutvikling.gui.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.programutvikling.domain.component.Component;

public class Search {

    public static FilteredList<Component> getFilteredList(ObservableList<Component> list, String query) {

        ObservableList<Component> temp = FXCollections.observableArrayList();
        query = query.toLowerCase();

        for (Component k : list) {
            if (k.getProductDescription().toLowerCase().contains(query) ||
                    k.getProductType().toLowerCase().contains(query) ||
                    Double.toString(k.getProductPrice()).contains(query) ||
                    k.getProductType().contains(query)) {
                temp.add(k);
            }
        }
        FilteredList<Component> result = new FilteredList<>(temp);
        return result;
    }
}
