package org.CarLounge.fis.exceptions;

public class CnpIsNotValid extends Exception{
    public CnpIsNotValid() {
        super(String.format("Personal identification number is not valid!"));
    }
}
