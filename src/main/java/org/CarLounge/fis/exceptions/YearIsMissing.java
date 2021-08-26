package org.CarLounge.fis.exceptions;

public class YearIsMissing extends Exception{
    public YearIsMissing() {
        super(String.format("Year field can't be empty!"));
    }
}
