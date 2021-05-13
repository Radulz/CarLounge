package org.CarLounge.fis.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.services.ListingService;
import org.dizitart.no2.objects.ObjectRepository;

public class MyListingsController {

    @FXML
    public ListView<String> carList = new ListView<>();

    public MyListingsController() {}

    public void setCarList() {
        String s;
        String username=ProviderMenuController.getUsername();

        for(Listing listing: ListingService.ListingRepository.find()) {
            if (username.equals(listing.getProviderEmail())) {
                if (listing.getActive() && !listing.getCompleted()) {
                    s = "#ACTIVE ->" + listing.getNumberPlate() + " " + listing.getMake() + " " + listing.getModel() + " " + listing.getYear() + " " + listing.getFuel() + " " + listing.getCmc() + " " + listing.getMileage() + " " + listing.getPrice();
                    carList.getItems().add(s);
                }
                else if(!listing.getActive() && !listing.getCompleted()){
                    s = "#IN_PROGRESS ->" + listing.getNumberPlate() + " " + listing.getMake() + " " + listing.getModel() + " " + listing.getYear() + " " + listing.getFuel() + " " + listing.getCmc() + " " + listing.getMileage() + " " + listing.getPrice();
                    carList.getItems().add(s);
                }
                else if(listing.getCompleted()){
                    s = "#COMPLETED ->" + listing.getNumberPlate() + " " + listing.getMake() + " " + listing.getModel() + " " + listing.getYear() + " " + listing.getFuel() + " " + listing.getCmc() + " " + listing.getMileage() + " " + listing.getPrice();
                    carList.getItems().add(s);
                }
            }
        }
    }

}
