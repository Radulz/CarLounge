package org.CarLounge.fis.model;

import java.util.Date;
import java.util.Objects;

public class Provider {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Date bdate;
    private String companyname;
    private String adress;
    private String phone;
    private String taxregno;

    public Provider(String email, String password, String firstname, String lastname, Date bdate, String companyname, String adress, String phone, String taxregno) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bdate = bdate;
        this.companyname = companyname;
        this.adress = adress;
        this.phone = phone;
        this.taxregno = taxregno;
    }

    public Provider() { }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBdate() {
        return bdate;
    }

    public String getCompanyname() {
        return companyname;
    }

    public String getAdress() {
        return adress;
    }

    public String getPhone() {
        return phone;
    }

    public String getTaxregno() {
        return taxregno;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTaxregno(String taxregno) {
        this.taxregno = taxregno;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", bdate=" + bdate +
                ", companyname='" + companyname + '\'' +
                ", adress='" + adress + '\'' +
                ", phone='" + phone + '\'' +
                ", taxregno='" + taxregno + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return email.equals(provider.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, firstname, lastname, bdate, companyname, adress, phone, taxregno);
    }
}
