package org.programutvikling;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.programutvikling.gui.ContextModel;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;


    @Override
    public void start(Stage primaryStage) throws IOException {

        scene = new Scene(loadFXML("primary"));

        System.out.println(App.class.getResource("primary"));
        primaryStage.setScene(scene);
        primaryStage.show();

        //scene = buildUI(primaryStage);
        if (scene == null) throw new NullPointerException();
        scene.getRoot().applyCss();
    }

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

}