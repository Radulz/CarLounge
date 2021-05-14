package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.CarLounge.fis.exceptions.RentalInProgress;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.ListingService;
import org.CarLounge.fis.services.ProviderService;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientProfileController implements Initializable {

    @FXML
    public JFXButton delete;

    @FXML
    public Text deleteAccountMessage;
    public TextField cnp;
    public TextField bDate;
    public TextField email;
    public TextField fName;
    public TextField lName;

    @FXML
    public TextField completedRentals;

    public void initialize(URL location, ResourceBundle resources) {
        String username = ClientMenuController.getUsername();
        int cr = 0;
        //boolean sw = false;
        Client client = new Client();
        for (Client c : ClientService.getClientRepository().find()) {
            if (username.equals(c.getEmail())) {
                email.setText(c.getEmail());
                fName.setText(c.getFirstname());
                lName.setText(c.getLastname());
                bDate.setText(c.getBirthdate());
                cnp.setText(c.getCNP());
                client = c;
                //sw = true;
                //if (sw) {
                    for (Listing l : ListingService.getListingRepository().find()) {
                        if (client.getEmail().equals(l.getClientEmail())) {
                            if (l.getCompleted()) {
                                cr++;
                            }
                        }
                    }
                    completedRentals.setText("" + cr);
               // }
            }
        }


    }

    //Exception + check if there s an active rental.
    public static void checkIfActive(Listing l) throws RentalInProgress {
        if(!l.getClientEmail().equals("-") && !l.getCompleted()){
            throw new RentalInProgress();
        }
    }

    public void deleteAccount(MouseEvent mouseEvent) throws Exception {
        for(Listing l: ListingService.getListingRepository().find()) {
            if(l.getClientEmail().equals(ClientMenuController.getUsername())) {
                try{
                    checkIfActive(l);
                }
                catch(RentalInProgress e){
                    deleteAccountMessage.setText(e.getMessage());
                    return;
                }
            }
        }


        for(Client c: ClientService.getClientRepository().find()) {
            if(c.getEmail().equals(ClientMenuController.getUsername())) {
                ClientService.getClientRepository().remove(c);
                break;
            }
        }

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login1.fxml"));
        Stage window = (Stage)delete.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}

