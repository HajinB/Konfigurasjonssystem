module org.programutvikling {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.programutvikling to javafx.fxml;
    exports org.programutvikling;
}