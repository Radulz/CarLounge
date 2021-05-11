package org.CarLounge.fis.exceptions;

public class LastNameFieldIsEmpty extends Exception{

    public LastNameFieldIsEmpty() {
        super(String.format("Last name is missing!"));
    }
}
