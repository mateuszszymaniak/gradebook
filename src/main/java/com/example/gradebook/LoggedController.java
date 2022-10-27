package com.example.gradebook;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;


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
    private TableView<Student> studentTable;
    @FXML TableView<Pair<Grade, Student>> gradeTable;
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
    private List<Grade> grades;
    private  List<Pair<Grade, Student>> pairGradeStudent;
    private String title = "";
    private Tab studentTab;
    private Tab gradeTab;
    private String pressedTab = "student";

    DBTransaction db = new DBTransaction();



    @FXML
    private void initialize() {
        TableColumn<Student, String> nazwisko = new TableColumn<>("Nazwisko");
        TableColumn<Student, String> imie = new TableColumn<>("Imię");
        TableColumn<Student, String> klasa = new TableColumn<>("Klasa");
        TableColumn<Student, String> rokSzkolny = new TableColumn<>("Rok szkolny");


//        if (db.ifStudentExist() == false) {
//            editStudent.setVisible(false);
//            deleteStudent.setVisible(false);
//            addGrade.setVisible(false);
//            editGrade.setVisible(false);
//            deleteGrade.setVisible(false);
//            table.setVisible(false);
//        } else if (db.ifGradeExist() == false) {
//            editGrade.setVisible(false);
//            deleteGrade.setVisible(false);
//        }
        action.setVisible(false);

        students = db.getStudents_byId(0);
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("surname"));
        imie.setCellValueFactory(new PropertyValueFactory<>("name"));
        klasa.setCellValueFactory(new PropertyValueFactory<>("studentGroup"));
        rokSzkolny.setCellValueFactory(new PropertyValueFactory<>("schoolYear"));

        studentTable.getColumns().add(nazwisko);
        studentTable.getColumns().add(imie);
        studentTable.getColumns().add(klasa);
        studentTable.getColumns().add(rokSzkolny);

        studentTable.getItems().addAll(students);
        gradeTable.setVisible(false);
    }
    public void pressedGradesTab(Event event) {
        studentTable.setVisible(false);
        gradeTable.setVisible(true);
        this.pressedTab = "grade";
        System.out.println("Grades tab pressed");
    }
    public void pressedStudentsTab(Event event) {
        gradeTable.setVisible(false);
        studentTable.setVisible(true);
        this.pressedTab = "student";
        createGradeTable();
        System.out.println("Students tab pressed");
    }

    public void createGradeTable(){
        gradeTable.getItems().clear();
        gradeTable.getColumns().clear();

        TableColumn<Pair<Grade, Student>, String> surname = new TableColumn<>("Nazwisko");
        TableColumn<Pair<Grade, Student>, String> name = new TableColumn<>("Imię");
        TableColumn<Pair<Grade, Student>, String> subject = new TableColumn<>("Przedmiot");
        TableColumn<Pair<Grade, Student>, String> type = new TableColumn<>("Rodzdaj oceny");
        TableColumn<Pair<Grade, Student>, String> grade = new TableColumn<>("Ocena");
        TableColumn<Pair<Grade, Student>, String> comment = new TableColumn<>("Komentarz");

        ObservableList<String> names = FXCollections.observableArrayList();

        pairGradeStudent = db.getGrades_byId_withStudentName(0);

        subject.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getSubject()));
        type.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getType()));
        grade.setCellValueFactory(param -> new SimpleStringProperty(Double.toString(param.getValue().getKey().getGrade())));
        comment.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getComment()));
        name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getName()));
        surname.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSurname()));

        gradeTable.getColumns().add(surname);
        gradeTable.getColumns().add(name);
        gradeTable.getColumns().add(subject);
        gradeTable.getColumns().add(type);
        gradeTable.getColumns().add(grade);
        gradeTable.getColumns().add(comment);

        gradeTable.getItems().addAll(pairGradeStudent);

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
        studentTable.getItems().clear();
        studentTable.getColumns().clear();
        initialize();

        action.setVisible(true);
        addButtonToStudentGrid(actionEvent, title);
    }

    public void onClickBtnDeleteStudent(ActionEvent actionEvent) {
        this.title = "Usuń";
        studentTable.getItems().clear();
        studentTable.getColumns().clear();
        initialize();

        action.setVisible(true);
        addButtonToStudentGrid(actionEvent, title);
    }

    public void onClickBtnEditGrade(ActionEvent actionEvent) {
        this.title = "Edytuj";
        gradeTable.getItems().clear();
        gradeTable.getColumns().clear();
        createGradeTable();

        action.setVisible(true);
        addButtonToGradeGrid(actionEvent, title);
    }

    public void onClickBtnDeleteGrade(ActionEvent actionEvent) {
        this.title = "Usuń";
        gradeTable.getItems().clear();
        gradeTable.getColumns().clear();
        createGradeTable();

        action.setVisible(true);
        addButtonToGradeGrid(actionEvent, title);
    }

    public void onClickBtnBackup(ActionEvent actionEvent) {
        Parent fxmlLoader = null;
        try {
            fxmlLoader = FXMLLoader.load(HelloController.class.getResource("backup-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void setLoggedUser(String login) {
        System.out.println("Przechwycono: " + login);
        loggedUser = login;
    }

    public void refreshGrid(ActionEvent actionEvent) {
        switch (this.pressedTab){
            case "student": {
                switch (this.title){
                    case "Edytuj": onClickBtnEditStudent(actionEvent); break;
                    case "Usuń": onClickBtnDeleteStudent(actionEvent); break;
                    default: {
                        studentTable.getItems().clear();
                        studentTable.getColumns().clear();
                        initialize();
                    }
                }
                break;
            }
            case "grade": {
                switch (this.title){
                    case "Edytuj": onClickBtnEditGrade(actionEvent); break;
                    case "Usuń": onClickBtnDeleteGrade(actionEvent); break;
                    default: {
                        gradeTable.getItems().clear();
                        gradeTable.getColumns().clear();
                        createGradeTable();
                    }
                }
                break;
            }
        }
    }

    private void addButtonToStudentGrid(ActionEvent actionEvent, String title) {
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

        studentTable.getColumns().add(action2);
    }

    private void addButtonToGradeGrid(ActionEvent actionEvent, String title) {
        TableColumn<Pair<Grade, Student>, Void> action2 = new TableColumn("Akcja");

        Callback<TableColumn<Pair<Grade, Student>, Void>, TableCell<Pair<Grade, Student>, Void>> cellFactory = new Callback<TableColumn<Pair<Grade, Student>, Void>, TableCell<Pair<Grade, Student>, Void>>() {
            @Override
            public TableCell<Pair<Grade, Student>, Void> call(final TableColumn<Pair<Grade, Student>, Void> param) {
                final TableCell<Pair<Grade, Student>, Void> cell = new TableCell<Pair<Grade, Student>, Void>() {

                    private final Button btn = new Button(title);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Pair<Grade, Student> data = getTableView().getItems().get(getIndex());

                            if (title == "Edytuj") {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editGrade-view.fxml"));
                                try {
                                    root = fxmlLoader.load();
                                    EditGradeController editGradeController = fxmlLoader.getController();
                                    editGradeController.editChosenGrade(data.getKey().getId());
                                    System.out.println("Przekazano ocene: " + data.getKey().getGrade() + " studentId: " + data.getKey().getStudentId() + " id: " + data.getKey().getId());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                                stage.show();
                                System.out.println("selectedData: " + data.getKey().getId());
                            } else {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deleteGrade-view.fxml"));
                                try {
                                    root = fxmlLoader.load();
                                    DeleteGradeController deleteGradeController = fxmlLoader.getController();
                                    deleteGradeController.deleteGrade(data.getKey());
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

        gradeTable.getColumns().add(action2);
    }
}
