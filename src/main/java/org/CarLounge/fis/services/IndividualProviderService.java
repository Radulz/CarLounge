import org.CarLounge.fis.exceptions.UsernameAlreadyExistsException;
import org.CarLounge.fis.model.Provider;
import org.CarLounge.fis.services.ProviderService;
import org.dizitart.no2.Nitrite;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

public class IndividualProviderService extends ProviderService {

    public static void addProvider(String email, String password, String fname, String lname, Date bdate) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(email);
        Provider p= new Provider(email, encodePassword(email, password), fname, lname, bdate, "IndividualPerson", "IndividualPerson", "IndividualPerson", "IndividualPerson");
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
