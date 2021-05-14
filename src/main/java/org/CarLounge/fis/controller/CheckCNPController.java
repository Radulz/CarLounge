package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import org.CarLounge.fis.exceptions.CnpDoesNotMatchEmail;
import org.CarLounge.fis.exceptions.CnpIsMissing;
import org.CarLounge.fis.exceptions.CnpIsNotValid;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.ProviderService;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckCNPController implements Initializable {
    @FXML
    public TextField cnp;
    @FXML
    public Text confirmMessage;
    @FXML
    private JFXButton cancel;
    @FXML
    private ImageView exit;
    @FXML
    private JFXButton next;

    private static Client client;
    private static Provider provider;
    private static int sw=0;

    public static Client getClient() {
        return client;
    }

    public static Provider getProvider() {
        return provider;
    }

    public static int getSw() {
        return sw;
    }

    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    private static boolean isCNPValid(String cnp){
        char ch;

        if(cnp.length()<13) {
            return false;
        }

        for(int i=0; i<cnp.length();i++){
            ch=cnp.charAt(i);
            if(!Character.isDigit(ch)){
                return false;
            }
        }

        return true;
    }

    private void checkIdentity(String cnp) throws CnpIsMissing, CnpIsNotValid, CnpDoesNotMatchEmail {
        if(cnp == ""){
            throw new CnpIsMissing();
        }
        else if(!isCNPValid(cnp)){
            throw new CnpIsNotValid();
        }
        else if(!confirmIdentity(cnp)){
            throw new CnpDoesNotMatchEmail();
        }

    }

    private static boolean confirmIdentity(String cnp){

        for(Provider p : ProviderService.ProviderRepository.find()){
            if(p.getCnp().equals(cnp) && p.getEmail().equals(ForgotPasswordController.getUsername())){
                provider=p;
                sw=1;
                return true;
            }
        }

        for(Client c : ClientService.ClientRepository.find()){
            if(c.getCNP().equals(cnp) && c.getEmail().equals(ForgotPasswordController.getUsername())){
                client=c;
                sw=2;
                return true;
            }
        }
        return false;
    }

    public void switchToChangePass(MouseEvent mouseEvent) throws IOException{

        String user = ForgotPasswordController.getUsername();
        String confirmCNP = cnp.getText();
        try {
            checkIdentity(confirmCNP);
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ChangePassword.fxml"));
            Stage window = (Stage) next.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        catch (CnpIsMissing e){
            confirmMessage.setText(e.getMessage());
        }
        catch (CnpIsNotValid e){
            confirmMessage.setText(e.getMessage());
        }
        catch (CnpDoesNotMatchEmail e){
            confirmMessage.setText(e.getMessage());
        }
    }

    public void switchToLogIn(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window = (Stage)cancel.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
