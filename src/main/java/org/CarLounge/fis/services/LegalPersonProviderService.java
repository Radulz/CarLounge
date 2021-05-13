package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Provider;
import org.dizitart.no2.Nitrite;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class LegalPersonProviderService extends ProviderService{

    public static String getTodayDate() {
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    private static void checkFields(String email, String password, String companyName, String address, String phone, String taxRegNo, String confirmPassword) throws EmailFieldIsEmpty, PasswordFieldIsEmpty, ConfirmPasswordFieldIsEmpty, PasswordsDoesNotMatch, PasswordDoesNotContainTheRequiredCharacters, TextIsNotAValidEmail, CompanyNameIsMissing, AddressIsMissing, PhoneNumberIsMissing, InvalidPhoneNumber, TaxRegNoIsMissing{
        if(email == ""){
            throw new EmailFieldIsEmpty();
        }
        else if(!checkEmail(email)){
            throw new TextIsNotAValidEmail();
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
        else if(companyName == ""){
            throw new CompanyNameIsMissing();
        }
        else if(address == ""){
            throw new AddressIsMissing();
        }
        else if(phone == ""){
            throw new PhoneNumberIsMissing();
        }
        else if(!isValidPhoneNumber(phone)){
            throw new InvalidPhoneNumber();
        }
        else if(taxRegNo == ""){
            throw new TaxRegNoIsMissing();
        }
    }
    public static void addProvider(String email, String password, String companyName, String address, String phone, String taxRegNo, String confirmPassword) throws UsernameAlreadyExistsException, EmailFieldIsEmpty, PasswordFieldIsEmpty, ConfirmPasswordFieldIsEmpty, PasswordsDoesNotMatch, PasswordDoesNotContainTheRequiredCharacters, TextIsNotAValidEmail, CompanyNameIsMissing, AddressIsMissing, PhoneNumberIsMissing, InvalidPhoneNumber, TaxRegNoIsMissing {
        checkFields(email, password, companyName, address, phone, taxRegNo, confirmPassword);
        checkUserDoesNotAlreadyExist(email);
        Provider p= new Provider(email, encodePassword(email, password), "LegalPerson", "LegalPerson", getTodayDate(), companyName, address, phone, taxRegNo, "LegalPerson");
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
