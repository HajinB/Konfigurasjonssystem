package org.programutvikling.gui.utility;

import javafx.stage.Stage;
import org.programutvikling.domain.component.Component;
import org.programutvikling.domain.user.User;
import org.programutvikling.domain.utility.Clickable;
import org.programutvikling.model.TemporaryUser;

public interface IController {
    void initData(Clickable c, Stage stage, int columnIndex);
}
