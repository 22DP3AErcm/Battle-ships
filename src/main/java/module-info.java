module org.openjfx {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}
