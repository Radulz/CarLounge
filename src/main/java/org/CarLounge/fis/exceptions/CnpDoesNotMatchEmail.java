package org.CarLounge.fis.exceptions;

public class CnpDoesNotMatchEmail extends Exception{
    public CnpDoesNotMatchEmail() {
        super(String.format("The Identification Number you entered does not match with the one associated with the email"));
    }
}
