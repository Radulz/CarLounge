package org.CarLounge.fis.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.services.ListingService;

import static java.lang.Integer.parseInt;

public class NewListingController {
    @FXML
    public TextField make;
    @FXML
    public TextField model;
    @FXML
    public TextField year;
    @FXML
    public TextField mileage;
    @FXML
    public TextField cmc;
    @FXML
    public TextField fuel;
    @FXML
    public Text listingText;
    @FXML
    public TextField price;
    @FXML
    public TextField noPlate;
    @FXML
    private JFXButton switchToHome;
    @FXML
    private StackPane contentArea;

    public void addCar(MouseEvent mouseEvent) {

        LogInController user = new LogInController();
        try{
            ListingService.addListing("-", ProviderMenuController.getUsername(), make.getText(), model.getText(), year.getText(), mileage.getText(), cmc.getText(), fuel.getText(), price.getText(), noPlate.getText());
            listingText.setText("Listing added successfully under email: " + ProviderMenuController.getUsername());
        }
        catch(MakeIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch(ModelIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch (YearIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch(YearIsNotValid e){
            listingText.setText(e.getMessage());
            year.clear();
        }
        catch(MileageIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch(MileageIsNotValid e){
            listingText.setText(e.getMessage());
            mileage.clear();
        }
        catch(CubicIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch(CmcNotValid e){
            listingText.setText(e.getMessage());
            cmc.clear();
        }
        catch(FuelIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch (FuelIsNotAccepted e){
            listingText.setText(e.getMessage());
            fuel.clear();
        }
        catch(NumberPlateIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch(NumberPlateIsNotValid e){
            listingText.setText(e.getMessage());
            noPlate.clear();
        }
        catch(ActiveListingAlreadyExists e){
            listingText.setText(e.getMessage());
        }
        catch(PriceIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch (PriceIsNotANumber e){
            listingText.setText(e.getMessage());
            price.clear();
        }
    }

    public void home(MouseEvent event) throws Exception{
        Parent fxml = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }


}
