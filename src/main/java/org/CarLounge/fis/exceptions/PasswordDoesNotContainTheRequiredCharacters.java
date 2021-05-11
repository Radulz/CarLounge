package org.CarLounge.fis.exceptions;

public class PasswordDoesNotContainTheRequiredCharacters extends Exception{

    public PasswordDoesNotContainTheRequiredCharacters() {
        super(String.format("Password does not contain the specified characters!"));
    }
}
