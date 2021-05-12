package org.CarLounge.fis.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.services.ListingService;
import org.dizitart.no2.objects.ObjectRepository;

public class ActiveListingController {
    @FXML
    public ListView carList;

    public static ObjectRepository<Listing> ListingRepository;

    public ActiveListingController() {}

    public void setCarList() {
        String s;

        for(Listing listing: ListingService.ListingRepository.find()) {
            s = listing.getMake() + " " + listing.getModel() + " " + listing.getYear() + " " + listing.getFuel() + " " + listing.getCmc() + " " + listing.getMileage() + " " + listing.getPrice();
            carList.getItems().add(s);
        }
    }
}
