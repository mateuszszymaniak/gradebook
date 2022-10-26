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
    @FXML TableView<Grade> gradeTable;
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
    private String title = "";
    private Tab studentTab;
    private Tab gradeTab;

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
        System.out.println("Grades tab pressed");
    }
    public void pressedStudentsTab(Event event) {
        gradeTable.setVisible(false);
        studentTable.setVisible(true);
        createGradeTable();
        System.out.println("Students tab pressed");
    }

    public void createGradeTable(){
        gradeTable.getItems().clear();
        gradeTable.getColumns().clear();

        TableColumn<Grade, String> fullname = new TableColumn<>("Uczeń");
        TableColumn<Grade, String> subject = new TableColumn<>("Przedmiot");
        TableColumn<Grade, String> type = new TableColumn<>("Rodzdaj oceny");
        TableColumn<Grade, String> grade = new TableColumn<>("Ocena");
        TableColumn<Grade, String> comment = new TableColumn<>("Komentarz");

        ObservableList<String> names = FXCollections.observableArrayList();

        grades = db.getGrades_byId(0);

        for(Grade grade1 : grades){
            //names.clear();
            names.add(db.getStudents_byId(grade1.getStudentId()).get(0).getName() + " " + db.getStudents_byId(grade1.getStudentId()).get(0).getSurname());
            //fullname.setCellValueFactory(data -> new SimpleStringProperty(db.getStudents_byId(grade1.getStudentId()).get(0).getName() + " " + db.getStudents_byId(grade1.getStudentId()).get(0).getSurname()));
            //gradeTable.setItems(FXCollections.observableArrayList(grades));
            //System.out.println(db.getStudents_byId(grade1.getStudentId()).get(0).getName() + " " + db.getStudents_byId(grade1.getStudentId()).get(0).getSurname());
            //fullname.getColumns().add(db.getStudents_byId(grade1.getStudentId()).get(0).getName() + " " + db.getStudents_byId(grade1.getStudentId()).get(0).getSurname());
            //grade1.setFullname(db.getStudentName(grade1.getStudentId()));
        }
        System.out.println(names);

        testAddNames(grades);

        //chyba najprostrze rozwiązanie dodawania imienia i nazwiska ucznia do tabelki
        //utworzyć w tabeli Grades nowe pole fullname, które będzie zawierało imię i nazwisko ucznia
        //i jakoś to uzupełnić (pozapytaniem?)
        //i wtedy można dane ucznia wyświetlić w tabelce odwolujac się do fullname
        //
        //po wprowadzeniu odpowiednich zmian poniższa linijka powinna prawidłowo działać :)
        //fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        gradeTable.getColumns().add(fullname);
        gradeTable.getColumns().add(subject);
        gradeTable.getColumns().add(type);
        gradeTable.getColumns().add(grade);
        gradeTable.getColumns().add(comment);

        gradeTable.getItems().addAll(grades);

    }
    public void testAddNames(List<Grade> grades){
        TableColumn<Grade, Void> action2 = new TableColumn("Uczeń");

        Callback<TableColumn<Grade, Void>, TableCell<Grade, Void>> cellFactory = new Callback<TableColumn<Grade, Void>, TableCell<Grade, Void>>() {
            @Override
            public TableCell<Grade, Void> call(final TableColumn<Grade, Void> param) {
                final TableCell<Grade, Void> cell = new TableCell<Grade, Void>() {

                    private final String btn = new String(title);

                    {
                        for(Grade grade1 : grades) {
                            //names.clear();
                            title = db.getStudents_byId(grade1.getStudentId()).get(0).getName() + " " + db.getStudents_byId(grade1.getStudentId()).get(0).getSurname();
                            //btn.setText(title);
                            System.out.println(title);
                        }
                        //Grade data = getTableView().getItems().get(getIndex());
                        //System.out.println("*************** TEST ***************");
                        //System.out.println(data);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(btn);
                        }
                    }
                };
                return cell;
            }
        };

        action2.setCellFactory(cellFactory);

        gradeTable.getColumns().add(action2);
    }
    public String studentFullname(int studentId){
        System.out.println("-> " + studentId);
        return "ok";
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
        addButtonToGrid(actionEvent, title);
    }

    public void onClickBtnDeleteStudent(ActionEvent actionEvent) {
        this.title = "Usuń";
        studentTable.getItems().clear();
        studentTable.getColumns().clear();
        initialize();

        action.setVisible(true);
        addButtonToGrid(actionEvent, title);
    }

    public void onClickBtnEditGrade(ActionEvent actionEvent) {

    }

    public void onClickBtnDeleteGrade(ActionEvent actionEvent) {

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
        switch (this.title){
            case "Edytuj": onClickBtnEditStudent(actionEvent); break;
            case "Usuń": onClickBtnDeleteStudent(actionEvent); break;
            default: {
                studentTable.getItems().clear();
                studentTable.getColumns().clear();
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

        studentTable.getColumns().add(action2);
    }
}
