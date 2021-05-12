package org.CarLounge.fis.exceptions;

public class PriceIsMissing extends Exception{
    public PriceIsMissing() {
        super(String.format("Price per day must be included!"));
    }
}
