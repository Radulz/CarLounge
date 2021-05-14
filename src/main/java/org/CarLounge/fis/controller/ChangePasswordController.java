package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.CarLounge.fis.exceptions.ConfirmPasswordFieldIsEmpty;
import org.CarLounge.fis.exceptions.PasswordDoesNotContainTheRequiredCharacters;
import org.CarLounge.fis.exceptions.PasswordFieldIsEmpty;
import org.CarLounge.fis.exceptions.PasswordsDoesNotMatch;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.ProviderService;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML
    public Text changePassMessage;
    @FXML
    public PasswordField password;
    @FXML
    public PasswordField confirmPassword;
    @FXML
    public JFXButton change;
    @FXML
    public Hyperlink logInNow;
    @FXML
    private ImageView exit;
    @FXML
    private JFXButton cancel;

    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    private static boolean checkPassword(String password){
        char ch;
        boolean capitalLetter = false;
        boolean lowerCaseLetter = false;
        boolean number = false;

        if(password.length() < 8) {
            return false;
        }
        for(int i=0;i < password.length();i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                number = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalLetter = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseLetter = true;
            }
            if(number && capitalLetter && lowerCaseLetter){
                return true;
            }

        }
        return false;
    }

    public static void checkFields(String pass, String confirmPass) throws PasswordFieldIsEmpty, ConfirmPasswordFieldIsEmpty, PasswordDoesNotContainTheRequiredCharacters, PasswordsDoesNotMatch {
        if(pass == ""){
            throw new PasswordFieldIsEmpty();
        }
        else if(!checkPassword(pass)){
            throw new PasswordDoesNotContainTheRequiredCharacters();
        }
        else if(confirmPass == ""){
            throw new ConfirmPasswordFieldIsEmpty();
        }
        else if(!pass.equals(confirmPass)){
            throw new PasswordsDoesNotMatch();
        }
    }

    public void switchToLogIn(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window = (Stage)cancel.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void changePassword(MouseEvent mouseEvent) {
        String pass = password.getText();
        String confirmPass = confirmPassword.getText();
        String encodedPass;
        try{
            checkFields(pass, confirmPass);
            if(CheckCNPController.getSw() != 0){
                if(CheckCNPController.getSw() == 1){
                    Provider provider = CheckCNPController.getProvider();
                    encodedPass= ClientService.encodePassword(ForgotPasswordController.getUsername(), pass);
                    provider.setPassword(encodedPass);
                    ProviderService.ProviderRepository.update(provider);
                    changePassMessage.setText("Password changed successfully!");
                    logInNow.setText("Login Now!");
                }
                else{
                    Client client = CheckCNPController.getClient();
                    encodedPass= ClientService.encodePassword(ForgotPasswordController.getUsername(), pass);
                    client.setPassword(encodedPass);
                    ClientService.ClientRepository.update(client);
                    changePassMessage.setText("Password changed successfully!");
                    logInNow.setText("Login Now!");
                }
            }
            else if(CheckTaxRegNoController.getSw() != -1){
                Provider provider = CheckTaxRegNoController.getProvider();
                encodedPass= ClientService.encodePassword(ForgotPasswordController.getUsername(), pass);
                provider.setPassword(encodedPass);
                ProviderService.ProviderRepository.update(provider);
                changePassMessage.setText("Password changed successfully!");
                logInNow.setText("Login Now!");
            }
        }
        catch(PasswordFieldIsEmpty e){
            changePassMessage.setText(e.getMessage());
        }
        catch(PasswordDoesNotContainTheRequiredCharacters e){
            changePassMessage.setText(e.getMessage());
        }
        catch(ConfirmPasswordFieldIsEmpty e){
            changePassMessage.setText(e.getMessage());
        }
        catch(PasswordsDoesNotMatch e){
            changePassMessage.setText(e.getMessage());
        }
    }


    public void goToLogIn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window = (Stage)cancel.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
