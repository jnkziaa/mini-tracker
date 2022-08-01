module com.example.gs_airlinetrackerappmain {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires gson;
    requires commons.lang;

    opens com.genspark.Main to javafx.fxml;
    exports com.genspark.Main;
    exports Controller;
    opens Controller to javafx.fxml;
}