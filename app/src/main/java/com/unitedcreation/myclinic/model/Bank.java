package com.unitedcreation.myclinic.model;

public class Bank {

    private String name;
    private int rating;
    private double latitude;
    private double longtitude;

    public Bank (String name, int rating, double latitude, double longtitude) {

        this.name = name;
        this.rating = rating;
        this.latitude = latitude;
        this.longtitude = longtitude;

    }
    public Bank(){}

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
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

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
