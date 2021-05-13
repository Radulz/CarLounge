package org.CarLounge.fis.exceptions;

public class CnpIsMissing extends Exception{
    public CnpIsMissing() {
        super(String.format("Personal identification number field can't be empty!"));
    }
}
