package com.unitedcreation.myclinic.model;

public class Appointment {
    private String name;
    private String time;

    public Appointment(){}

    public Appointment(String name,String time){

        this.name=name;
        this.time=time;

    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
