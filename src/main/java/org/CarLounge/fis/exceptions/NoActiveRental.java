package org.CarLounge.fis.exceptions;

public class NoActiveRental extends Exception{
    public NoActiveRental() {
        super(String.format("You can`t submit feedback without an active rental!"));
    }
}
