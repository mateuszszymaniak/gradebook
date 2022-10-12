module com.example.gradebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires org.mapstruct.processor;

    opens com.example.gradebook to javafx.fxml;
    exports com.example.gradebook;
}