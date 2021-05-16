package org.CarLounge.fis.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.FileSystemService;
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
class ClientRegistrationTest {
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
    void testClientRegistration(FxRobot robot) throws InterruptedException {

        robot.clickOn("#logIn");
        assertThat(robot.lookup("#LogInMessage").queryText()).hasText("Please enter your credentials.");
        robot.clickOn("#signUp");
        robot.clickOn("#choiceClient");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Email field can't be empty!");

        robot.clickOn("#ClientRegistrationEmail");
        robot.write("test");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("The entered text is not an email!");

        robot.clickOn("#ClientRegistrationEmail");
        robot.write("@gmail.com");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("First name is missing!");

        robot.clickOn("#ClientRegistrationFirstName");
        robot.write("Robot");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Last name is missing!");

        robot.clickOn("#ClientRegistrationLastName");
        robot.write("RobotLast");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Birth date field is empty!");

        robot.clickOn("#ClientRegistrationBirthDate");
        robot.write("0");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("A valid date is required!");

        robot.clickOn("#ClientRegistrationBirthDate");
        robot.write("01/02/2008");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("You must be at least 18 years old!");

        robot.clickOn("#ClientRegistrationBirthDate");
        robot.write("01/02/2003");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Personal identification number field can't be empty!");

        robot.clickOn("#ClientRegistrationCnp");
        robot.write("123");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Personal identification number is not valid!");

        robot.clickOn("#ClientRegistrationCnp");
        robot.write("1231231231");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Password field can't be empty!");

        robot.clickOn("#ClientRegistrationPassword");
        robot.write("T");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Password does not contain the specified characters!");

        robot.clickOn("#ClientRegistrationPassword");
        robot.write("are1234");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Confirm password field can't be empty!");

        robot.clickOn("#ClientRegistrationConfirmPassword");
        robot.write("Tare123");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Passwords must match!");

        robot.clickOn("#ClientRegistrationConfirmPassword");
        robot.write("4");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Account created successfully!");

        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText(String.format("An account with the same personal identification number %s already exists!", TEXT));

        robot.clickOn("#ClientRegistrationCnp");
        robot.write("1111111111111");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText(String.format("An account with the username %s already exists!", USERNAME));

        robot.clickOn("#ClientRegistrationGoToLogIn");

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
    }
}
