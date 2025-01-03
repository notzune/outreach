module xyz.zuner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires org.testfx;
    requires org.testfx.junit5;
    requires java.desktop;
    requires junit;
    requires javafx.swing;

    opens xyz.zuner to javafx.base, javafx.fxml, com.google.gson, javafx.swing;
    opens xyz.zuner.obj to javafx.base, javafx.fxml, com.google.gson, javafx.swing;
    opens xyz.zuner.handlers to javafx.base, javafx.fxml, com.google.gson, javafx.swing;
    opens xyz.zuner.api to javafx.base, javafx.fxml, com.google.gson, javafx.swing;
    exports xyz.zuner;
    exports xyz.zuner.handlers;
}