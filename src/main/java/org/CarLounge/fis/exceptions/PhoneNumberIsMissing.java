package org.CarLounge.fis.exceptions;

public class PhoneNumberIsMissing extends Exception{

    public PhoneNumberIsMissing() {
        super(String.format("Phone number field can't be empty!"));
    }
}
