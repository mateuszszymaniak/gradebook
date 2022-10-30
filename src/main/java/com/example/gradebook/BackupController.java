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

public class BackupController {
    @FXML private Button btnCancel;
    @FXML private TextField passwordText;

    public void onClickBtnCreate(ActionEvent actionEvent){
        Backup bckp = new Backup();
        String password = passwordText.getText().trim();
        bckp.createBackup(password);
        passwordText.setText("");

        closeWindow();

        Parent fxmlLoader = null;
        try {
            fxmlLoader = FXMLLoader.load(ResetPasswdController.class.getResource("successCreateBckp-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void onClickBtnRestore(ActionEvent actionEvent){
        Backup bckp = new Backup();
        String password = passwordText.getText().trim();
        bckp.importBackup(password);
        passwordText.setText("");

        closeWindow();

        Parent fxmlLoader = null;
        try {
            fxmlLoader = FXMLLoader.load(ResetPasswdController.class.getResource("successRestoreBckp-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }

    public void onClickBtnCancel(ActionEvent actionEvent){
        closeWindow();
    }
    public void closeWindow(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
