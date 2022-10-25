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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML private TextField loginText;
    @FXML private PasswordField passwordText;
    @FXML private Label errorMsg;
    @FXML private Button btnLogin;
    private Parent root;

    @FXML
    protected void onClickBtnLogin(ActionEvent actionEvent) throws IOException {
        //dodanie ifa z błedem logowania podobnie do RegisterView.java onClickBtnRegister metoda onClickBtnRegister
        DBTransaction db = new DBTransaction();
        String errors = "";
        String login = loginText.getText().trim();
        String password = String.valueOf(passwordText.getText().trim().hashCode());
        if(!login.equals("") && !password.equals("") && db.signIn(login,password) == true){
            errorMsg.setText(errors);
            errorMsg.setVisible(false);
            loggedWindow(login);

        } else {
            errors = "Podany login i/lub hasło jest nieprawidłowe!";
            errorMsg.setText(errors);
            errorMsg.setVisible(true);
        }
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
    public void loggedWindow(String login) throws IOException {
        //zamykanie okna NARESZCIE!!!!
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();

        //przekazywanie zmiennych!!!!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logged-view.fxml"));
        root = fxmlLoader.load();
        LoggedController loggedController = fxmlLoader.getController();
        loggedController.setLoggedUser(login);

        Scene scene = new Scene(root, 428, 373);
        Stage loggedWindow = new Stage();
        loggedWindow.setTitle("Dziennik " + login);
        loggedWindow.setScene(scene);
        loggedWindow.initModality(Modality.WINDOW_MODAL);
        loggedWindow.show();
    }
}