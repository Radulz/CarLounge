package org.CarLounge.fis.exceptions;

public class BirthDateFieldIsEmpty extends Exception{

    public BirthDateFieldIsEmpty() {
        super(String.format("Birth date field is empty!"));
    }
}
