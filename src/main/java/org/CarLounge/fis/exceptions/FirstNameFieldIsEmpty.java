package org.CarLounge.fis.exceptions;

public class FirstNameFieldIsEmpty extends Exception{

    public FirstNameFieldIsEmpty() {
        super(String.format("First name is missing!"));
    }
}
