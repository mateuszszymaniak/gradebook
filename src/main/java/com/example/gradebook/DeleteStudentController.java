package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteStudentController {
    private int studentId;
    @FXML Button btnYes;
    @FXML Button btnNo;
    @FXML Label name;
    private DBTransaction db = new DBTransaction();

    public void onClickBtnYes(ActionEvent actionEvent) {
        db.deleteStudent(studentId);
        Stage stage = (Stage) btnYes.getScene().getWindow();
        stage.close();
    }
    public void onClickBtnNo(ActionEvent actionEvent) {
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }
    public void deleteStudent(Student student) {
        studentId = student.getId();
        System.out.println("Przechwycono: " + student.getName() + " " + student.getSurname());
        loadData(studentId);
    }
    public void loadData(int id) {
        name.setText(db.getStudents_byId(id).get(0).getName() + " " + db.getStudents_byId(id).get(0).getSurname() + "?");
    }
}
