package org.CarLounge.fis.exceptions;

public class ActiveRentalAlreadyExists extends Exception{
    public ActiveRentalAlreadyExists() {
        super(String.format("You can't have more than one rental at a time!"));
    }
}
