package org.CarLounge.fis.exceptions;

public class NumberPlateIsMissing extends Exception {
    public NumberPlateIsMissing() {
        super(String.format("Number plate field can't be empty"));
    }
}
