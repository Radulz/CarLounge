package org.CarLounge.fis.model;

public class Listing {
    private String clientEmail;
    private String providerEmail;

    private String make;
    private String model;
    private int year;
    private int mileage;
    private int cmc;
    private String fuel;
    private String notes;

    private boolean active=false;
    private boolean completed=false;

    public Listing(String clientEmail, String providerEmail, String make, String model, int year, int mileage, int cmc, String fuel, String notes) {
        this.clientEmail=clientEmail;
        this.providerEmail=providerEmail;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.cmc=cmc;
        this.fuel=fuel;
        this.notes=notes;
    }

    public Listing(){ }

    public String getProviderEmail(){
        return providerEmail;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public int getCmc() {
        return cmc;
    }

    public String getFuel() {
        return fuel;
    }

    public String getNotes() {
        return notes;
    }

    public boolean getActive() {
        return active;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setCmc(int cmc) {
        this.cmc = cmc;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
