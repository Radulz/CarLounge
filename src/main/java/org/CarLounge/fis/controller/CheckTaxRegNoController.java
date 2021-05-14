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
import org.CarLounge.fis.exceptions.TaxRegNoDoesNotMatchEmail;
import org.CarLounge.fis.exceptions.TaxRegNoIsMissing;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ProviderService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckTaxRegNoController implements Initializable {
    @FXML
    public Text confirmMessage;
    @FXML
    public TextField taxRegNo;
    @FXML
    private ImageView exit;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton next;

    private static Provider provider;
    private static int sw=-1;

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

    public static boolean checkTaxRegNoExistence(String taxRegNo){
        for(Provider p : ProviderService.ProviderRepository.find()){
            if(p.getTaxRegNo().equals(taxRegNo) && p.getEmail().equals(ForgotPasswordController.getUsername())){
                provider = p;
                sw=3;
                return true;
            }
        }
        return false;
    }

    public static void checkTaxRegNo(String taxRegNo) throws TaxRegNoIsMissing, TaxRegNoDoesNotMatchEmail {
        if(taxRegNo == ""){
            throw new TaxRegNoIsMissing();
        }
        else if(!checkTaxRegNoExistence(taxRegNo)){
            throw new TaxRegNoDoesNotMatchEmail();
        }

    }

    public void switchToChangePass(MouseEvent mouseEvent) throws IOException {
        String tax = taxRegNo.getText();
        try{
            checkTaxRegNo(tax);
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ChangePassword.fxml"));
            Stage window = (Stage)next.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        catch (TaxRegNoIsMissing e){
            confirmMessage.setText(e.getMessage());
        }
        catch (TaxRegNoDoesNotMatchEmail e){
            confirmMessage.setText(e.getMessage());
        }
    }

    public void switchToLogIn(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window = (Stage)cancel.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
