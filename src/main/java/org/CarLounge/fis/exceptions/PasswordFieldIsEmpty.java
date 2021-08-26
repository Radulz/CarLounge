package org.CarLounge.fis.exceptions;

public class PasswordFieldIsEmpty extends Exception{

    public PasswordFieldIsEmpty() {
        super(String.format("Password field can't be empty!"));
    }
}
