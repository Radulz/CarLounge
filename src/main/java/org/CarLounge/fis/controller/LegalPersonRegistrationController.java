package org.CarLounge.fis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import org.CarLounge.fis.services.LegalPersonProviderService;

public class LegalPersonRegistrationController {
    @FXML
    public Button Submit;
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;
    @FXML
    public PasswordField confirmPassword;
    @FXML
    public TextField companyName;
    @FXML
    public Label individualPhotoPath;
    @FXML
    public TextField address;
    @FXML
    public TextField phoneNo;
    @FXML
    public TextField taxRegNo;
    @FXML
    public Hyperlink logIn;
    @FXML
    public Text registrationMessage;
    @FXML
    private Hyperlink goBackToProviderR;

    public void switchBackToProviderR(MouseEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ProviderRegistration.fxml"));
        Stage window= (Stage)goBackToProviderR.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void sendReg(MouseEvent mouseEvent) {

        try{
            LegalPersonProviderService.addProvider(email.getText(),  password.getText(), companyName.getText(), address.getText(), phoneNo.getText(), taxRegNo.getText(), confirmPassword.getText() );
            registrationMessage.setText("Account created successfully!");
            logIn.setText("Login now!");
        }
        catch(EmailFieldIsEmpty e){
            registrationMessage.setText(e.getMessage());
        }
        catch(TextIsNotAValidEmail e){
            registrationMessage.setText(e.getMessage());
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
        catch(CompanyNameIsMissing e){
            registrationMessage.setText(e.getMessage());
        }
        catch(AddressIsMissing e){
            registrationMessage.setText(e.getMessage());
        }
        catch(PhoneNumberIsMissing e){
            registrationMessage.setText(e.getMessage());
        }
        catch(InvalidPhoneNumber e){
            registrationMessage.setText(e.getMessage());
        }
        catch(TaxRegNoIsMissing e){
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
