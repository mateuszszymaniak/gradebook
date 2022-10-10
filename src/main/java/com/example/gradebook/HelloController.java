package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private TextField loginText;
    @FXML
    private PasswordField passwordText;

    @FXML
    protected void onClickBtnLogin(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("logged-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            Stage loggedWindow = new Stage();
            loggedWindow.setTitle("Nagłówek osoby zalogowanej");
            loggedWindow.setScene(scene);
            loggedWindow.initModality(Modality.WINDOW_MODAL);
            System.out.println(loginText.getText());
            System.out.println(passwordText.getText());
            loggedWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Label loggedUser = new Label("Zalogowany user");

    }

    public void onClickBtnRegister(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            Stage loggedWindow = new Stage();
            loggedWindow.setTitle("Rejestracja");
            loggedWindow.setScene(scene);
            loggedWindow.initModality(Modality.WINDOW_MODAL);
            System.out.println(loginText.getText());
            System.out.println(passwordText.getText());
            loggedWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}