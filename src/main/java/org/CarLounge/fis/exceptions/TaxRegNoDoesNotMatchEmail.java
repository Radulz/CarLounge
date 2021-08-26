package org.CarLounge.fis.exceptions;

public class TaxRegNoDoesNotMatchEmail extends Exception{
    public TaxRegNoDoesNotMatchEmail() {
        super(String.format("The tax registration number you entered does not match with the one associated with the email!"));
    }
}
