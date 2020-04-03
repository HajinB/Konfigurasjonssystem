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
        requires java.prefs;


        opens org.programutvikling to javafx.fxml;
        opens org.programutvikling.gui to javafx.fxml;
        opens org.programutvikling.user to javafx.fxml;
        opens org.programutvikling.component to javafx.base;
        exports org.programutvikling;
        }