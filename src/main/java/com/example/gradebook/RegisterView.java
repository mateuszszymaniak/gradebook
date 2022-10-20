package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
    @FXML
    private Label errorMsg;

    public void onClickBtnRegister(ActionEvent actionEvent) {
        DBTransaction db = new DBTransaction();
        String errors = "";
        String login = loginText.getText().trim();
        String password = passwordText.getText().trim();
        String passwordRepeated = passwordTextRepeated.getText().trim();
        if(!login.equals("") && password.equals(passwordRepeated)){
            passwordText.setStyle("-fx-border-color: none;");
            passwordTextRepeated.setStyle("-fx-border-color: none;");
            errorMsg.setVisible(false);
            db.registerUser(login, password);
        } else if (login.equals("")) {
            loginText.setStyle("-fx-border-color: red;");
            errors += "Nie podano loginu!\n";
            errorMsg.setText(errors);
            errorMsg.setVisible(true);
        } else {
            passwordText.setStyle("-fx-border-color: red;");
            passwordTextRepeated.setStyle("-fx-border-color: red;");
            errors += "Hasła nie są takie same!";
            errorMsg.setText(errors);
            errorMsg.setVisible(true);
        }
    }

    public void onClickBtnCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
