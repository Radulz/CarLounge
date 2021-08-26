package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Listing;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ListingServiceTest {

    public static final String CLIENT_EMAIL = "-";
    public static final String PROVIDER_EMAIL = "provider@yahoo.com";
    public static final String MAKE = "BMW";
    public static final String MODEL = "320d";
    public static final String YEAR = "2010";
    public static final String MILEAGE = "195000";
    public static final String CMC = "1969";
    public static final String FUEL = "Diesel";
    public static final String PRICE = "26";
    public static final String NUMBER_PLATE = "AR25RON";

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".testingCarLoungeDatabases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ProviderService.initDatabase();
        ClientService.initDatabase();
        ListingService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        ListingService.closeDatabase();
        ProviderService.closeDatabase();
        ClientService.closeDatabase();
    }

    @Test
    @DisplayName ("Listing database is initialized and there are no listings.")
    void testListingInitDB() {
        assertThat(ListingService.getAllListings()).isNotNull();
        assertThat(ListingService.getAllListings()).isEmpty();
    }

    @Test
    @DisplayName("Rental added to the database")
    void testRentalIsAdded() throws NumberPlateIsMissing, CubicIsMissing, ModelIsMissing, YearIsNotValid, YearIsMissing, MakeIsMissing, FuelIsMissing, ActiveListingAlreadyExists, FuelIsNotAccepted, MileageIsMissing, CmcNotValid, PriceIsMissing, PriceIsNotANumber, NumberPlateIsNotValid, MileageIsNotValid {
        ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, FUEL, PRICE, NUMBER_PLATE);
        assertThat(ListingService.getAllListings()).isNotEmpty();
        assertThat(ListingService.getAllListings()).size().isEqualTo(1);
        Listing c= ListingService.getAllListings().get(0);
        assertThat(c).isNotNull();
        assertThat(c.getClientEmail()).isEqualTo(CLIENT_EMAIL);
        assertThat(c.getProviderEmail()).isEqualTo(PROVIDER_EMAIL);
        assertThat(c.getMake()).isEqualTo(MAKE);
        assertThat(c.getModel()).isEqualTo(MODEL);
        assertThat(c.getYear()).isEqualTo(Integer.parseInt(YEAR));
        assertThat(c.getMileage()).isEqualTo(Integer.parseInt(MILEAGE));
        assertThat(c.getCmc()).isEqualTo(Integer.parseInt(CMC));
        assertThat(c.getFuel()).isEqualTo(FUEL);
        assertThat(c.getPrice()).isEqualTo(PRICE);
        assertThat(c.getNumberPlate()).isEqualTo(NUMBER_PLATE);
    }

    @Test
    @DisplayName("Listing Numberplate already exists")
    void testListingExists(){
        assertThrows(ActiveListingAlreadyExists.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, FUEL, PRICE, NUMBER_PLATE);
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, FUEL, PRICE, NUMBER_PLATE);
        });

    }

    @Test
    @DisplayName("Make is null")
    void testListingMakeNull() {
        assertThrows(MakeIsMissing.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, "", MODEL, YEAR, MILEAGE, CMC, FUEL, PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Model is null")
    void testModelNull() {
        assertThrows(ModelIsMissing.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, "", YEAR, MILEAGE, CMC, FUEL, PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Year null")
    void testYearNull() {
        assertThrows(YearIsMissing.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, "", MILEAGE, CMC, FUEL, PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Year is not valid.")
    void testYearIsNotValid() {
        assertThrows(YearIsNotValid.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, "s", MILEAGE, CMC, FUEL, PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Mileage is null")
    void testBirthDateNull() {
        assertThrows(MileageIsMissing.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, "", CMC, FUEL, PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Mileage not valid")
    void testMileageNotValid() {
        assertThrows(MileageIsNotValid.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, "s", CMC, FUEL, PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("CMC is null")
    void testCMCIsNull() {
        assertThrows(CubicIsMissing.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, "", FUEL, PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("CMC is not valid")
    void testCMCNotValid() {
        assertThrows(CmcNotValid.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, "s", FUEL, PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Fuel is null")
    void testFuelNull() {
        assertThrows(FuelIsMissing.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, "", PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Fuel is not accepted")
    void testFuelIsNotValid() {
        assertThrows(FuelIsNotAccepted.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, "Pacura", PRICE, NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Price is null")
    void testPriceNull() {
        assertThrows(PriceIsMissing.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, FUEL, "", NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Price is not a number.")
    void testPriceNotNumber() {
        assertThrows(PriceIsNotANumber.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, FUEL, "s", NUMBER_PLATE);

        });
    }

    @Test
    @DisplayName("Number plate is missing")
    void testNumberPlateNull() {
        assertThrows(NumberPlateIsMissing.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, FUEL, PRICE, "");

        });
    }

    @Test
    @DisplayName("Number plate is missing")
    void testNumberPlateNotValid() {
        assertThrows(NumberPlateIsNotValid.class, () -> {
            ListingService.addListing(CLIENT_EMAIL, PROVIDER_EMAIL, MAKE, MODEL, YEAR, MILEAGE, CMC, FUEL, PRICE, "s");

        });
    }
}