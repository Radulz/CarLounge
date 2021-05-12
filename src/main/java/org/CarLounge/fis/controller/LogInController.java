package org.CarLounge.fis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import org.CarLounge.fis.exceptions.AccountDoesNotExist;
import org.CarLounge.fis.exceptions.CredentialsAreMissing;
import org.CarLounge.fis.exceptions.CredentialsEnteredAreIncorrect;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.ProviderService;


public class LogInController implements Initializable {
    private String username;
    @FXML
    public TextField email;

    @FXML
    public PasswordField password;

    @FXML
    public Text logInMessage;

    @FXML
    private JFXButton goToChoice;

    @FXML
    private JFXButton logInToAcc;

    @FXML
    private BorderPane logInContainer;

    @FXML
    private ImageView exit;

    public LogInController() {

    }

    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    public String getUsername() {
        return username;
    }

    public TextField getEmail() {
        return email;
    }

    public void switchToChoice(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ChoiceRegistration.fxml"));
        Stage window= (Stage)goToChoice.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public static void checkClientUser(String user, String pass) throws CredentialsEnteredAreIncorrect {
        for(Client client: ClientService.getClientRepository().find()) {
            if (user.equals(client.getEmail())) {
                if (!pass.equals(client.getPassword())) {
                    throw new CredentialsEnteredAreIncorrect();
                }
            }
        }
    }

    public static void checkProviderUser(String user, String pass) throws CredentialsEnteredAreIncorrect {
        for(Provider provider: ProviderService.getProviderRepository().find()){
            if(user.equals(provider.getEmail())){
                if(!pass.equals(provider.getPassword())){
                    throw new CredentialsEnteredAreIncorrect();
                }
            }
        }
    }

    public static void checkCredentials(String user, String pass) throws CredentialsAreMissing{
        if(user == "" || pass == ""){
            throw new CredentialsAreMissing();
        }
    }

    public static String checkExistence(String user, String pass) throws AccountDoesNotExist {

        String accountClass = "";
        for(Client client: ClientService.getClientRepository().find()) {
            if (user.equals(client.getEmail())) {
                if (pass.equals(client.getPassword())) {
                    accountClass = "Client";
                }
            }
        }
        for(Provider provider: ProviderService.getProviderRepository().find()){
            if(user.equals(provider.getEmail())){
                if(pass.equals(provider.getPassword())){
                    accountClass = "Provider";
                }
            }
        }
        if(accountClass == "") {
            throw new AccountDoesNotExist();
        }
        else {
            return accountClass;
        }

    }


    public void logIn(MouseEvent mouseEvent) throws Exception {
        String accountEmail = email.getText();
        String accountPassword = password.getText();
        String accountPasswordEncoded = ClientService.encodePassword(accountEmail, accountPassword);
        String accountClass = "";
        this.username=accountEmail;

        try{
            checkCredentials(accountEmail, accountPassword);
        }
        catch(CredentialsAreMissing e){
            logInMessage.setText(e.getMessage());
            return;
        }

        try {
            accountClass= checkExistence(accountEmail, accountPasswordEncoded);
            if (accountClass.equals("Client")) {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomeClient.fxml"));
                Stage window = (Stage) logInToAcc.getScene().getWindow();
                window.setScene(new Scene(root));
            } else if (accountClass.equals("Provider")) {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomeProvider.fxml"));
                Stage window = (Stage) logInToAcc.getScene().getWindow();
                window.setScene(new Scene(root));
            }
        }
        catch(AccountDoesNotExist e){

            try{
                checkClientUser(accountEmail, accountPasswordEncoded);
            }
            catch(CredentialsEnteredAreIncorrect e1){
                logInMessage.setText(e1.getMessage());
                return;
            }
            try{
                checkProviderUser(accountEmail, accountPasswordEncoded);
            }
            catch(CredentialsEnteredAreIncorrect e1){
                logInMessage.setText(e1.getMessage());
                return;
            }

            logInMessage.setText(e.getMessage());
        }

        /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomeClient.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomeProvider.fxml"));
        Stage window = (Stage)logInToAcc.getScene().getWindow();
        window.setScene(new Scene(root));*/
    }

}
