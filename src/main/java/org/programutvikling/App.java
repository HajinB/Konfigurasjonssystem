package org.programutvikling;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.programutvikling.gui.FileHandling;
import org.programutvikling.gui.utility.Dialog;

import java.io.IOException;
import java.util.Optional;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    FileHandling fileHandling = new FileHandling();

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
        //setRoot("org/programutvikling/primary.fxml");
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initOnExitHandler(primaryStage);
        scene = new Scene(loadFXML("primary"));
        System.out.println(App.class.getResource("primary"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);

        //scene = buildUI(primaryStage);
        if (scene == null) throw new NullPointerException();
        scene.getRoot().applyCss();
    }

    private void initOnExitHandler(Stage stage) {
        Platform.setImplicitExit(false);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Alert alert = Dialog.getConfirmationAlert("Avslutter..", "", "Vil du lagre endringene dine?", "");
                Optional<ButtonType> result = alert.showAndWait();
                //hvis avbryt blir trykket på
                if (result.get() == alert.getButtonTypes().get(2)) {
                    we.consume();
                    return;
                }

                if (result.get() == alert.getButtonTypes().get(0)) {
                    Thread thread = new Thread(() -> {
                        try {
                            FileHandling.saveBackup();
                            FileHandling.saveAllAdminFiles();
                            System.exit(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    thread.run();
                } else {
                    System.out.println("");
                    System.exit(0);
                }
            }
        });
    }

}