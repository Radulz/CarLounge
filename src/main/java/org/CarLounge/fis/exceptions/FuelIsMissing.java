package org.CarLounge.fis.exceptions;

public class FuelIsMissing extends Exception{
    public FuelIsMissing() {
        super(String.format("Fuel field can't be empty!"));
    }
}
