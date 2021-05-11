package org.CarLounge.fis.exceptions;

public class PasswordsDoesNotMatch extends Exception{

    public PasswordsDoesNotMatch() {
        super(String.format("Passwords must match!"));
    }
}
