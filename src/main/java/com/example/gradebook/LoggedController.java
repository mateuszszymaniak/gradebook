package com.example.gradebook;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class LoggedController {
    @FXML private Button addStudent;
    @FXML private Button addGrade;
    @FXML private Button deleteStudent;
    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, String> nazwisko;
    @FXML private TableColumn<Student, String> imie;
    @FXML private TableColumn<Student, String> klasa;
    @FXML private TableColumn<Student, String> rokSzkolny;
    @FXML private TableColumn<Student, Boolean> toDelete;

    @FXML private void initialize() {
        DBTransaction db = new DBTransaction();
        if (db.ifStudentExist() == false) {
            addGrade.setVisible(false);
            table.setVisible(false);
        }
        List<Student> students = db.getStudents_byId(0);
        for (Student data : students) {
            nazwisko.setCellValueFactory(c -> new SimpleStringProperty(data.getSurname()));
            imie.setCellValueFactory(c -> new SimpleStringProperty(data.getName()));
            klasa.setCellValueFactory(c -> new SimpleStringProperty(data.getStudentGroup()));
            rokSzkolny.setCellValueFactory(c -> new SimpleStringProperty(data.getSchoolYear()));
            //below line needs to be deleted when changing value of checkbox and deleting will works
            toDelete.setVisible(true);
            toDelete.setCellFactory(c -> new CheckBoxTableCell<>());
            //TODO add listener to changed value of checkbox
            //TODO deleting by selected rows maybe helps (https://www.youtube.com/watch?v=NFiMj6jszGM&ab_channel=java-techtutorials)
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

    public void onClickBtnDeleteStudent(ActionEvent actionEvent) {
        toDelete.setVisible(true);
    }
}
