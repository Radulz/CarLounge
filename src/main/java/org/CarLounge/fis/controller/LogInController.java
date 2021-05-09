package org.CarLounge.fis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import com.jfoenix.controls.JFXButton;
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


public class LogInController {
    @FXML
    private JFXButton goToChoice;

    @FXML
    private JFXButton logInToAcc;

    @FXML
    private BorderPane logInContainer;

    /*@FXML
    private StackPane mainContainer;*/

    public void switchToChoice(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ChoiceRegistration.fxml"));
        /*Scene scene = goToChoice.getScene();
        root.translateXProperty().set(scene.getWidth());

        mainContainer.getChildren().add(root);

        Timeline timeline=new Timeline();
        KeyValue KV=new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame KF = new KeyFrame(Duration.seconds(0.6), KV);
        timeline.getKeyFrames().add(KF);
        timeline.setOnFinished(t -> { mainContainer.getChildren().remove(logInContainer); });
        timeline.play();*/

        Stage window= (Stage)goToChoice.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void switchToHomeScene(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomeProvider.fxml"));
        Stage window = (Stage)logInToAcc.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
