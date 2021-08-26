package org.CarLounge.fis.exceptions;

public class CompanyNameIsMissing extends Exception{
    public CompanyNameIsMissing() {
        super(String.format("Company name field can't be empty!"));
    }
}
