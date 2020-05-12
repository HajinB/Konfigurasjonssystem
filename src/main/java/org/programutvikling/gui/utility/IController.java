package org.programutvikling.gui.utility;

import javafx.stage.Stage;
import org.programutvikling.domain.utility.Clickable;

//brukes for å abstrahere controllers, for å kunne åpne forskjellige fxml via en metode.
public interface IController {
    void initData(Clickable c, Stage stage);
}
