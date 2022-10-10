package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    protected void onClickBtnLogin(ActionEvent actionEvent) throws IOException {
        //dodanie ifa z błedem logowania podobnie do RegisterView.java onClickBtnRegister metoda onClickBtnRegister

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

    public void onClickBtnRegister(ActionEvent actionEvent) throws IOException {
        //blokada okna po rodzicu
        Parent fxmlLoader = FXMLLoader.load(HelloController.class.getResource("register-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }
}