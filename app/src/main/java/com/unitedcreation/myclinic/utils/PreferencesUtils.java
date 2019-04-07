package com.unitedcreation.myclinic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.model.Patient;
import com.unitedcreation.myclinic.model.StemCellUser;

import static com.unitedcreation.myclinic.utils.StringUtils.AGE;
import static com.unitedcreation.myclinic.utils.StringUtils.ISSSUE;
import static com.unitedcreation.myclinic.utils.StringUtils.LICENCE;
import static com.unitedcreation.myclinic.utils.StringUtils.NAME;
import static com.unitedcreation.myclinic.utils.StringUtils.QUALIFICATION;
import static com.unitedcreation.myclinic.utils.StringUtils.STATE;
import static com.unitedcreation.myclinic.utils.StringUtils.STREET;
import static com.unitedcreation.myclinic.utils.StringUtils.USER_ID;
import static com.unitedcreation.myclinic.utils.StringUtils.ZIP;

public class PreferencesUtils {

    private static SharedPreferences getPreferences (Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public static void addUser (Context context, String uid) {
        getPreferences(context).edit().putString(USER_ID, uid).apply();
    }

    static void removeUser (Context context) {
        getPreferences(context).edit().putString(USER_ID, null).apply();
    }

    public static String getUserId (Context context) {
        return getPreferences(context).getString(USER_ID, "0");
    }

    public static void addPatientData (Context context, Patient patient) {
        getPreferences(context).edit().putString(NAME,patient.getName()).apply();
        getPreferences(context).edit().putString(STATE,patient.getState()).apply();
        getPreferences(context).edit().putString(STREET,patient.getStreetAddress()).apply();
        getPreferences(context).edit().putString(ZIP,patient.getZipCode()).apply();
        getPreferences(context).edit().putString(ISSSUE,patient.getIssue()).apply();
        getPreferences(context).edit().putInt(AGE,patient.getmAge()).apply();
    }

    public static Patient getPatientData(Context context){
        Patient patient=new Patient();
        patient.setName(getPreferences(context).getString(NAME,""));
        patient.setState(getPreferences(context).getString(STATE,""));
        patient.setStreetAddress(getPreferences(context).getString(STREET,""));
        patient.setZipCode(getPreferences(context).getString(ZIP,""));
        patient.setIssue(getPreferences(context).getString(ISSSUE,""));
        patient.setmAge(getPreferences(context).getInt(AGE,0));
        return patient;
    }
    public static void addDoctorData (Context context,Doctor doctor) {
        getPreferences(context).edit().putString(NAME,doctor.getName()).apply();
        getPreferences(context).edit().putString(STATE,doctor.getState()).apply();
        getPreferences(context).edit().putString(STREET,doctor.getStreetAddress()).apply();
        getPreferences(context).edit().putString(ZIP,doctor.getZipCode()).apply();
        getPreferences(context).edit().putString(QUALIFICATION,doctor.getQualification()).apply();
        getPreferences(context).edit().putInt(AGE,doctor.getmAge()).apply();
        getPreferences(context).edit().putString(LICENCE,doctor.getLicence()).apply();
    }
    public static Doctor getDoctorData  (Context context){
        Doctor doctor=new Doctor();
        doctor.setName(getPreferences(context).getString(NAME,""));
        doctor.setState(getPreferences(context).getString(STATE,""));
        doctor.setStreetAddress(getPreferences(context).getString(STREET,""));
        doctor.setZipCode(getPreferences(context).getString(ZIP,""));
        doctor.setQualification(getPreferences(context).getString(QUALIFICATION,""));
        doctor.setLicence(getPreferences(context).getString(LICENCE,""));
        doctor.setmAge(getPreferences(context).getInt(AGE,0));
        return doctor;
    }
    public static void addStemUserData (Context context, StemCellUser stemCellUser) {
        getPreferences(context).edit().putString(NAME,stemCellUser.getName()).apply();
        getPreferences(context).edit().putString(STATE,stemCellUser.getState()).apply();
        getPreferences(context).edit().putString(STREET,stemCellUser.getStreetAddress()).apply();
        getPreferences(context).edit().putString(ZIP,stemCellUser.getZipCode()).apply();
        getPreferences(context).edit().putInt(AGE,stemCellUser.getmAge()).apply();
    }
    public static StemCellUser getStemUserData (Context context){
        StemCellUser stemcelluser=new StemCellUser();
        stemcelluser.setName(getPreferences(context).getString(NAME,""));
        stemcelluser.setState(getPreferences(context).getString(STATE,""));
        stemcelluser.setStreetAddress(getPreferences(context).getString(STREET,""));
        stemcelluser.setZipCode(getPreferences(context).getString(ZIP,""));
        stemcelluser.setmAge(getPreferences(context).getInt(AGE,0));
        return stemcelluser;
    }

}
