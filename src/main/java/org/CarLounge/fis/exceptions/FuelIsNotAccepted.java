package org.CarLounge.fis.exceptions;

public class FuelIsNotAccepted extends Exception{
    public FuelIsNotAccepted() {
        super(String.format("This fuel is not accepted!"));
    }
}
