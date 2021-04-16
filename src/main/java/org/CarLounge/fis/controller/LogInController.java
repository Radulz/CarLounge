package org.CarLounge.fis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class LogInController {
    @FXML
    Button goToRegister;

    public void switchToRegister(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Stage window= (Stage)goToRegister.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    Button logintoacc;

    public void switchToHomeScene(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        Stage window = (Stage)logintoacc.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
