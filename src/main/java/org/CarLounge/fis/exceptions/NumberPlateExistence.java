package org.CarLounge.fis.exceptions;

public class NumberPlateExistence extends Exception{
    public NumberPlateExistence() {
        super(String.format("Number plate entered does not appear in the list!"));
    }
}
