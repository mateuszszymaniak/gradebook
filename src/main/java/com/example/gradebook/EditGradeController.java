package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EditGradeController {
    @FXML
    private Button cancelButton;
    @FXML private TextField gradeText;
    @FXML private TextField typeText;
    @FXML private TextField subjectText;
    @FXML private TextField commentText;
    @FXML private Label errorMsg;
    private int gradeId, studentId, userId;

    private DBTransaction db = new DBTransaction();
    public void onClickBtnEdit(ActionEvent actionEvent) throws IOException {
        String grade = gradeText.getText().trim();
        String type = typeText.getText().trim();
        String subject = subjectText.getText().trim();
        String comment = commentText.getText().trim();

        if(!grade.equals("") && !type.equals("") && !subject.equals("")){
            db.editGrade(gradeId, Double.parseDouble(grade), subject, type, comment, studentId, userId);
            errorMsg.setVisible(false);

            closeWindow();

            Parent fxmlLoader = FXMLLoader.load(RegisterController.class.getResource("successEditGrade-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } else {
            errorMsg.setText("Formularz nie został uzupełniony!");
            errorMsg.setVisible(true);
        }
    }

    public void onClickBtnCancel(ActionEvent actionEvent) {
        closeWindow();
    }
    public void closeWindow(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void editChosenGrade(int id) {
        gradeId = id;
        System.out.println("Przechwycono ocene o id: " + id);
        loadData(id);
    }
    public void loadData(int id){
        System.out.println(id);
        List<Grade> grade = db.getGrades_byId(id);
        gradeText.setText(Double.toString(grade.get(0).getGrade()));
        typeText.setText(grade.get(0).getType());
        subjectText.setText(grade.get(0).getSubject());
        commentText.setText(grade.get(0).getComment());
        studentId = grade.get(0).getStudentId();
        userId = grade.get(0).getUserId();
        System.out.println(grade.get(0).getGrade() + "|" + grade.get(0).getType() + "|" + grade.get(0).getSubject() + "|" + grade.get(0).getComment());
    }
}
