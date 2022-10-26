package com.example.gradebook;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.util.List;

public class LoggedController {
    @FXML
    private Button addStudent;
    @FXML
    private Button editStudent;
    @FXML
    private Button deleteStudent;
    @FXML
    private Button addGrade;
    @FXML
    private Button editGrade;
    @FXML
    private Button deleteGrade;
    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student, String> nazwisko;
    @FXML
    private TableColumn<Student, String> imie;
    @FXML
    private TableColumn<Student, String> klasa;
    @FXML
    private TableColumn<Student, String> rokSzkolny;
    //@FXML private TableColumn<Student, Boolean> action;
    @FXML
    private Tab oceny;
    @FXML
    private Pane contentPane;
    @FXML
    private Button cancelButton;
    TableColumn<Student, String> action = new TableColumn<>("Akcja");
    private Parent root;
    private String loggedUser;
    private List<Student> students;
    private String title = "";

    @FXML
    private void initialize() {

        DBTransaction db = new DBTransaction();

        TableColumn<Student, String> nazwisko = new TableColumn<>("Nazwisko");
        TableColumn<Student, String> imie = new TableColumn<>("Imię");
        TableColumn<Student, String> klasa = new TableColumn<>("Klasa");
        TableColumn<Student, String> rokSzkolny = new TableColumn<>("Rok szkolny");


        if (db.ifStudentExist() == false) {
            editStudent.setVisible(false);
            deleteStudent.setVisible(false);
            addGrade.setVisible(false);
            editGrade.setVisible(false);
            deleteGrade.setVisible(false);
            table.setVisible(false);
        } else if (db.ifGradeExist() == false) {
            editGrade.setVisible(false);
            deleteGrade.setVisible(false);
        }
        action.setVisible(false);

        students = db.getStudents_byId(0);
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("surname"));
        imie.setCellValueFactory(new PropertyValueFactory<>("name"));
        klasa.setCellValueFactory(new PropertyValueFactory<>("studentGroup"));
        rokSzkolny.setCellValueFactory(new PropertyValueFactory<>("schoolYear"));

        table.getColumns().add(nazwisko);
        table.getColumns().add(imie);
        table.getColumns().add(klasa);
        table.getColumns().add(rokSzkolny);

        table.getItems().addAll(students);
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addGrade-view.fxml"));
        root = fxmlLoader.load();
        AddGradeController addGradeController = fxmlLoader.getController();
        addGradeController.setLoggedUser(loggedUser);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();

    }

    public void onClickBtnEditStudent(ActionEvent actionEvent) {
        this.title = "Edytuj";
        table.getItems().clear();
        table.getColumns().clear();
        initialize();

        action.setVisible(true);
        addButtonToGrid(actionEvent, title);
    }

    public void onClickBtnDeleteStudent(ActionEvent actionEvent) {
        this.title = "Usuń";
        table.getItems().clear();
        table.getColumns().clear();
        initialize();

        action.setVisible(true);
        addButtonToGrid(actionEvent, title);
    }

    public void onClickBtnEditGrade(ActionEvent actionEvent) {

    }

    public void onClickBtnDeleteGrade(ActionEvent actionEvent) {

    }

    public void setLoggedUser(String login) {
        System.out.println("Przechwycono: " + login);
        loggedUser = login;
    }

    public void refreshGrid(ActionEvent actionEvent) {
        switch (this.title){
            case "Edytuj": onClickBtnEditStudent(actionEvent); break;
            case "Usuń": onClickBtnDeleteStudent(actionEvent); break;
            default: {
                table.getItems().clear();
                table.getColumns().clear();
                initialize();
            }
        }
    }

    private void addButtonToGrid(ActionEvent actionEvent, String title) {
        TableColumn<Student, Void> action2 = new TableColumn("Akcja");

        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = new Callback<TableColumn<Student, Void>, TableCell<Student, Void>>() {
            @Override
            public TableCell<Student, Void> call(final TableColumn<Student, Void> param) {
                final TableCell<Student, Void> cell = new TableCell<Student, Void>() {

                    private final Button btn = new Button(title);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Student data = getTableView().getItems().get(getIndex());

                            if (title == "Edytuj") {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editStudent-view.fxml"));
                                try {
                                    root = fxmlLoader.load();
                                    EditStudentController editStudentController = fxmlLoader.getController();
                                    editStudentController.editChosenStudent(data.getId());
                                    System.out.println("Przekazano ucznia: " + data.getName() + " " + data.getSurname() + " id: " + data.getId());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                                stage.show();
                                System.out.println("selectedData: " + data.getId());
                            } else {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deleteStudent-view.fxml"));
                                try {
                                    root = fxmlLoader.load();
                                    DeleteStudentController deleteStudentController = fxmlLoader.getController();
                                    deleteStudentController.deleteStudent(data);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                                stage.show();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        action2.setCellFactory(cellFactory);

        table.getColumns().add(action2);
    }
}
