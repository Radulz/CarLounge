package org.CarLounge.fis.exceptions;

public class TaxRegNoAlreadyExists extends Exception {

    private String taxRegNo;

    public TaxRegNoAlreadyExists(String taxRegNo) {
        super(String.format("An account with the same tax registration number %s already exists!", taxRegNo));
        this.taxRegNo = taxRegNo;
    }

    public String getUsername() {
        return taxRegNo;
    }
}
