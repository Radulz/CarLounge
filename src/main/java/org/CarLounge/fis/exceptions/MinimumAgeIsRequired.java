package org.CarLounge.fis.exceptions;

public class MinimumAgeIsRequired extends Exception{

    public MinimumAgeIsRequired() {
        super(String.format("You must be at least 18 years old!"));
    }
}
