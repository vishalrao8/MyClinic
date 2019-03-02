package com.unitedcreation.myclinic.model;

public class Bank {

    private String name;
    private int rating;
    private double latitude;
    private double longitude;

    public Bank (String name, int rating, double latitude, double longitude) {

        this.name = name;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;

    }
    public Bank(){}

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
