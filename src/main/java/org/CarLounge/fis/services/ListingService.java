package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Listing;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import static java.lang.Integer.parseInt;

public class ListingService {

    public static ObjectRepository<Listing> ListingRepository;

    private static void checkFields(String make, String model, String year, String mileage, String cmc, String fuel, String price, String noPlate) throws MakeIsMissing, ModelIsMissing, YearIsMissing, YearIsNotValid, MileageIsMissing, MileageIsNotValid, CubicIsMissing, FuelIsMissing, PriceIsMissing, NumberPlateIsMissing, NumberPlateIsNotValid, ActiveListingAlreadyExists {
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
        else if(fuel == ""){
            throw new FuelIsMissing();
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

    private static boolean mileageValidation(String mileage){
        int mil = parseInt(mileage);
        return mil >= 0 && mil <= 1000000;
    }
    private static boolean yearValidation(String year){
        int y=parseInt(year);
        return y >=1900 && y <= 2021;
    }

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Listing.db").toFile())
                .openOrCreate("cars", "cars");

        ListingRepository = database.getRepository(Listing.class);
    }

    public static void addListing(String clientEmail, String providerEmail, String make, String model, String year, String mileage, String cmc, String fuel, String price, String numberPlate) throws MakeIsMissing, ModelIsMissing, YearIsMissing, YearIsNotValid, MileageIsMissing, MileageIsNotValid, CubicIsMissing, FuelIsMissing, PriceIsMissing, NumberPlateIsMissing, NumberPlateIsNotValid, ActiveListingAlreadyExists {
        checkFields(make, model, year, mileage, cmc, fuel, price, numberPlate);
        Listing l = new Listing(clientEmail, providerEmail, make, model, parseInt(year), parseInt(mileage), parseInt(cmc), fuel, price, numberPlate);
        l.setActive(true);
        ListingRepository.insert(l);
    }

    public static ObjectRepository<Listing> getListingRepository(){
        return ListingRepository;
    }
}
