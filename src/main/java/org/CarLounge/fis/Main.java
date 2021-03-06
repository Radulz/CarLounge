package org.CarLounge.fis;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.jfoenix.controls.JFXButton;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.FileSystemService;
import org.CarLounge.fis.services.ListingService;
import org.CarLounge.fis.services.ProviderService;

//import java.awt.*;
//import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Main extends Application {
    double x, y = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FileSystemService.initDirectory();
        ClientService.initDatabase();
        ProviderService.initDatabase();
        ListingService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        primaryStage.setTitle("CarLounge");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
