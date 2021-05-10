package org.CarLounge.fis.services;

import org.CarLounge.fis.model.Provider;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

public class ProviderService {

    protected static ObjectRepository<Provider> ProviderRepository;

    public static void initDatabase(){
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Provider.db").toFile())
                .openOrCreate("provider", "provider");

        ProviderRepository = database.getRepository(Provider.class);
    }

}
