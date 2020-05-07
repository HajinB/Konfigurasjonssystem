package org.programutvikling.gui.utility;

import javafx.fxml.FXMLLoader;
import org.programutvikling.gui.EnduserController;
import org.programutvikling.gui.TabComponentsController;
import org.programutvikling.gui.TabUsersController;
import org.programutvikling.gui.UserPopupController;

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
    public javafx.fxml.FXMLLoader getFxmlLoaderTabUser(String fxml) throws IOException {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/org/programutvikling/userPopup.fxml"));
        System.out.println("Loader kjørt userPopup.fxml");
        loader.setControllerFactory(type -> {
            if (type == UserPopupController.class) {
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
        if(fxml.equals("computerPopup.fxml") || fxml.equals("detailsPopup.fxml")){
            return getFxmlLoaderEndUser(fxml);
        }else if(fxml.equals("editPopup.fxml")){
            return getFxmlLoaderTabComponents(fxml);
        }else if(fxml.equals("userPopup.fxml")) {
            return getFxmlLoaderTabUser(fxml);
        }
        return null;
    }



    public static FXMLLoader fxmlLoaderFactory(String fxml) throws IOException {
        FXMLGetter fxmlGetter = new FXMLGetter();
        FXMLLoader loader = fxmlGetter.getFxmlLoader(fxml);
        return loader;
    }

}
