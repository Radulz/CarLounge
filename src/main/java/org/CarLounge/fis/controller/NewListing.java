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

public class NewListing {
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
    private JFXButton switchToHome;
    @FXML
    private StackPane contentArea;

    public void addCar(MouseEvent mouseEvent) {

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("login1.fxml"));

        LogInController user = new LogInController();
        try{
            ListingService.addListing("-", ProviderMenuController.getUsername(), make.getText(), model.getText(), year.getText(), mileage.getText(), cmc.getText(), fuel.getText(),price.getText());
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
        }
        catch(MileageIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch(MileageIsNotValid e){
            listingText.setText(e.getMessage());
        }
        catch(CubicIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch(FuelIsMissing e){
            listingText.setText(e.getMessage());
        }
        catch(PriceIsMissing e){
            listingText.setText(e.getMessage());
        }
    }

    public void home(MouseEvent event) throws Exception{
        Parent fxml = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }


}
