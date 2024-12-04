module xyz.zuner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens xyz.zuner to javafx.base, javafx.fxml, com.google.gson;
    opens xyz.zuner.obj to javafx.base, javafx.fxml, com.google.gson;
    opens xyz.zuner.handlers to javafx.base, javafx.fxml, com.google.gson;
    opens xyz.zuner.api to javafx.base, javafx.fxml, com.google.gson;
    exports xyz.zuner;
    exports xyz.zuner.handlers;
}