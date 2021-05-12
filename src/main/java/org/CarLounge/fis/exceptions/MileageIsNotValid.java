package org.CarLounge.fis.exceptions;

public class MileageIsNotValid extends Exception{
    public MileageIsNotValid() {
        super(String.format("Mileage must be between 0 km and 1 000 000 km"));
    }
}
