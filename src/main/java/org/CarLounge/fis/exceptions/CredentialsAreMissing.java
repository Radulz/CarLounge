package org.CarLounge.fis.exceptions;

public class CredentialsAreMissing extends Exception{
    public CredentialsAreMissing() {
        super(String.format("Please enter your credentials."));
    }
}
