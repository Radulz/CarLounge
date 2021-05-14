package org.CarLounge.fis.exceptions;

public class CmcNotValid extends Exception{
    public CmcNotValid() {
        super(String.format("Cubic capacity must be an integer between 100 and 20000"));
    }
}
