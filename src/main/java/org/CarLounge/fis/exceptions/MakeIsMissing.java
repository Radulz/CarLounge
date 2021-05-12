package org.CarLounge.fis.exceptions;

public class MakeIsMissing extends Exception{
    public MakeIsMissing() {
        super(String.format("Make field can't be empty!"));
    }
}
