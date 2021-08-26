package org.CarLounge.fis.exceptions;

public class CnpAlreadyExists extends Exception {

    private String cnp;

    public CnpAlreadyExists(String cnp) {
        super(String.format("An account with the same personal identification number %s already exists!", cnp));
        this.cnp = cnp;
    }

    public String getUsername() {
        return cnp;
    }
}
