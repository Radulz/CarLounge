package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.services.ListingService;
import org.dizitart.no2.objects.ObjectRepository;

public class ActiveListingController {
    @FXML
    public ListView<String> carList = new ListView<>();
    @FXML
    public JFXButton completeRental;
    @FXML
    public JFXButton submitFeedback;
    @FXML
    public TextField activeListingField;
    @FXML
    public TextField feedbackText;
    @FXML
    public RadioButton rb1;
    @FXML
    public RadioButton rb2;
    @FXML
    public RadioButton rb3;
    @FXML
    public RadioButton rb4;
    @FXML
    public RadioButton rb5;

    public ActiveListingController() {}

    public void setCarList() {
        String s;
        String username = ClientMenuController.getUsername();
        for(Listing listing: ListingService.ListingRepository.find()) {
            if (username.equals(listing.getClientEmail()) && listing.getCompleted()) {
                s = listing.getMake() + " " + listing.getModel() + " " + listing.getYear() + " " + listing.getFuel() + " " + listing.getCmc() + " " + listing.getMileage() + " " + listing.getPrice();
                carList.getItems().add(s);
            }
        }
    }
}
