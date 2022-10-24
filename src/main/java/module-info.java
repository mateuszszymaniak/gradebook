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
    requires java.sql;
    requires static lombok;
    requires org.mapstruct;
    requires org.mapstruct.processor;
    requires mysql.connector.java;
    requires org.apache.commons.collections4;
    
    opens com.example.gradebook to javafx.fxml;
    exports com.example.gradebook;
}