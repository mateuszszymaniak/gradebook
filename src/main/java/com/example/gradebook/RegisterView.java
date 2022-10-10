package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterView {
    @FXML
    private TextField  loginText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField passwordTextRepeated;
    @FXML
    private Button closeButton;

    public void onClickBtnRegister(ActionEvent actionEvent) {
        // przyda sie if na sprawdzenie czy login jest w bazie

        
    }

    public void onClickBtnCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
