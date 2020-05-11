package org.programutvikling.gui.utility;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class FXMLGetter {
    javafx.fxml.FXMLLoader getFxmlLoaderAny(String fxml){
        //FXMLLoader loader = App.getLoader("/org/programutvikling/editPopup");
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/org/programutvikling/" + fxml));
        //setControllerFactory er for å kunne instansiere en controller med verdier i konstruktøren
        loader.setControllerFactory(type -> {
                try {
                    return type.newInstance();
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        });
        return loader;
    }

    public FXMLLoader getFxmlLoader(String fxml) throws IOException {
       return getFxmlLoaderAny(fxml);
    }


    public static FXMLLoader fxmlLoaderFactory(String fxml) throws IOException {
        FXMLGetter fxmlGetter = new FXMLGetter();
        FXMLLoader loader = fxmlGetter.getFxmlLoader(fxml);
        return loader;
    }

}
