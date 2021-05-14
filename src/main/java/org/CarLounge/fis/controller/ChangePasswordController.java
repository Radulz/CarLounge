package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML
    private ImageView exit;

    @FXML
    private JFXButton cancel;

    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    public void switchToLogIn(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window = (Stage)cancel.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
