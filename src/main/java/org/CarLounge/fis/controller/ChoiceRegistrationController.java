package org.CarLounge.fis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ChoiceRegistrationController {
    @FXML
    private Hyperlink goBackToLogin;

    @FXML
    private Button goToClientR;

    @FXML
    private Button goToProviderR;

    @FXML
    private AnchorPane choiceContainer;

    @FXML
    private StackPane mainChoiceContainer;

    public void switchBackToLogin(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window= (Stage)goBackToLogin.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void switchToClientR(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientRegistration.fxml"));
        Stage window= (Stage)goToClientR.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    public void switchToProviderR(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ProviderRegistration.fxml"));
        Stage window= (Stage)goToProviderR.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
