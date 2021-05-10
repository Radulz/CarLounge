package org.CarLounge.fis.services;

import org.CarLounge.fis.exceptions.UsernameAlreadyExistsException;
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

    public static Date getTodayDate(String date) {
        try{
            return new SimpleDateFormat("MM-dd-yyyy").parse(date);
        } catch(ParseException e){
            return null;
        }
    }
    public static void addProvider(String email, String password, String companyname, String adress, String phone, String taxregno) throws UsernameAlreadyExistsException, Exception {
        checkUserDoesNotAlreadyExist(email);
        Provider p= new Provider(email, encodePassword(email, password), "LegalPerson", "LegalPerson", getTodayDate("05-11-2021") , companyname, adress, phone, taxregno);
        ProviderRepository.insert(p);
    }

    public static void checkUserDoesNotAlreadyExist(String email) throws UsernameAlreadyExistsException {
        for (Provider provider : ProviderRepository.find()) {
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
}
