package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;

public class AddGradeController {
    @FXML private ChoiceBox studentName;
    @FXML private ChoiceBox subject;
    @FXML private ChoiceBox gradeType;
    @FXML private TextField grade;
    @FXML private TextField comment;
    @FXML private Button addGrade;
    @FXML private Button cancelButton;
    @FXML private Label errorMsg;
    BidiMap<Integer, String> students = new TreeBidiMap<>();
    private Integer loggerUserId;
    private Integer studentIdValue;
    private DBTransaction db = new DBTransaction();
    @FXML private void initialize(){
        List<Student> downloadStudents = db.getStudents_byId(0);

        for (Student data : downloadStudents){
            students.put(data.getId(), data.getName() + " " + data.getSurname());
        }
        for (String name : students.values()) {
            studentName.getItems().add(name);
        }
        //studentName.getSelectionModel().select(0);

        List<String> subjectList = new ArrayList<>();
        subjectList.add("Matematyka");
        subjectList.add("Język angielski");
        subjectList.add("Informatyka");
        for (String subName : subjectList){
            subject.getItems().add(subName);
        }

        List<String> gradeTypeList = new ArrayList<>();
        gradeTypeList.add("Sprawdzian");
        gradeTypeList.add("Kartkówka");
        gradeTypeList.add("Odpowiedź ustna");
        for(String grade : gradeTypeList){
            gradeType.getItems().add(grade);
        }
    }

    public void onClickBtnAddGrade(ActionEvent actionEvent) {
        DBTransaction db = new DBTransaction();
        String error = "";
        String gradeTypeValue = (String) gradeType.getValue();
        String subjectValue = (String) subject.getValue();
        String gradeValue = grade.getText().trim();
        String commentValue = comment.getText().trim();

        try {
            studentIdValue = students.getKey(studentName.getValue());
        } catch (Exception e) {
            System.out.println("Nie wybrano ucznia");
        }

        if (gradeTypeValue != null && subjectValue != null && !gradeValue.equals("") && studentIdValue != null) {
            db.addGrade(Double.parseDouble(gradeValue), subjectValue, gradeTypeValue, commentValue, studentIdValue, loggerUserId);
            System.out.println("ok");
            errorMsg.setVisible(false);
        } else if (studentIdValue == null){
            error += "Nie podano ucznia!\n";
            errorMsg.setText(error);
            errorMsg.setVisible(true);
        } else if(subjectValue == null){
            error += "Nie wybrano przedmiotu!\n";
            errorMsg.setText(error);
            errorMsg.setVisible(true);
        } else if (gradeTypeValue == null){
            error += "Nie podano rodzaju oceny!\n";
            errorMsg.setText(error);
            errorMsg.setVisible(true);
        } else {
            error += "Nie podano oceny!";
            errorMsg.setText(error);
            errorMsg.setVisible(true);
        }
    }

    public void onClickBtnCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setLoggedUser(String login) {
        loggerUserId = db.getUserId(login).get(0).getId();
    }
}
