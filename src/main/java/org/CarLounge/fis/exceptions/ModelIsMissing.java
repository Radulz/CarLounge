package org.CarLounge.fis.exceptions;

public class ModelIsMissing extends Exception{
    public ModelIsMissing() {
        super(String.format("Model field can't be empty!"));
    }
}
