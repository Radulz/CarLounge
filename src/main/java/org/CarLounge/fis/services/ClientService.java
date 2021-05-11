package org.CarLounge.fis.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.chrono.Chronology;
import java.util.Date;
import java.util.Objects;

import org.CarLounge.fis.exceptions.UsernameAlreadyExistsException;
import org.CarLounge.fis.model.Client;
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

    public static void addClient(String email, String password, String fname, String lname, String bdate) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(email);
        Client c= new Client(email, encodePassword(email, password), fname, lname, bdate);
        ClientRepository.insert(c);
    }


    public static void checkUserDoesNotAlreadyExist(String email) throws UsernameAlreadyExistsException {
        for (Client client : ClientRepository.find()) {
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
