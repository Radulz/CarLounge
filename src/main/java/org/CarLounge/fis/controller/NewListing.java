package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewListing {
    @FXML
    private JFXButton switchToHome;

    @FXML
    private StackPane contentArea;

    public void cancel(ActionEvent actionEvent) {
    }

    public void ok(ActionEvent actionEvent) {
    }

    public void addCar(MouseEvent mouseEvent) {
    }

    public void home(MouseEvent event) throws Exception{
        Parent fxml = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
}
