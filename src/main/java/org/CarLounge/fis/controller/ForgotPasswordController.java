package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.CarLounge.fis.exceptions.AccountDoesNotExist;
import org.CarLounge.fis.exceptions.EmailFieldIsEmpty;
import org.CarLounge.fis.exceptions.TextIsNotAValidEmail;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.ProviderService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    @FXML
    public ImageView exit;
    @FXML
    public TextField email;
    @FXML
    public Text forgotMessage;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton next;

    private static String username;
    private static String type;

    public static String getUsername() {
        return username;
    }

    public static String getType() {
        return type;
    }

    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    public void switchToLogIn(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window = (Stage)cancel.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    private static boolean checkEmail(String email){
        String [] acceptedMails = {"@yahoo.com", "@yahoo.co.uk", "@yahoo.ro", "@gmail.com", "@student.upt.ro", "@cs.upt.ro"};
        for(String sir : acceptedMails){
            if(email.endsWith(sir))
                return true;
        }
        return false;
    }

    private static boolean checkExistence(String user) {

        type = "";
        for(Client client: ClientService.getClientRepository().find()) {
            if (user.equals(client.getEmail())) {
                    type = "Individual";
            }
        }
        for(Provider provider: ProviderService.getProviderRepository().find()){
            if(user.equals(provider.getEmail()) && provider.getFirstName().equals("LegalPerson")){
                type = "Legal";
            }
            else if(user.equals(provider.getEmail()) && !provider.getFirstName().equals("LegalPerson")){
                type = "Individual";
            }
        }
        if(type == "") {
            return false;
        }
        else {
            return true;
        }

    }

    public static void checkEmailField(String email) throws EmailFieldIsEmpty, TextIsNotAValidEmail, AccountDoesNotExist {
        if(email == ""){
            throw  new EmailFieldIsEmpty();
        }
        else if(!checkEmail(email)){
            throw new TextIsNotAValidEmail();
        }
        else if(!checkExistence(email)){
            throw new AccountDoesNotExist();
        }
    }

    public void switchToNext(MouseEvent mouseEvent) throws IOException {
        username = email.getText();
        try{
            checkEmailField(username);
            if(type.equals("Individual")){
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CheckCNP.fxml"));
                Stage window = (Stage)next.getScene().getWindow();
                window.setScene(new Scene(root));
            }
            else if(type.equals("Legal")){
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CheckTaxRegNo.fxml"));
                Stage window = (Stage)next.getScene().getWindow();
                window.setScene(new Scene(root));
            }
        }
        catch(EmailFieldIsEmpty | TextIsNotAValidEmail | AccountDoesNotExist e){
            forgotMessage.setText(e.getMessage());
        }
    }
}
