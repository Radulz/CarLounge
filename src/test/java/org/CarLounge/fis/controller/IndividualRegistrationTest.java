package org.CarLounge.fis.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.IndividualProviderService;
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
class IndividualRegistrationTest {
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
    void testIndividualRegistration(FxRobot robot) throws InterruptedException {

        robot.clickOn("#signUp");
        robot.clickOn("#choiceProvider");
        robot.clickOn("#choiceIndividual");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Email field can't be empty!");

        robot.clickOn("#IndividualRegistrationEmail");
        robot.write("test");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("The entered text is not an email!");

        robot.clickOn("#IndividualRegistrationEmail");
        robot.write("@gmail.com");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("First name is missing!");

        robot.clickOn("#IndividualRegistrationFirstName");
        robot.write("Robot");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Last name is missing!");

        robot.clickOn("#IndividualRegistrationLastName");
        robot.write("RobotLast");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Birth date field is empty!");

        robot.clickOn("#IndividualRegistrationBirthDate");
        robot.write("0");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("A valid date is required!");

        robot.clickOn("#IndividualRegistrationBirthDate");
        robot.write("01/02/2008");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("You must be at least 18 years old!");

        robot.clickOn("#IndividualRegistrationBirthDate");
        robot.write("01/02/2003");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Phone number field can't be empty!");

        robot.clickOn("#IndividualRegistrationPhone");
        robot.write("0");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Invalid phone number!");

        robot.clickOn("#IndividualRegistrationPhone");
        robot.write("743123123");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Personal identification number field can't be empty!");

        robot.clickOn("#IndividualRegistrationCnp");
        robot.write("123");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Personal identification number is not valid!");

        robot.clickOn("#IndividualRegistrationCnp");
        robot.write("1231231231");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Password field can't be empty!");

        robot.clickOn("#IndividualRegistrationPassword");
        robot.write("T");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Password does not contain the specified characters!");

        robot.clickOn("#IndividualRegistrationPassword");
        robot.write("are1234");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Confirm password field can't be empty!");

        robot.clickOn("#IndividualRegistrationConfirmPassword");
        robot.write("Tare123");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Passwords must match!");

        robot.clickOn("#IndividualRegistrationConfirmPassword");
        robot.write("4");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText("Account created successfully!");

        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText(String.format("An account with the same personal identification number %s already exists!", TEXT));

        robot.clickOn("#IndividualRegistrationCnp");
        robot.write("1111111111111");
        robot.clickOn("#IndividualRegistrationSubmit");
        assertThat(robot.lookup("#IndividualRegistrationMessage").queryText()).hasText(String.format("An account with the username %s already exists!", USERNAME));

        robot.clickOn("#IndividualRegistrationGoToLogIn");

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
