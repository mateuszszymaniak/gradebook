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

public class AddStudentController {
    @FXML private Button cancelButton;
    @FXML private TextField surnameText;
    @FXML private TextField nameText;
    @FXML private TextField studentGroupText;
    @FXML private TextField schoolYearText;
    @FXML private Label errorMsg;

    public void onClickBtnAdd(ActionEvent actionEvent) throws IOException {
        DBTransaction db = new DBTransaction();
        String surname = surnameText.getText().trim();
        String name = nameText.getText().trim();
        String studentGroup = studentGroupText.getText().trim();
        String schoolYear = schoolYearText.getText().trim();

        if(!surname.equals("") && !name.equals("") && !studentGroup.equals("") && !schoolYear.equals("")){
            db.addStudent(surname, name, studentGroup, schoolYear);
            errorMsg.setVisible(false);

            Parent fxmlLoader = FXMLLoader.load(RegisterController.class.getResource("successAddStudent-view.fxml"));
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
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
