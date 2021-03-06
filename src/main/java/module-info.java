module org.programutvikling {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires java.desktop;

    opens org.programutvikling to javafx.fxml;
    opens org.programutvikling.gui.controllers to javafx.fxml;
    opens org.programutvikling.model to javafx.fxml;
    opens org.programutvikling.domain.component to javafx.base;
    opens org.programutvikling.domain.computer to javafx.base;
    opens org.programutvikling.domain.user to javafx.base;

    exports org.programutvikling;
    exports org.programutvikling.gui.customTextField;
}