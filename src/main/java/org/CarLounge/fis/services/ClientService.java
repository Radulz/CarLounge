package org.CarLounge.fis.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import org.CarLounge.fis.exceptions.*;
import org.CarLounge.fis.model.Client;
import org.CarLounge.fis.model.Provider;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

public class ClientService {

    private static ObjectRepository<Client> ClientRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Client.db").toFile())
                .openOrCreate("client", "client");

        ClientRepository = database.getRepository(Client.class);
    }

    private static void checkFields(String email, String password, String fName, String lName, String bDate, String confirmPassword, String cnp) throws PasswordsDoesNotMatch, ConfirmPasswordFieldIsEmpty, PasswordDoesNotContainTheRequiredCharacters, PasswordFieldIsEmpty, MinimumAgeIsRequired, BirthDateIsNotADate, BirthDateFieldIsEmpty, LastNameFieldIsEmpty, FirstNameFieldIsEmpty, TextIsNotAValidEmail, EmailFieldIsEmpty, CnpIsMissing, CnpIsNotValid {
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
        else if(cnp == ""){
            throw new CnpIsMissing();
        }
        else if(!isCNPValid(cnp)){
            throw  new CnpIsNotValid();
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

    private static boolean isCNPValid(String cnp){
        char ch;

        if(cnp.length()<13) {
            return false;
        }

        for(int i=0; i<cnp.length();i++){
            ch=cnp.charAt(i);
            if(!Character.isDigit(ch)){
                return false;
            }
        }

        return true;
    }

    private static boolean checkDate(String s) {

        String[] aux = s.split("/");

        int m = Integer.parseInt(aux[0]);
        int d = Integer.parseInt(aux[1]);
        int y = Integer.parseInt(aux[2]);

        LocalDateTime fromDateTime = LocalDateTime.of(y, m, d, 0, 0, 0);
        LocalDateTime toDateTime = LocalDateTime.now();

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        return years > 17;
    }

    private static boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static boolean checkEmail(String email){
        String [] acceptedMails = {"@yahoo.com", "@yahoo.co.uk", "@yahoo.ro", "@gmail.com", "@student.upt.ro", "@cs.upt.ro"};
        for(String sir : acceptedMails){
            if(email.endsWith(sir))
                return true;
        }
        return false;
    }

    private static boolean checkPassword(String password){

        char ch;
        boolean capitalLetter = false;
        boolean lowerCaseLetter = false;
        boolean number = false;

        if(password.length() < 8) {
            return false;
        }
        for(int i=0;i < password.length();i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                number = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalLetter = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseLetter = true;
            }
            if(number && capitalLetter && lowerCaseLetter){
                return true;
            }

        }
        return false;
    }

    public static void addClient(String email, String password, String fName, String lName, String bDate, String confirmPassword, String cnp) throws UsernameAlreadyExistsException, PasswordDoesNotContainTheRequiredCharacters, EmailFieldIsEmpty, LastNameFieldIsEmpty, PasswordFieldIsEmpty, BirthDateFieldIsEmpty, FirstNameFieldIsEmpty, MinimumAgeIsRequired, PasswordsDoesNotMatch, TextIsNotAValidEmail, ConfirmPasswordFieldIsEmpty, BirthDateIsNotADate, CnpIsMissing, CnpIsNotValid {
        checkFields(email, password, fName, lName, bDate, confirmPassword, cnp);
        checkUserDoesNotAlreadyExist(email);
        Client c= new Client(email, encodePassword(email, password), fName, lName, bDate, cnp);
        ClientRepository.insert(c);
    }


    public static void checkUserDoesNotAlreadyExist(String email) throws UsernameAlreadyExistsException {
        for (Client client : ClientRepository.find()) {
            if (Objects.equals(email, client.getEmail()))
                throw new UsernameAlreadyExistsException(email);
        }
        for (Provider provider : LegalPersonProviderService.getProviderRepository().find()) {
            if (Objects.equals(email, provider.getEmail()))
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

    public static ObjectRepository<Client> getClientRepository(){
        return ClientRepository;
    }
}
