package org.CarLounge.fis;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.CarLounge.fis.services.FileSystemService;
import org.CarLounge.fis.services.UserService;

//import java.awt.*;
//import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //initDirectory();
        //UserService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        primaryStage.setTitle("CarLounge");
        primaryStage.setScene(new Scene(root, 400, 360));
        primaryStage.show();
    }

    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
