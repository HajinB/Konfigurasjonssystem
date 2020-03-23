package org.programutvikling;

import java.io.IOException;
import javafx.fxml.FXML;

//https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/
//https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
