package org.programutvikling.gui.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.programutvikling.domain.user.User;

public class UserSearch {

    public static FilteredList<User> getFilteredList(ObservableList<User> list, String query) {

        ObservableList<User> temp = FXCollections.observableArrayList();
        query = query.toLowerCase();
        
        for (User user : list) {
            if (user.getUsername().toLowerCase().contains(query) ||
                    user.getName().toLowerCase().contains(query) ||
                    user.getEmail().toLowerCase().contains(query) ||
                    user.getAddress().toLowerCase().contains(query) ||
                    user.getZip().toLowerCase().contains(query) ||
                    user.getCity().toLowerCase().contains(query)
            ) {
                temp.add(user);
            }
        }
        FilteredList<User> result = new FilteredList<>(temp);
        return result;
    }
}
