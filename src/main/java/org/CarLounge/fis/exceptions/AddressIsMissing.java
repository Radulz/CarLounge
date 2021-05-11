package org.CarLounge.fis.exceptions;

public class AddressIsMissing extends Exception{
    public AddressIsMissing() {
        super(String.format("Address field can't be empty!"));
    }
}
