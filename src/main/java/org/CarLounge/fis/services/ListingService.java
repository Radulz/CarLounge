package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Listing;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import static java.lang.Integer.parseInt;

public class ListingService {

    public static ObjectRepository<Listing> ListingRepository;

    private static void checkFields(String make, String model, String year, String mileage, String cmc, String fuel, String price) throws MakeIsMissing, ModelIsMissing, YearIsMissing, YearIsNotValid, MileageIsMissing, MileageIsNotValid, CubicIsMissing, FuelIsMissing, PriceIsMissing {
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
        else if(price == ""){
            throw new PriceIsMissing();
        }

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

    public static void addListing(String clientEmail, String providerEmail, String make, String model, String year, String mileage, String cmc, String fuel, String price) throws MakeIsMissing, ModelIsMissing, YearIsMissing, YearIsNotValid, MileageIsMissing, MileageIsNotValid, CubicIsMissing, FuelIsMissing, PriceIsMissing {
        checkFields(make, model, year, mileage, cmc, fuel, price);
        Listing l = new Listing(clientEmail, providerEmail, make, model, parseInt(year), parseInt(mileage), parseInt(cmc), fuel, price);
        l.setActive(true);
        ListingRepository.insert(l);
    }

    public static ObjectRepository<Listing> getListingRepository(){
        return ListingRepository;
    }
}
