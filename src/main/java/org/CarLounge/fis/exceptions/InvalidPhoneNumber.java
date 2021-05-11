package org.CarLounge.fis.exceptions;

public class InvalidPhoneNumber extends Exception{
    public InvalidPhoneNumber() {
        super(String.format("Invalid phone number!"));
    }
}
