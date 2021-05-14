package org.CarLounge.fis.exceptions;

public class YearIsNotValid extends Exception{
    public YearIsNotValid() {
        super(String.format("Year must be an integer between 1900 and 2021."));
    }
}
