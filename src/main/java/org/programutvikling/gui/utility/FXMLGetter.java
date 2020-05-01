package org.programutvikling.gui.utility;

import javafx.fxml.FXMLLoader;
import org.programutvikling.gui.EnduserController;
import org.programutvikling.gui.TabComponentsController;

import java.io.IOException;

public class FXMLGetter {
    public javafx.fxml.FXMLLoader getFxmlLoaderTabComponents(String fxml) throws IOException {
        //FXMLLoader loader = App.getLoader("/org/programutvikling/editPopup");
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/org/programutvikling/" + fxml));
        //setControllerFactory er for å kunne instansiere en controller med verdier i konstruktøren
        loader.setControllerFactory(type -> {
            if (type == TabComponentsController.class) {
                return this;
            }
           /* if (type == EnduserController.class) {
                return this;
            } */else {
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

    public javafx.fxml.FXMLLoader getFxmlLoaderEndUser(String fxml) throws IOException {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/org/programutvikling/" + fxml));
        //setControllerFactory er for å kunne instansiere en controller med verdier i konstruktøren
        loader.setControllerFactory(type -> {
            if (type == EnduserController.class) {
                return this;
            }
           /* if (type == EnduserController.class) {
                return this;
            } */else {
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

    public FXMLLoader getFxmlLoader(String fxml) throws IOException {
        if(fxml.equals("computerPopup.fxml")){
            return getFxmlLoaderEndUser(fxml);
        } else if(fxml.equals("editPopup.fxml")){
            return getFxmlLoaderTabComponents(fxml);
        }
        return null;
    }
}
