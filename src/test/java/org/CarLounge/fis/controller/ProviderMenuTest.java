package org.CarLounge.fis.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.FileSystemService;
import org.CarLounge.fis.services.ListingService;
import org.CarLounge.fis.services.ProviderService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ProviderMenuTest {

    double x, y = 0;

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".CarLoungeDatabases";
        // FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        ProviderService.initDatabase();
        ListingService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws IOException {
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

    @Test
    void testProviderMenu(FxRobot robot) throws InterruptedException {

        robot.clickOn("#LogInEmail");
        robot.write("provider@gmail.com");
        robot.clickOn("#LogInPassword");
        robot.write("Provider1");
        robot.clickOn("#logIn");


        robot.clickOn("#MenuButtonProvider");

        Thread.sleep(2000);
        robot.clickOn("#HomeProvider");
        robot.clickOn("#NewListingProvider");
        robot.clickOn("#MyListingsProvider");
        robot.clickOn("#MyProfileProvider");
        robot.clickOn("#LogOutOfAccountProvider");
    }

}