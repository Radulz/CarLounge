package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Provider;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProviderServiceTest {

    public static final String PROV_GMAIL_COM = "prov@gmail.com";
    public static final String PASSWORD = "Tare1234";
    public static final String FNAME = "fname";
    public static final String LNAME = "lname";
    public static final String B_DATE = "01/02/2000";
    public static final String CPASSWORD = "Tare1234";
    public static final String PHONE_NO = "0712312312";
    public static final String CNP = "1231231231256";


    public static final String USERNAME_YAHOO_COM = "username@yahoo.com";
    public static final String COMPANIE = "Companie";
    public static final String ADDRESS = "Street";
    public static final String PHONE = "0712312312";
    public static final String TAXREGNO = "CUI123";

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
    }

    @AfterEach
    void tearDown() {
        ProviderService.closeDatabase();
        ClientService.closeDatabase();
    }

    @Test
    @DisplayName ("Provider database is initialized and there are no providers.")
    void testProviderInitDB() {

        assertThat(ProviderService.getAllProviders()).isNotNull();
        assertThat(ProviderService.getAllProviders()).isEmpty();
    }

    @Test
    @DisplayName("Individual added to the database")
    void testProviderIsAdded() throws CnpAlreadyExists, CnpIsNotValid, BirthDateIsNotADate, LastNameFieldIsEmpty, UsernameAlreadyExistsException, PhoneNumberIsMissing, PasswordsDoesNotMatch, MinimumAgeIsRequired, FirstNameFieldIsEmpty, PasswordDoesNotContainTheRequiredCharacters, CnpIsMissing, InvalidPhoneNumber, TextIsNotAValidEmail, BirthDateFieldIsEmpty, ConfirmPasswordFieldIsEmpty, EmailFieldIsEmpty, PasswordFieldIsEmpty {
        IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, CPASSWORD, PHONE_NO, CNP);
        assertThat(ProviderService.getAllProviders()).isNotEmpty();
        assertThat(ProviderService.getAllProviders()).size().isEqualTo(1);
        Provider c= ProviderService.getAllProviders().get(0);
        assertThat(c).isNotNull();
        assertThat(c.getEmail()).isEqualTo(PROV_GMAIL_COM);
        assertThat(c.getPassword()).isEqualTo(ClientService.encodePassword(PROV_GMAIL_COM, PASSWORD));
        assertThat(c.getFirstName()).isEqualTo(FNAME);
        assertThat(c.getLastName()).isEqualTo(LNAME);
        assertThat(c.getBDate()).isEqualTo(B_DATE);
        assertThat(c.getPhone()).isEqualTo(PHONE_NO);
        assertThat(c.getCnp()).isEqualTo(CNP);
    }

    @Test
    @DisplayName("Provider CNP already exists")
    void testProviderCNPExists(){
        assertThrows(CnpAlreadyExists.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, CNP);
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, CNP);
        });

    }

    @Test
    @DisplayName("Provider email already exists")
    void testProviderEmailExists(){
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, CNP);
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, "1231233213210");
        });

    }

    @Test
    @DisplayName("Email is null")
    void testClientEmailNull() {
        assertThrows(EmailFieldIsEmpty.class, () -> {
            IndividualProviderService.addProvider("", PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("Email is not valid")
    void testIndividualEmailValid() {
        assertThrows(TextIsNotAValidEmail.class, () -> {
            IndividualProviderService.addProvider("us", PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("First name is null")
    void testFirstnameNull() {
        assertThrows(FirstNameFieldIsEmpty.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, "", LNAME, B_DATE, PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("Last name is null")
    void testLastnameNull() {
        assertThrows(LastNameFieldIsEmpty.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, "", B_DATE, PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("Birth date is null")
    void testBirthDateNull() {
        assertThrows(BirthDateFieldIsEmpty.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, "", PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("Birth date is not valid")
    void testBirthDateNotValid() {
        assertThrows(BirthDateIsNotADate.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, "s", PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("18 years old")
    void testBirthDateGreater() {
        assertThrows(MinimumAgeIsRequired.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, "01/02/2008", PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("Phone null")
    void testPhoneNull() {
        assertThrows(PhoneNumberIsMissing.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, "", CNP);

        });
    }

    @Test
    @DisplayName("Phone not valid")
    void testPhoneValid() {
        assertThrows(InvalidPhoneNumber.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, "1235", CNP);

        });
    }

    @Test
    @DisplayName("CNP null")
    void testCNPNull() {
        assertThrows(CnpIsMissing.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, "");

        });
    }

    @Test
    @DisplayName("CNP is not valid")
    void testCNPValid() {
        assertThrows(CnpIsNotValid.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, "s");

        });
    }

    @Test
    @DisplayName("Password null")
    void testPasswordNull() {
        assertThrows(PasswordFieldIsEmpty.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, "", FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("Password doesn't meet requirements.")
    void testPasswordRequirements() {
        assertThrows(PasswordDoesNotContainTheRequiredCharacters.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, "tare", FNAME, LNAME, B_DATE, PASSWORD, PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("Confirm password is null.")
    void testConfirmPassword() {
        assertThrows(ConfirmPasswordFieldIsEmpty.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, "", PHONE_NO, CNP);

        });
    }

    @Test
    @DisplayName("Passwords do not match.")
    void testMatchingPasswords() {
        assertThrows(PasswordsDoesNotMatch.class, () -> {
            IndividualProviderService.addProvider(PROV_GMAIL_COM, PASSWORD, FNAME, LNAME, B_DATE, PASSWORD + "5", PHONE_NO, CNP);

        });
    }

    //Testing for Legal Person


    @Test
    @DisplayName("Legal Person added to the database")
    void testProviderLIsAdded() throws PasswordDoesNotContainTheRequiredCharacters, EmailFieldIsEmpty, UsernameAlreadyExistsException, TextIsNotAValidEmail, PasswordsDoesNotMatch, TaxRegNoIsMissing, ConfirmPasswordFieldIsEmpty, PasswordFieldIsEmpty, AddressIsMissing, InvalidPhoneNumber, TaxRegNoAlreadyExists, PhoneNumberIsMissing, CompanyNameIsMissing {
        LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD);
        assertThat(ProviderService.getAllProviders()).isNotEmpty();
        assertThat(ProviderService.getAllProviders()).size().isEqualTo(1);
        Provider c= ProviderService.getAllProviders().get(0);
        assertThat(c).isNotNull();
        assertThat(c.getEmail()).isEqualTo(USERNAME_YAHOO_COM);
        assertThat(c.getPassword()).isEqualTo(ClientService.encodePassword(USERNAME_YAHOO_COM, PASSWORD));
        assertThat(c.getCompanyName()).isEqualTo(COMPANIE);
        assertThat(c.getAddress()).isEqualTo(ADDRESS);
        assertThat(c.getPhone()).isEqualTo(PHONE);
        assertThat(c.getTaxRegNo()).isEqualTo(TAXREGNO);
    }

    @Test
    @DisplayName("Provider TAX already exists")
    void testProviderTAXExists(){
        assertThrows(TaxRegNoAlreadyExists.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD);
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD);
        });

    }

    @Test
    @DisplayName("Legal email already exists")
    void testProviderLEmailExists(){
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD);
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, PHONE, "CUI", PASSWORD);
        });

    }

    @Test
    @DisplayName("Email is null")
    void testProviderLEmailNull() {
        assertThrows(EmailFieldIsEmpty.class, () -> {
            LegalPersonProviderService.addProvider("", PASSWORD, COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD);

        });
    }

    @Test
    @DisplayName("Email is not valid")
    void testLegalEmailValid() {
        assertThrows(TextIsNotAValidEmail.class, () -> {
            LegalPersonProviderService.addProvider("US", PASSWORD, COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD);

        });
    }

    @Test
    @DisplayName("Password null")
    void testLegalPasswordNull() {
        assertThrows(PasswordFieldIsEmpty.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, "", COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD);

        });
    }

    @Test
    @DisplayName("Password doesn't meet requirements.")
    void testLegalPasswordRequirements() {
        assertThrows(PasswordDoesNotContainTheRequiredCharacters.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, "s", COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD);

        });
    }

    @Test
    @DisplayName("Confirm password is null.")
    void testLegalConfirmPassword() {
        assertThrows(ConfirmPasswordFieldIsEmpty.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, PHONE, TAXREGNO, "");

        });
    }

    @Test
    @DisplayName("Passwords do not match.")
    void testLegalMatchingPasswords() {
        assertThrows(PasswordsDoesNotMatch.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, PHONE, TAXREGNO, PASSWORD + "123");

        });
    }

    @Test
    @DisplayName("Company null")
    void testCompanyNull() {
        assertThrows(CompanyNameIsMissing.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, "", ADDRESS, PHONE, TAXREGNO, PASSWORD );

        });
    }

    @Test
    @DisplayName("Address null")
    void testAddressNull() {
        assertThrows(AddressIsMissing.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, "", PHONE, TAXREGNO, PASSWORD );

        });
    }

    @Test
    @DisplayName("Phone null")
    void testLegalPhoneNull() {
        assertThrows(PhoneNumberIsMissing.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, "", TAXREGNO, PASSWORD);

        });
    }

    @Test
    @DisplayName("Phone not valid")
    void testLegalPhoneValid() {
        assertThrows(InvalidPhoneNumber.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, "07", TAXREGNO, PASSWORD);

        });
    }

    @Test
    @DisplayName("Tax null")
    void testTaxNull() {
        assertThrows(TaxRegNoIsMissing.class, () -> {
            LegalPersonProviderService.addProvider(USERNAME_YAHOO_COM, PASSWORD, COMPANIE, ADDRESS, PHONE, "", PASSWORD);

        });
    }


}