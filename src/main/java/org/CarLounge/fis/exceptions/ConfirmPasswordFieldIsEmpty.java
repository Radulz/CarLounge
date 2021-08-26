package org.CarLounge.fis.exceptions;

public class ConfirmPasswordFieldIsEmpty extends Exception{

    public ConfirmPasswordFieldIsEmpty() {
        super(String.format("Confirm password field can't be empty!"));
    }
}
