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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ProviderAllTest {
    public static final String TEXT = "1231231231213";
    public static final String TEXT1 = "1239999999999";
    public static final String USERNAME = "test@gmail.com";
    public static final String USERNAME1 = "client@gmail.com";
    public static final String PLATE = "AR10FIS";
    double x, y = 0;

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".testingCarLoungeDatabases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        ProviderService.initDatabase();
        ListingService.initDatabase();
    }
    @AfterEach
    void tearDown() {
        ClientService.closeDatabase();
        ProviderService.closeDatabase();
        ListingService.closeDatabase();
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
        robot.write("1231231213");
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

        robot.clickOn("#MenuButtonProvider");
        Thread.sleep(2000);

        robot.clickOn("#HomeProvider");
        Thread.sleep(1000);

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
        robot.write(PLATE);
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

        robot.clickOn("#NewListingAdd");
        assertThat(robot.lookup("#NewListingMessage").queryText()).hasText(String.format("An active listing with %s number plate already exists!", PLATE));

        robot.clickOn("#MyListingsProvider");
        Thread.sleep(5000);

        robot.clickOn("#MyProfileProvider");
        Thread.sleep(5000);

        robot.clickOn("#LogOutOfAccountProvider");
        Thread.sleep(1000);

        robot.clickOn("#signUp");

        robot.clickOn("#choiceClient");

        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText("Email field can't be empty!");

        robot.clickOn("#ClientRegistrationEmail");
        robot.write("client");
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
        robot.write("9999999999");
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
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText(String.format("An account with the same personal identification number %s already exists!", TEXT1));

        robot.clickOn("#ClientRegistrationCnp");
        robot.write("1111111111111");
        robot.clickOn("#ClientRegistrationSubmit");
        assertThat(robot.lookup("#ClientRegistrationMessage").queryText()).hasText(String.format("An account with the username %s already exists!", USERNAME1));

        robot.clickOn("#ClientRegistrationGoToLogIn");

        robot.clickOn("#LogInEmail");
        robot.write(USERNAME1);
        robot.clickOn("#LogInPassword");
        robot.write("Tare1234");
        robot.clickOn("#logIn");

        robot.clickOn("#MenuButtonClient");
        Thread.sleep(2000);

        robot.clickOn("#HomeClient");
        Thread.sleep(1000);

        robot.clickOn("#CarsClient");
        Thread.sleep(1000);

        robot.clickOn("#ClientRentButton");
        assertThat(robot.lookup("#ClientRentalMessage").queryText()).hasText("Number plate field can't be empty");

        robot.clickOn("#ClientNumberPlate");
        robot.write("s");
        robot.clickOn("#ClientRentButton");
        assertThat(robot.lookup("#ClientRentalMessage").queryText()).hasText("Number plate is not valid!");

        robot.clickOn("#ClientNumberPlate");
        robot.write(PLATE);
        robot.clickOn("#ClientRentButton");
        assertThat(robot.lookup("#ClientRentalMessage").queryText()).hasText("Rental successful!");
        Thread.sleep(1000);

        robot.clickOn("#ManageRentalsClient");
        Thread.sleep(1000);

        robot.clickOn("#RB1");
        robot.clickOn("#RB2");
        robot.clickOn("#RB3");
        robot.clickOn("#RB4");
        robot.clickOn("#RB5");
        Thread.sleep(500);

        robot.clickOn("#ClientCompleteRental");
        assertThat(robot.lookup("#ClientCompleteRentalMessage").queryText()).hasText("Listing marked as completed successfully! Thank you for your feedback!");

        Thread.sleep(1000);
        robot.clickOn("#ClientCompleteRental");
        assertThat(robot.lookup("#ClientCompleteRentalMessage").queryText()).hasText("You can`t submit feedback without an active rental!");
        Thread.sleep(1000);

        robot.clickOn("#MyProfileClient");
        Thread.sleep(1000);

        robot.clickOn("#LogOutOfAccountClient");
        Thread.sleep(1000);

        robot.clickOn("#LogInEmail");
        robot.write(USERNAME);
        robot.clickOn("#LogInPassword");
        robot.write("Tare1234");
        robot.clickOn("#logIn");

        robot.clickOn("#MenuButtonProvider");
        Thread.sleep(2000);

        robot.clickOn("#MyProfileProvider");
        Thread.sleep(1000);

        robot.clickOn("#LogOutOfAccountProvider");
        Thread.sleep(1000);
    }
}
