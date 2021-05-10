package org.CarLounge.fis.model;

import java.util.Date;
import java.util.Objects;

public class Client {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Date birthdate;

    public Client(String email, String password, String firstname, String lastname, Date birthdate){
        this.email=email;
        this.password=password;
        this.firstname=firstname;
        this.lastname=lastname;
        this.birthdate=birthdate;
    }
    public Client() { }

    public String toString() {
        return "Client{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", bdate=" + birthdate +
                '}';
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

    public Date getBdate() {
        return birthdate;
    }

    public void setBdate(Date bdate) {
        this.birthdate = bdate;
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

