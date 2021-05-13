package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ClientService;
import org.CarLounge.fis.services.ListingService;
import org.CarLounge.fis.services.ProviderService;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientProfileController implements Initializable {

    public JFXButton deleteAccount;
    public TextField cnp;
    public TextField bDate;
    public TextField email;
    public TextField fName;
    public TextField lName;

    public void initialize(URL location, ResourceBundle resources) {
        String username = ClientMenuController.getUsername();
        Client client = new Client();
        for (Client c : ClientService.getClientRepository().find()) {
            if (username.equals(c.getEmail())) {
                email.setText(c.getEmail());
                fName.setText(c.getFirstname());
                lName.setText(c.getLastname());
                bDate.setText(c.getBirthdate());
                cnp.setText(c.getCNP());
                client = c;
            }
        }
    }

}
