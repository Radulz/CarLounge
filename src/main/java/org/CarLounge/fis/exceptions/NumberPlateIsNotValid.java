package org.CarLounge.fis.exceptions;

public class NumberPlateIsNotValid extends Exception{
    public NumberPlateIsNotValid() {
        super(String.format("Number plate is not valid!"));
    }
}
