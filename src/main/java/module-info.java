/*module org.programutvikling {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.programutvikling.gui to javafx.fxml;
    exports org.programutvikling.gui;
}*/

module org.programutvikling {
        requires javafx.controls;
        requires javafx.fxml;
        requires org.junit.jupiter.api;
        requires org.junit.jupiter.engine;
        requires java.prefs;
        requires java.desktop;

    opens org.programutvikling to javafx.fxml;
        opens org.programutvikling.gui to javafx.fxml;
        opens org.programutvikling.user to javafx.fxml;
        opens org.programutvikling.component to javafx.base;
        opens org.programutvikling.computer to javafx.base;
        exports org.programutvikling;
        exports org.programutvikling.gui.customTextField;
        }