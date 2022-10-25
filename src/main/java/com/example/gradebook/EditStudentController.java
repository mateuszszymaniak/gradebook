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

public class EditStudentController {

    @FXML private Button cancelButton;
    @FXML private TextField surnameText;
    @FXML private TextField nameText;
    @FXML private TextField studentGroupText;
    @FXML private TextField schoolYearText;
    @FXML private Label errorMsg;
    private int studentId;

    private DBTransaction db = new DBTransaction();
    public void onClickBtnEdit(ActionEvent actionEvent) throws IOException {
        String surname = surnameText.getText().trim();
        String name = nameText.getText().trim();
        String studentGroup = studentGroupText.getText().trim();
        String schoolYear = schoolYearText.getText().trim();

        if(!surname.equals("") && !name.equals("") && !studentGroup.equals("") && !schoolYear.equals("")){
            db.editStudent(studentId, surname, name, studentGroup, schoolYear);
            errorMsg.setVisible(false);

            closeWindow();

            Parent fxmlLoader = FXMLLoader.load(RegisterController.class.getResource("successEditStudent-view.fxml"));
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
    public void editChosenStudent(int id) {
        studentId = id;
        System.out.println("Przechwycono ucznia o id: " + id);
        loadData(id);
    }
    public void loadData(int id){
        System.out.println(id);
        List<Student> student = db.getStudents_byId(id);
        surnameText.setText(student.get(0).getSurname());
        nameText.setText(student.get(0).getName());
        studentGroupText.setText(student.get(0).getStudentGroup());
        schoolYearText.setText(student.get(0).getSchoolYear());
        System.out.println(student.get(0).getSurname() + "|" + student.get(0).getName() + "|" + student.get(0).getStudentGroup() + "|" + student.get(0).getSchoolYear());
    }

}
