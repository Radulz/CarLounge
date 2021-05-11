package org.CarLounge.fis.exceptions;

public class CredentialsEnteredAreIncorrect extends Exception{
    public CredentialsEnteredAreIncorrect() {
        super(String.format("Credentials entered are incorrect!"));
    }
}
