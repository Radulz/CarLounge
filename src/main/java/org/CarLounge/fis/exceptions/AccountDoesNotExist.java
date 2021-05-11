package org.CarLounge.fis.exceptions;

public class AccountDoesNotExist extends Exception{
    public AccountDoesNotExist() {
        super(String.format("This account does not exist. Sign Up now!"));
    }
}
