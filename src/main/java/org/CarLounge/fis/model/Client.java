package org.CarLounge.fis.model;

import java.time.chrono.Chronology;
import java.util.Date;
import java.util.Objects;

public class Client {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String birthdate;
    private String CNP;

    public Client(String email, String password, String firstname, String lastname, String birthdate, String CNP){
        this.email=email;
        this.password=password;
        this.firstname=firstname;
        this.lastname=lastname;
        this.birthdate=birthdate;
        this.CNP=CNP;
    }
    public Client() { }

    public String toString() {
        return "Client{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", bDate=" + birthdate +
                '}';
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return email.equals(client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, firstname, lastname, birthdate);
    }


}

