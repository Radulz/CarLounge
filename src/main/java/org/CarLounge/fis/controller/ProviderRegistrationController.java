package org.CarLounge.fis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
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

public class ProviderRegistrationController {
    @FXML
    private Button goToIndividual;

    @FXML
    private Button goToLegal;

    @FXML
    private Hyperlink goBackToChoice;

    public void switchToIndividual(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("IndividualPRegistration.fxml"));
        Stage window= (Stage)goToIndividual.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void switchToLegal(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LegalPersonRegistration.fxml"));
        Stage window= (Stage)goToLegal.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void switchBackToChoice(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ChoiceRegistration.fxml"));
        Stage window= (Stage)goBackToChoice.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
