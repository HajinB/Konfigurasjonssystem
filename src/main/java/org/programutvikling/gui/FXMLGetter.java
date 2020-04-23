package org.programutvikling.gui;

import java.io.IOException;

public class FXMLGetter {
    public javafx.fxml.FXMLLoader getFxmlLoader(String fxml) throws IOException {
        //FXMLLoader loader = App.getLoader("/org/programutvikling/editPopup");
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/org/programutvikling/"+fxml));
        //setControllerFactory er for å kunne instansiere en controller med verdier i konstruktøren
        loader.setControllerFactory(type -> {
            if (type == TabComponentsController.class) {
                return this;
            } else {
                try {
                    return type.newInstance();
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return loader;
    }
}
