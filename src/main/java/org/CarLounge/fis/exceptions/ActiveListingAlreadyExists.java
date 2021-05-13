package org.CarLounge.fis.exceptions;

public class ActiveListingAlreadyExists extends Exception{
    private String noPlate;

    public ActiveListingAlreadyExists(String noPlate) {
        super(String.format("An active listing with %s number plate already exists!", noPlate));
        this.noPlate = noPlate;
    }

    public String getNoPlate() {
        return noPlate;
    }
}
