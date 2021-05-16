package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.services.ClientService;
import javafx.scene.image.ImageView;

public class ClientRegistrationController implements  Initializable{
    @FXML
    public TextField email;
    @FXML
    public TextField firstname;
    @FXML
    public TextField lastname;
    @FXML
    public PasswordField password;
    @FXML
    public TextField birthDate;
    @FXML
    public JFXButton Submit;
    @FXML
    public Text registrationMessage;
    @FXML
    public Hyperlink logIn;
    @FXML
    public PasswordField confirmPassword;
    @FXML
    public TextField cnp;
    @FXML
    private Hyperlink goBackToChoice;
    @FXML
    private ImageView exit;

    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    public void switchBackToChoice(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ChoiceRegistration.fxml"));
        Stage window = (Stage)goBackToChoice.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void sendReg(MouseEvent mouseEvent) {

        try{
            ClientService.addClient(email.getText(), password.getText(), firstname.getText(), lastname.getText(), birthDate.getText(), confirmPassword.getText(), cnp.getText());
            registrationMessage.setText("Account created successfully!");
            logIn.setText("Login now!");
        }
        catch(EmailFieldIsEmpty e){
            registrationMessage.setText(e.getMessage());
        }
        catch(TextIsNotAValidEmail e){
            registrationMessage.setText(e.getMessage());
        }
        catch(FirstNameFieldIsEmpty e){
            registrationMessage.setText(e.getMessage());
        }
        catch(LastNameFieldIsEmpty e){
            registrationMessage.setText(e.getMessage());
        }
        catch(BirthDateFieldIsEmpty e){
            registrationMessage.setText(e.getMessage());
        }
        catch(BirthDateIsNotADate e){
            registrationMessage.setText(e.getMessage());
            birthDate.clear();
        }
        catch(MinimumAgeIsRequired e){
            registrationMessage.setText(e.getMessage());
            birthDate.clear();
        }
        catch(CnpIsMissing e){
            registrationMessage.setText(e.getMessage());
        }
        catch(CnpIsNotValid e){
            registrationMessage.setText(e.getMessage());
        }
        catch(CnpAlreadyExists e) {
            registrationMessage.setText(e.getMessage());
            cnp.clear();
        }
        catch(PasswordFieldIsEmpty e){
            registrationMessage.setText(e.getMessage());
        }
        catch(PasswordDoesNotContainTheRequiredCharacters e){
            registrationMessage.setText(e.getMessage());
        }
        catch(ConfirmPasswordFieldIsEmpty e){
            registrationMessage.setText(e.getMessage());
        }
        catch(PasswordsDoesNotMatch e){
            registrationMessage.setText(e.getMessage());
        }
        catch(UsernameAlreadyExistsException e){

            registrationMessage.setText(e.getMessage());
            logIn.setText("Login now!");
        }
    }

    public void goToLogIn(MouseEvent mouseEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window = (Stage)logIn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}