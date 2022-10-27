package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteGradeController {
    private int gradeId;
    @FXML
    Button btnYes;
    @FXML Button btnNo;
    @FXML
    Label name;

    private DBTransaction db = new DBTransaction();

    public void onClickBtnYes(ActionEvent actionEvent) {
        db.deleteGrade(gradeId);
        Stage stage = (Stage) btnYes.getScene().getWindow();
        stage.close();
    }

    public void onClickBtnNo(ActionEvent actionEvent) {
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }
    public void deleteGrade(Grade grade) {
        gradeId = grade.getId();
        System.out.println("Przechwycono: " + Double.toString(grade.getGrade()) + " studentId: " + grade.getStudentId());
        loadData(gradeId);
    }
    public void loadData(int id) {
        name.setText(db.getGrades_byId_withStudentName(id).get(0).getValue().getName() + " " + db.getGrades_byId_withStudentName(id).get(0).getValue().getSurname() +
                " " + db.getGrades_byId(id).get(0).getGrade() + " " + db.getGrades_byId(id).get(0).getSubject() + "?");
    }

}
