package org.CarLounge.fis.exceptions;

public class BirthDateIsNotADate extends Exception{
    public BirthDateIsNotADate() {
        super(String.format("A valid date is required!"));
    }
}
