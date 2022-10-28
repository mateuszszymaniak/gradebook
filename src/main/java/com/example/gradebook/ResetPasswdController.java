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

public class ResetPasswdController {
    @FXML private Button btnCancel;
    @FXML private TextField loginText;
    @FXML private TextField passwordText;
    @FXML private TextField repeatPasswordText;
    @FXML private Label errorMsg;

    public void onClickBtnSave(ActionEvent actionEvent) {
        DBTransaction db = new DBTransaction();
        String errors = "";
        String login = loginText.getText().trim();
        String password = passwordText.getText().trim();
        String repeat = repeatPasswordText.getText().trim();
        if(!login.equals("") && password.equals(repeat) && db.ifUserExist(login) == true){
            passwordText.setStyle("-fx-border-color: none;");
            repeatPasswordText.setStyle("-fx-border-color: none;");
            errorMsg.setVisible(false);
            int id = db.getUserId(login).get(0).getId();
            db.changePassword(id, String.valueOf(password.hashCode()));;
            loginText.setText("");
            passwordText.setText("");
            repeatPasswordText.setText("");

            closeWindow();

            Parent fxmlLoader = null;
            try {
                fxmlLoader = FXMLLoader.load(ResetPasswdController.class.getResource("successReset-view.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } else if (db.ifUserExist(login) == false){
            errors += "Podany użytkownik nie istnieje!\n";
            errorMsg.setText(errors);
            errorMsg.setVisible(true);
        } else if (login.equals("")) {
            loginText.setStyle("-fx-border-color: red;");
            errors += "Nie podano loginu!\n";
            errorMsg.setText(errors);
            errorMsg.setVisible(true);
        } else {
            passwordText.setStyle("-fx-border-color: red;");
            repeatPasswordText.setStyle("-fx-border-color: red;");
            errors += "Hasła nie są takie same!";
            errorMsg.setText(errors);
            errorMsg.setVisible(true);
        }
    }

    public void onClickBtnCancel(ActionEvent actionEvent) {
        closeWindow();
    }
    public void closeWindow(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
