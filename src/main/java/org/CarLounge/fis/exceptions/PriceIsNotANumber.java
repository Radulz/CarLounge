package org.CarLounge.fis.exceptions;

public class PriceIsNotANumber extends Exception{
    public PriceIsNotANumber() {
        super(String.format("Price must be an integer number!"));
    }
}
