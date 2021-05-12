package org.CarLounge.fis.exceptions;

public class MileageIsMissing extends Exception{
    public MileageIsMissing() {
        super(String.format("Mileage can't be empty!"));
    }
}
