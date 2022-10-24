package com.example.gradebook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML private TextField loginText;
    @FXML private PasswordField passwordText;
    @FXML private Label errorMsg;

    @FXML
    protected void onClickBtnLogin(ActionEvent actionEvent) throws IOException {
        //dodanie ifa z błedem logowania podobnie do RegisterView.java onClickBtnRegister metoda onClickBtnRegister
        DBTransaction db = new DBTransaction();
        String errors = "";
        String login = loginText.getText().trim();
        String password = passwordText.getText().trim();
        if(!login.equals("") && !password.equals("") && db.ifUserExist(login) == true){
            errorMsg.setText(errors);
            errorMsg.setVisible(false);
            loggedWindow(login);

        } else {
            errors = "Podany login i/lub hasło jest nieprawidłowe!";
            errorMsg.setText(errors);
            errorMsg.setVisible(true);
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
    public void loggedWindow(String login) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("logged-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 428, 373);
        Stage loggedWindow = new Stage();
        loggedWindow.setTitle("Dziennik " + login);
        loggedWindow.setScene(scene);
        loggedWindow.initModality(Modality.WINDOW_MODAL);
        loggedWindow.setUserData(login);
        loggedWindow.show();
    }
}