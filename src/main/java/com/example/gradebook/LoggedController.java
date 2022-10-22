package com.example.gradebook;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class LoggedController {
    @FXML private Button addStudent;
    @FXML private Button addGrade;
    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, String> nazwisko;
    @FXML private TableColumn<Student, String> imie;
    @FXML private TableColumn<Student, String> klasa;
    @FXML private TableColumn<Student, String> rokSzkolny;

    @FXML private void initialize(){
        DBTransaction db = new DBTransaction();
        if(db.ifStudentExist() == false){
            addGrade.setVisible(false);
            table.setVisible(false);
        }
        List<Student> students = db.getStudents_byId(0);
        for (Student data : students) {
            nazwisko.setCellValueFactory(c -> new SimpleStringProperty(data.getSurname()));
            imie.setCellValueFactory(c -> new SimpleStringProperty(data.getName()));
            klasa.setCellValueFactory(c -> new SimpleStringProperty(data.getStudentGroup()));
            rokSzkolny.setCellValueFactory(c -> new SimpleStringProperty(data.getSchoolYear()));
            table.getItems().add(data);
        }
    }
    public void onClickBtnAddStudent(ActionEvent actionEvent) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(HelloController.class.getResource("addStudent-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void onClickBtnAddGrade(ActionEvent actionEvent) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(HelloController.class.getResource("addGrade-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }
}
