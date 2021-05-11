package org.CarLounge.fis.exceptions;

public class TaxRegNoIsMissing extends Exception{
    public TaxRegNoIsMissing() {
        super(String.format("Tax Registration Number field can't be empty!"));
    }
}
