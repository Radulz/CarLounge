package org.CarLounge.fis.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.CarLounge.fis.services.*;
import org.CarLounge.fis.services.ProviderService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
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
class LegalPersonRegistrationTest {
    public static final String TEXT = "1231231231231";
    public static final String USERNAME = "test@gmail.com";
    double x, y = 0;

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".testingCarLoungeDatabases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        ProviderService.initDatabase();
    }

    /*@Start
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
    }*/

    @AfterEach
    void tearDown() {
        ClientService.closeDatabase();
        ProviderService.closeDatabase();
        ListingService.closeDatabase();
    }

    @Test
    void testLegalPersonRegistration(FxRobot robot) throws InterruptedException {

        robot.clickOn("#signUp");
        robot.clickOn("#choiceProvider");
        robot.clickOn("#choiceLegal");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Email field can't be empty!");

        robot.clickOn("#LegalRegistrationEmail");
        robot.write("test");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("The entered text is not an email!");

        robot.clickOn("#LegalRegistrationEmail");
        robot.write("@gmail.com");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Password field can't be empty!");

        robot.clickOn("#LegalRegistrationPassword");
        robot.write("T");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Password does not contain the specified characters!");

        robot.clickOn("#LegalRegistrationPassword");
        robot.write("are1234");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Confirm password field can't be empty!");

        robot.clickOn("#LegalRegistrationConfirmPassword");
        robot.write("Tare123");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Passwords must match!");

        robot.clickOn("#LegalRegistrationConfirmPassword");
        robot.write("4");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Company name field can't be empty!");

        robot.clickOn("#LegalRegistrationCompany");
        robot.write("Robotzi");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Address field can't be empty!");

        robot.clickOn("#LegalRegistrationAddress");
        robot.write("YouTube");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Phone number field can't be empty!");

        robot.clickOn("#LegalRegistrationPhone");
        robot.write("0");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Invalid phone number!");

        robot.clickOn("#LegalRegistrationPhone");
        robot.write("743123123");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Tax Registration Number field can't be empty!");


        robot.clickOn("#LegalRegistrationTax");
        robot.write("1231231231231");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText("Account created successfully!");

        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText(String.format("An account with the same tax registration number %s already exists!", TEXT));

        robot.clickOn("#LegalRegistrationTax");
        robot.write("1111111111111");
        robot.clickOn("#LegalRegistrationSubmit");
        assertThat(robot.lookup("#LegalRegistrationMessage").queryText()).hasText(String.format("An account with the username %s already exists!", USERNAME));

        robot.clickOn("#LegalRegistrationGoToLogIn");

        robot.clickOn("#LogInEmail");
        robot.write("t");
        robot.clickOn("#LogInPassword");
        robot.write("Tare123");
        robot.clickOn("#logIn");
        assertThat(robot.lookup("#LogInMessage").queryText()).hasText("This account does not exist. Sign Up now!");

        robot.clickOn("#LogInEmail");
        robot.write("est@gmail.com");
        robot.clickOn("#logIn");
        assertThat(robot.lookup("#LogInMessage").queryText()).hasText("Credentials entered are incorrect!");

        robot.clickOn("#LogInPassword");
        robot.write("4");
        robot.clickOn("#logIn");

        robot.clickOn("#MenuButtonProvider");
        Thread.sleep(2000);
        robot.clickOn("#LogOutOfAccountProvider");
    }
}
