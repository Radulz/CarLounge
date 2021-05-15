package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Listing;
import org.CarLounge.fis.model.Provider;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;

import static java.lang.Integer.parseInt;

public class ListingService {

    public static ObjectRepository<Listing> ListingRepository;

    public static List<Listing> getAllListings(){
        return ListingRepository.find().toList();
    }

    private static void checkFields(String make, String model, String year, String mileage, String cmc, String fuel, String price, String noPlate) throws MakeIsMissing, ModelIsMissing, YearIsMissing, YearIsNotValid, MileageIsMissing, MileageIsNotValid, CubicIsMissing, FuelIsMissing, PriceIsMissing, NumberPlateIsMissing, NumberPlateIsNotValid, ActiveListingAlreadyExists, PriceIsNotANumber, CmcNotValid, FuelIsNotAccepted {
        if(make == ""){
            throw new MakeIsMissing();
        }
        else if(model == ""){
            throw new ModelIsMissing();
        }
        else if(year == ""){
            throw new YearIsMissing();
        }
        else if(!yearValidation(year)){
            throw new YearIsNotValid();
        }
        else if(mileage == ""){
            throw new MileageIsMissing();
        }
        else if(!mileageValidation(mileage)){
            throw new MileageIsNotValid();
        }
        else if(cmc == ""){
            throw new CubicIsMissing();
        }
        else if(!isValidCmc(cmc)){
            throw new CmcNotValid();
        }
        else if(fuel == ""){
            throw new FuelIsMissing();
        }
        else if(!checkFuel(fuel)){
            throw new FuelIsNotAccepted();
        }
        else if(noPlate == ""){
            throw new NumberPlateIsMissing();
        }
        else if(!isValidNoPlate(noPlate)){
            throw new NumberPlateIsNotValid();
        }
        else if(!checkActiveListing(noPlate)){
            throw new ActiveListingAlreadyExists(noPlate);
        }
        else if(price == ""){
            throw new PriceIsMissing();
        }
        else if(!isNumeric(price)){
            throw new PriceIsNotANumber();
        }

    }

    private static boolean isNumeric(String price){
        try {
            int d = Integer.parseInt(price);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static boolean checkActiveListing(String noPlate){
        for(Listing l : ListingRepository.find()){
            if(noPlate.equals(l.getNumberPlate())){
                if(l.getActive()){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidNoPlate(String noPlate){

        if(noPlate.length() < 7 || noPlate.length() > 8) {
            return false;
        }

        return true;
    }

    private static boolean isValidCmc(String cmc){
        if(!isNumeric(cmc)){
            return false;
        }
        int c = parseInt(cmc);
        return c >= 100 && c <= 20000;
    }

    private static boolean mileageValidation(String mileage){
        if(!isNumeric(mileage)){
            return false;
        }
        int mil = parseInt(mileage);
        return mil >= 0 && mil <= 1000000;
    }
    private static boolean yearValidation(String year){
        if(!isNumeric(year)){
            return false;
        }
        int y=parseInt(year);
        return y >=1900 && y <= 2021;
    }

    private static boolean checkFuel(String fuel){
        String[] acceptedFuel = {"Petrol", "Gasoline", "Diesel", "Gas", "Electric", "Hybrid", "Ethanol", "LPG", "GPL"};
        if(isNumeric(fuel)){
            return false;
        }
        for(String sir : acceptedFuel){
            if(sir.equals(fuel)){
                return true;
            }
        }
        return false;
    }

    public static void initDatabase() {
        FileSystemService.initDirectory();
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Listing.db").toFile())
                .openOrCreate("cars", "cars");

        ListingRepository = database.getRepository(Listing.class);
    }

    public static void addListing(String clientEmail, String providerEmail, String make, String model, String year, String mileage, String cmc, String fuel, String price, String numberPlate) throws MakeIsMissing, ModelIsMissing, YearIsMissing, YearIsNotValid, MileageIsMissing, MileageIsNotValid, CubicIsMissing, FuelIsMissing, PriceIsMissing, NumberPlateIsMissing, NumberPlateIsNotValid, ActiveListingAlreadyExists, PriceIsNotANumber, CmcNotValid, FuelIsNotAccepted {
        checkFields(make, model, year, mileage, cmc, fuel, price, numberPlate);
        Listing l = new Listing(clientEmail, providerEmail, make, model, parseInt(year), parseInt(mileage), parseInt(cmc), fuel, price, numberPlate);
        l.setActive(true);
        for(Provider p : ProviderService.ProviderRepository.find()){
            if(p.getEmail().equals(providerEmail)){
                l.setProviderPhone(p.getPhone());
            }
        }
        ListingRepository.insert(l);
    }

    public static ObjectRepository<Listing> getListingRepository(){
        return ListingRepository;
    }
}
