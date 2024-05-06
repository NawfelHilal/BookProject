module com.example.bookproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.bookproject to javafx.fxml;
    exports com.example.bookproject;
}