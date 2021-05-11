package org.CarLounge.fis.exceptions;

public class EmailFieldIsEmpty extends Exception{

    public EmailFieldIsEmpty() {
        super(String.format("Email field can't be empty!"));
    }
}
