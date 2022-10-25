package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML private TextField  loginText;
    @FXML private PasswordField passwordText;
    @FXML private PasswordField passwordTextRepeated;
    @FXML private Button closeButton;
    @FXML private Label errorMsg;

    public void onClickBtnRegister(ActionEvent actionEvent) throws IOException {
        DBTransaction db = new DBTransaction();
        String errors = "";
        String login = loginText.getText().trim();
        String password = passwordText.getText().trim();
        String passwordRepeated = passwordTextRepeated.getText().trim();
        if(!login.equals("") && password.equals(passwordRepeated) && db.ifUserExist(login) == false){
            passwordText.setStyle("-fx-border-color: none;");
            passwordTextRepeated.setStyle("-fx-border-color: none;");
            errorMsg.setVisible(false);
            db.registerUser(login, String.valueOf(password.hashCode()));
            loginText.setText("");
            passwordText.setText("");
            passwordTextRepeated.setText("");

            closeWindow();

            Parent fxmlLoader = FXMLLoader.load(RegisterController.class.getResource("successRegister-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();

        } else if (db.ifUserExist(login) == true){
            errors += "Podany użytkownik istnieje!\n";
            errorMsg.setText(errors);
            errorMsg.setVisible(true);
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
        closeWindow();
    }
    public void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

/*
TODO
1. Po poprawnej rejestracji wyskakujace okienko powinno zamknac tez formularz rejestracji
2. Hashowanie hasla
3. Regex (chociażby prosty)?
*/