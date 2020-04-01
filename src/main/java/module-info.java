/*module org.programutvikling {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.programutvikling.gui to javafx.fxml;
    exports org.programutvikling.gui;
}*/

module org.programutvikling {
        requires javafx.controls;
        requires javafx.fxml;

        opens org.programutvikling to javafx.fxml;
        opens org.programutvikling.gui to javafx.fxml;
        opens org.programutvikling.bruker to javafx.fxml;
        opens org.programutvikling.komponent to javafx.fxml;
        exports org.programutvikling;
        }