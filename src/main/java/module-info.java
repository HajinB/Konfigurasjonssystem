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
        opens org.programutvikling.user to javafx.fxml;
        opens org.programutvikling.component to javafx.fxml;
        exports org.programutvikling;
        }