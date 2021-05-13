package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ListingService;
import org.CarLounge.fis.services.ProviderService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProviderProfileController implements Initializable {
    @FXML
    public StackPane contentArea;
    @FXML
    public TextField fName;
    @FXML
    public TextField lName;
    @FXML
    public TextField email;
    @FXML
    public TextField phoneNo;
    @FXML
    public TextField bDate;
    @FXML
    public TextField company;
    @FXML
    public TextField address;
    @FXML
    public TextField taxRegNo;
    @FXML
    public TextField activeListings;
    @FXML
    public TextField inactiveListings;
    @FXML
    public TextField feedbackScore;
    @FXML
    public JFXButton deleteAccount;
    @FXML
    public TextField cnp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LogInController user = new LogInController();
        String username = ProviderMenuController.getUsername();
        int cr = 0, ar = 0;
        boolean sw = false;
        Provider provider = new Provider();
        for (Provider p : ProviderService.getProviderRepository().find()) {
            if (username.equals(p.getEmail())) {
                email.setText(p.getEmail());
                fName.setText(p.getFirstName());
                lName.setText(p.getLastName());
                phoneNo.setText(p.getPhone());
                bDate.setText(p.getBDate());
                company.setText(p.getCompanyName());
                address.setText(p.getAddress());
                taxRegNo.setText(p.getTaxRegNo());
                feedbackScore.setText(String.format("%.2f", p.getFeedback()));
                cnp.setText(p.getCnp());
                provider = p;
                sw = true;
            }
        }
        if (sw) {
            for (Listing l : ListingService.getListingRepository().find()) {
                if (provider.getEmail().equals(l.getProviderEmail())) {
                    if (l.getCompleted()) {
                        cr++;
                    }
                    if (l.getActive()) {
                        ar++;
                    }
                }
            }
            inactiveListings.setText("" + cr);
            activeListings.setText("" + ar);
        }
    }
}

