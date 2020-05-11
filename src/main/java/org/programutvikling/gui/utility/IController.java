package org.programutvikling.gui.utility;

import javafx.stage.Stage;
import org.programutvikling.domain.utility.Clickable;

public interface IController {
    void initData(Clickable c, Stage stage, int columnIndex);
}
