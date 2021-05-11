package org.CarLounge.fis.exceptions;

public class TextIsNotAValidEmail extends Exception{

    public TextIsNotAValidEmail() {
        super(String.format("The entered text is not an email!"));
    }

}
