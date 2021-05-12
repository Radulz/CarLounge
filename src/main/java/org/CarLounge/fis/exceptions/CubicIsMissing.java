package org.CarLounge.fis.exceptions;

public class CubicIsMissing extends Exception{
    public CubicIsMissing() {
        super(String.format("Cubic Capacity field can't be empty"));
    }
}
