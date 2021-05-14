package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.CarLounge.fis.exceptions.NoActiveRental;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ListingService;
import org.CarLounge.fis.services.ProviderService;
import org.dizitart.no2.objects.ObjectRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class ActiveListingController implements Initializable {
    @FXML
    public ListView<String> carList = new ListView<>();
    @FXML
    public JFXButton completeRental;
    @FXML
    public TextField activeListingField;
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
    @FXML
    public Text completeText;

    private ToggleGroup rb = new ToggleGroup();
    private Listing completeListing;
    String providerEmail;

    public ActiveListingController() {}

    public void setCarList() {
        String s;
        String username = ClientMenuController.getUsername();
        for(Listing listing: ListingService.ListingRepository.find()) {
            if (username.equals(listing.getClientEmail()) && listing.getCompleted()) {
                s = listing.getProviderEmail() + ": " + listing.getNumberPlate() + " " + listing.getMake() + " " + listing.getModel() + " " + listing.getYear() + " " + listing.getFuel() + " " + listing.getCmc() + " " + listing.getMileage() + " " + listing.getPrice();
                carList.getItems().add(s);
            }

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        activeListingField.setText("");
        String username = ClientMenuController.getUsername();
        String s="";
        rb1.setToggleGroup(rb);
        rb2.setToggleGroup(rb);
        rb3.setToggleGroup(rb);
        rb4.setToggleGroup(rb);
        rb5.setToggleGroup(rb);
        for(Listing l : ListingService.ListingRepository.find()){
            if(l.getClientEmail().equals(username) && !l.getCompleted()){
                s = l.getNumberPlate() + " " + l.getMake() + " " + l.getModel() + " " + l.getYear() + " " + l.getFuel() + " " + l.getFuel() + " " + l.getCmc() + " " + l.getMileage() + " " + l.getPrice();
                activeListingField.setText(s);
                completeListing = l;
                providerEmail = l.getProviderEmail();
                break;
            }
        }
    }

    public void checkNull(String text) throws NoActiveRental {
        if(text == "") {
            throw new NoActiveRental();
        }
    }

    public void completeListing(MouseEvent mouseEvent) {
        double value = 0;
        double feedbackAvg = 0;

        if (rb1.isSelected()) {
            value = 1;
        } else if (rb2.isSelected()) {
            value = 2;
        } else if (rb3.isSelected()) {
            value = 3;
        } else if (rb4.isSelected()) {
            value = 4;
        } else if (rb5.isSelected()) {
            value = 5;
        }

        try {
            checkNull(activeListingField.getText());
            if (value != 0) {
                for (Provider p : ProviderService.ProviderRepository.find()) {
                    if (p.getEmail().equals(providerEmail)) {
                        p.getFeedbackMarks().add(value);
                        feedbackAvg=p.getSum();
                        p.setFeedback(feedbackAvg / p.getFeedbackMarks().size());
                        ProviderService.ProviderRepository.update(p);
                        break;
                    }

                }
            }

            completeListing.setCompleted(true);
            ListingService.ListingRepository.update(completeListing);
            activeListingField.setText("");

            if (value == 0) {
                completeText.setText("Listing marked as completed successfully!");
            } else {
                completeText.setText("Listing marked as completed successfully! Thank you for your feedback!");
            }
        }
        catch (NoActiveRental e) {
            completeText.setText(e.getMessage());
        }
    }
}
