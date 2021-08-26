package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Client;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class ClientServiceTest {
    public static final String USER_GMAIL_COM = "user@gmail.com";
    public static final String PASSWORD = "Tare1234";
    public static final String CNP = "1231231231230";
    public static final String B_DATE = "01/02/2000";
    public static final String NUME_2 = "Nume2";
    public static final String NUME_1 = "Nume1";

    @BeforeAll
    static void beforeAll(){


    }
    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".testingCarLoungeDatabases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        ProviderService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        ClientService.closeDatabase();
        ProviderService.closeDatabase();
    }

   @Test
   @DisplayName ("Client database is initialized and there are no clients.")
    void testClientInitDB() {
        assertThat(ClientService.getAllClients()).isNotNull();
        assertThat(ClientService.getAllClients()).isEmpty();
    }

    @Test
    @DisplayName("Client added to the database")
    void testClientIsAdded() throws CnpAlreadyExists, CnpIsNotValid, BirthDateIsNotADate, LastNameFieldIsEmpty, UsernameAlreadyExistsException, MinimumAgeIsRequired, EmailFieldIsEmpty, PasswordsDoesNotMatch, FirstNameFieldIsEmpty, CnpIsMissing, TextIsNotAValidEmail, BirthDateFieldIsEmpty, ConfirmPasswordFieldIsEmpty, PasswordDoesNotContainTheRequiredCharacters, PasswordFieldIsEmpty {
        ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, CNP);
        assertThat(ClientService.getAllClients()).isNotEmpty();
        assertThat(ClientService.getAllClients()).size().isEqualTo(1);
        Client c= ClientService.getAllClients().get(0);
        assertThat(c).isNotNull();
        assertThat(c.getEmail()).isEqualTo(USER_GMAIL_COM);
        assertThat(c.getPassword()).isEqualTo(ClientService.encodePassword(USER_GMAIL_COM, PASSWORD));
        assertThat(c.getFirstname()).isEqualTo(NUME_1);
        assertThat(c.getLastname()).isEqualTo(NUME_2);
        assertThat(c.getBirthdate()).isEqualTo(B_DATE);
        assertThat(c.getCNP()).isEqualTo(CNP);
    }

    @Test
    @DisplayName("Client CNP already exists")
    void testClientCNPExists(){
        assertThrows(CnpAlreadyExists.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, CNP);
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, CNP);
        });

    }

    @Test
    @DisplayName("Client email already exists")
    void testClientEmailExists() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, CNP);
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, "1231231231238");
        });
    }

    @Test
    @DisplayName("Email is null")
    void testClientEmailNull() {
        assertThrows(EmailFieldIsEmpty.class, () -> {
            ClientService.addClient("", PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("Email is not valid")
    void testClientEmailValid() {
        assertThrows(TextIsNotAValidEmail.class, () -> {
            ClientService.addClient("user", PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("First name is null")
    void testFirstnameNull() {
        assertThrows(FirstNameFieldIsEmpty.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, "", NUME_2, B_DATE, PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("Last name is null")
    void testLastnameNull() {
        assertThrows(LastNameFieldIsEmpty.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_2, "", B_DATE, PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("Birth date is null")
    void testBirthDateNull() {
        assertThrows(BirthDateFieldIsEmpty.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, "", PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("Birth date is not valid")
    void testBirthDateNotValid() {
        assertThrows(BirthDateIsNotADate.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, "s", PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("18 years old")
    void testBirthDateGreater() {
        assertThrows(MinimumAgeIsRequired.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, "01/02/2008", PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("CNP null")
    void testCNPNull() {
        assertThrows(CnpIsMissing.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, "");

        });
    }

    @Test
    @DisplayName("CNP is not valid")
    void testCNPValid() {
        assertThrows(CnpIsNotValid.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD, "s");

        });
    }

    @Test
    @DisplayName("Password null")
    void testPasswordNull() {
        assertThrows(PasswordFieldIsEmpty.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, "", NUME_1, NUME_2, B_DATE, PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("Password doesn't meet requirements.")
    void testPasswordRequirements() {
        assertThrows(PasswordDoesNotContainTheRequiredCharacters.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, "tare", NUME_1, NUME_2, B_DATE, PASSWORD, CNP);

        });
    }

    @Test
    @DisplayName("Confirm password is null.")
    void testConfirmPassword() {
        assertThrows(ConfirmPasswordFieldIsEmpty.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, "", CNP);

        });
    }

    @Test
    @DisplayName("Passwords do not match.")
    void testMatchingPasswords() {
        assertThrows(PasswordsDoesNotMatch.class, () -> {
            ClientService.addClient(USER_GMAIL_COM, PASSWORD, NUME_1, NUME_2, B_DATE, PASSWORD + "5", CNP);

        });
    }

}