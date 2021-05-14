package org.CarLounge.fis.exceptions;

public class RentalInProgress extends Exception{
    public RentalInProgress() {
        super(String.format("Rental in progress!"));
    }
}
