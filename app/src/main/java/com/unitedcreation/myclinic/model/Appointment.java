package com.unitedcreation.myclinic.model;

public class Appointment {
    private String pname,dname;
    private String time;
    private String date;
    private String status;
    private String doctorId,patientId;

    public Appointment(){}

    public Appointment(String pname,String dname,String time,String date,String status,String doctorId,String patientId){

        this.pname=pname;
        this.dname=dname;
        this.time=time;
        this.status=status;
        this.date=date;
        this.doctorId=doctorId;
        this.patientId=patientId;

    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDname() {
        return dname;
    }

    public String getPname() {
        return pname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
