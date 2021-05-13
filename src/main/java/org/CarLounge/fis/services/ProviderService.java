package org.CarLounge.fis.services;

import org.CarLounge.fis.model.Provider;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProviderService {

    protected static ObjectRepository<Provider> ProviderRepository;

    public static void initDatabase(){
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Provider.db").toFile())
                .openOrCreate("provider", "provider");

        ProviderRepository = database.getRepository(Provider.class);
    }
    protected static boolean checkDate(String s) {

        String aux[] = s.split("/");

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

    protected static boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    protected static boolean checkEmail(String email){
        String [] acceptedMails = {"@yahoo.com", "@yahoo.co.uk", "@yahoo.ro", "@gmail.com", "@student.upt.ro", "@cs.upt.ro"};
        for(String sir : acceptedMails){
            if(email.endsWith(sir))
                return true;
        }
        return false;
    }

    protected static boolean checkPassword(String password){

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
    protected static boolean isValidPhoneNumber(String phone){
        char ch;

        if(phone.length()<10) {
            return false;
        }

        for(int i=0; i<phone.length();i++){
            ch=phone.charAt(i);
            if(!Character.isDigit(ch)){
                return false;
            }
        }

        return true;
    }
    protected static boolean isCNPValid(String cnp){
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

    public static ObjectRepository<Provider> getProviderRepository(){
        return ProviderRepository;
    }
}
