package org.CarLounge.fis.exceptions;

public class NotesAreMissing extends Exception{
    public NotesAreMissing() {
        super(String.format("Notes can't be empty and must include the price/day!"));
    }
}
