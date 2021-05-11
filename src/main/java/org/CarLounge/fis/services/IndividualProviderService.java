package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Provider;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class IndividualProviderService extends ProviderService {

    private static void checkFields(String email, String password, String fName, String lName, String bDate, String confirmPassword) throws EmailFieldIsEmpty, PasswordFieldIsEmpty, FirstNameFieldIsEmpty, LastNameFieldIsEmpty, BirthDateFieldIsEmpty, ConfirmPasswordFieldIsEmpty, PasswordsDoesNotMatch, PasswordDoesNotContainTheRequiredCharacters, TextIsNotAValidEmail, MinimumAgeIsRequired, BirthDateIsNotADate {
        if(email == ""){
            throw new EmailFieldIsEmpty();
        }
        else if(!checkEmail(email)){
            throw new TextIsNotAValidEmail();
        }
        else if(fName == ""){
            throw new FirstNameFieldIsEmpty();
        }
        else if(lName == ""){
            throw new LastNameFieldIsEmpty();
        }
        else if(bDate == ""){
            throw new BirthDateFieldIsEmpty();
        }
        else if(!isValid(bDate)){
            throw new BirthDateIsNotADate();
        }
        else if(!checkDate(bDate)){
            throw new MinimumAgeIsRequired();
        }
        else if(password == ""){
            throw new PasswordFieldIsEmpty();
        }
        else if(!checkPassword(password)){
            throw new PasswordDoesNotContainTheRequiredCharacters();
        }
        else if(confirmPassword == ""){
            throw new ConfirmPasswordFieldIsEmpty();
        }
        else if(!password.equals(confirmPassword)){
            throw new PasswordsDoesNotMatch();
        }
    }

    public static void addProvider(String email, String password, String fName, String lName, String bDate, String confirmPassword) throws UsernameAlreadyExistsException, EmailFieldIsEmpty, PasswordFieldIsEmpty, FirstNameFieldIsEmpty, LastNameFieldIsEmpty, BirthDateFieldIsEmpty, ConfirmPasswordFieldIsEmpty, PasswordsDoesNotMatch, PasswordDoesNotContainTheRequiredCharacters, TextIsNotAValidEmail, MinimumAgeIsRequired, BirthDateIsNotADate {
        checkFields(email, password, fName, lName, bDate, confirmPassword);
        checkUserDoesNotAlreadyExist(email);
        Provider p= new Provider(email, encodePassword(email, password), fName, lName, bDate, "IndividualPerson", "IndividualPerson", "IndividualPerson", "IndividualPerson");
        ProviderRepository.insert(p);
    }


    public static void checkUserDoesNotAlreadyExist(String email) throws UsernameAlreadyExistsException {
        for (Provider provider : ProviderRepository.find()) {
            if (Objects.equals(email, provider.getEmail()))
                throw new UsernameAlreadyExistsException(email);
        }
        for (Client client : ClientService.getClientRepository().find()) {
            if (Objects.equals(email, client.getEmail()))
                throw new UsernameAlreadyExistsException(email);
        }
    }

    public static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }
}
