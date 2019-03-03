package com.unitedcreation.myclinic.model;

public class Phone {
    private String number;
    private String type;
    private String uid;
    public Phone(String number,String type,String uid){
        this.number=number;
        this.type=type;
        this.uid=uid;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getUid() {
        return uid;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
