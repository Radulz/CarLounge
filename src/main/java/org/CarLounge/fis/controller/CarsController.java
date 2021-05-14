package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.CarLounge.fis.exceptions.ActiveRentalAlreadyExists;
import org.CarLounge.fis.exceptions.NumberPlateExistence;
import org.CarLounge.fis.exceptions.NumberPlateIsMissing;
import org.CarLounge.fis.exceptions.NumberPlateIsNotValid;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ListingService;
import org.CarLounge.fis.services.ProviderService;
import org.dizitart.no2.objects.ObjectRepository;

import javax.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.List;


public class CarsController {
    @FXML
    public ListView<String> carList = new ListView<>();
    @FXML
    public Text rentalMessage;
    @FXML
    public Text contactDetails;
    @FXML
    public JFXButton rent;
    @FXML
    public TextField noPlate;

    private List<String> cars = new ArrayList<>();

    public CarsController() {}


    public void setCarList() {
        String s;
        String ratingMessage="";
        assignCars();
        for(Listing listing: ListingService.ListingRepository.find()) {
            if (listing.getActive()) {
                for(Provider p : ProviderService.ProviderRepository.find()){
                    if(p.getEmail().equals(listing.getProviderEmail())){
                        if(p.getFeedback()==0){
                            ratingMessage="Unrated.";
                        }
                        else {
                            ratingMessage=String.format("%.2f", p.getFeedback());
                        }
                    }
                }
                s = listing.getNumberPlate() + " " + listing.getMake() + " " + listing.getModel() + " " + listing.getYear() + " " + listing.getFuel() + " " + listing.getCmc() + " " + listing.getMileage() + " " + listing.getPrice() + " Rating: " + ratingMessage;
                carList.getItems().add(s);
            }
        }

    }

    public void assignCars(){
        for(Listing l : ListingService.ListingRepository.find()){
            if(l.getActive()){
                cars.add(l.getNumberPlate());
            }
        }
    }

    public boolean checkExistence(String noPlate){
        for(Listing l : ListingService.ListingRepository.find()){
            if(l.getActive()){
                if(l.getNumberPlate().equals(noPlate)){
                    return true;
                }
            }
        }
        return false;
    }

    public void checkFields(String noPlate) throws NumberPlateIsMissing, NumberPlateIsNotValid, NumberPlateExistence {
        if(noPlate == ""){
            throw new NumberPlateIsMissing();
        }
        else if(!isValidNoPlate(noPlate)){
            throw new NumberPlateIsNotValid();
        }
        else if(!checkExistence(noPlate)){
            throw new NumberPlateExistence();
        }
    }

    public void checkActiveRentals(String client) throws ActiveRentalAlreadyExists {
        for(Listing l : ListingService.ListingRepository.find()){
            if(l.getClientEmail().equals(client) && !l.getCompleted()){
                throw new ActiveRentalAlreadyExists();
            }
        }
    }

    private static boolean isValidNoPlate(String noPlate){

        if(noPlate.length() < 7 || noPlate.length() > 8) {
            return false;
        }

        return true;
    }

    public void rentACar(MouseEvent mouseEvent) {
        String number = noPlate.getText();
        String useremail="", userphone="";
        try{
            checkFields(number);
            checkActiveRentals(ClientMenuController.getUsername());
            for(Listing l : ListingService.ListingRepository.find()){
                if(number.equals(l.getNumberPlate())){
                    l.setActive(false);
                    l.setClientEmail(ClientMenuController.getUsername());
                    ListingService.ListingRepository.update(l);
                    useremail=l.getProviderEmail();
                    userphone=l.getProviderPhone();
                }

            }
            rentalMessage.setText("Rental successful!");
            contactDetails.setText(String.format("Contact the provider\nfor more details\nemail: %s \nphone: %s", useremail, userphone));
        }
        catch(NumberPlateIsMissing e){
            rentalMessage.setText(e.getMessage());
            contactDetails.setText("");
        }
        catch(NumberPlateIsNotValid e){
            rentalMessage.setText(e.getMessage());
            contactDetails.setText("");
        }
        catch (NumberPlateExistence e){
            rentalMessage.setText(e.getMessage());
            contactDetails.setText("");
        }
        catch (ActiveRentalAlreadyExists e){
            rentalMessage.setText(e.getMessage());
            contactDetails.setText("");
        }
    }
}
