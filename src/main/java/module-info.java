module com.example.gestiontareas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires javafx.media;

    opens com.example.gestiontareas to javafx.fxml;
    exports com.example.gestiontareas;
}