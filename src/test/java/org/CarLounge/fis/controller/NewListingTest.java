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
class NewListingTest {
    public static final String USERNAME = "provider@gmail.com";
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
    void testNewListing(FxRobot robot) throws InterruptedException {

        robot.clickOn("#LogInEmail");
        robot.write(USERNAME);
        robot.clickOn("#LogInPassword");
        robot.write("Provider1");
        robot.clickOn("#logIn");


        robot.clickOn("#MenuButtonProvider");

        Thread.sleep(2000);
        robot.clickOn("#HomeProvider");
        robot.clickOn("#NewListingProvider");
        Thread.sleep(1000);

        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Make field can't be empty!");

        robot.clickOn("#NewListingMake");
        robot.write("Audi");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Model field can't be empty!");

        robot.clickOn("#NewListingModel");
        robot.write("A6");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Year field can't be empty!");

        robot.clickOn("#NewListingYear");
        robot.write("s");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Year must be an integer between 1900 and 2021.");

        robot.clickOn("#NewListingYear");
        robot.write("2009");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Mileage can't be empty!");

        robot.clickOn("#NewListingMileage");
        robot.write("s");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Mileage must be an integer between 0 km and 1 000 000 km");

        robot.clickOn("#NewListingMileage");
        robot.write("180000");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Cubic Capacity field can't be empty");

        robot.clickOn("#NewListingCmc");
        robot.write("s");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Cubic capacity must be an integer between 100 and 20000");

        robot.clickOn("#NewListingCmc");
        robot.write("2998");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Fuel field can't be empty!");

        robot.clickOn("#NewListingFuel");
        robot.write("s");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("This fuel is not accepted!");

        robot.clickOn("#NewListingFuel");
        robot.write("Petrol");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Number plate field can't be empty");

        robot.clickOn("#NewListingPlate");
        robot.write("s");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Number plate is not valid!");

        robot.clickOn("#NewListingPlate");
        robot.write("AR10FIS");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Price per day must be included!");

        robot.clickOn("#NewListingPrice");
        robot.write("s");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText("Price must be an integer number!");

        robot.clickOn("#NewListingPrice");
        robot.write("50");
        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText(String.format("Listing added successfully under email: %s", USERNAME));

        robot.clickOn("#MyListingsProvider");
        Thread.sleep(2000);

        robot.clickOn("#MyProfileProvider");
        robot.clickOn("#LogOutOfAccountProvider");
    }
}