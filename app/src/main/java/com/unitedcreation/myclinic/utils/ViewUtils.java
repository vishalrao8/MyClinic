package com.unitedcreation.myclinic.utils;

import android.content.Context;
import android.content.Intent;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.ui.doctor.DoctorActivity;
import com.unitedcreation.myclinic.ui.newuser.WelcomeActivity;
import com.unitedcreation.myclinic.ui.patient.PatientActivity;
import com.unitedcreation.myclinic.ui.stemcell.StemActivity;

import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.PATIENT;
import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;

public class ViewUtils {

    public static void switchTheme(Context context, int position) {

        switch (position) {

            case 1: context.setTheme(R.style.Red); break;

            case 2: context.setTheme(R.style.Blue); break;

            case 3: context.setTheme(R.style.Green); break;

            case 4: context.setTheme(R.style.Brown); break;

            default: context.setTheme(R.style.Green);

        }
    }

    static void moveToHome(Context context) {

        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    public static void moveToCorrespondingUi (Context context, int position) {

        Class activityClass = null;

        switch (position) {
            case 0: activityClass = StemActivity.class;
            break;

            case 1: activityClass = DoctorActivity.class;
            break;

            case 2: activityClass = PatientActivity.class;
        }

        Intent intent = new Intent(context, activityClass);

        intent.putExtra(PROFILE_EXTRA, position);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(intent);
    }

    public static void moveToCorrespondingUi (Context context, String type) {

        Class activityClass = null;
        int position=0;

        switch (type) {
            case StringUtils.STEM: activityClass = StemActivity.class;

            case DOCTOR: activityClass = DoctorActivity.class;

            case PATIENT: activityClass = PatientActivity.class;
        }

        Intent intent = new Intent(context, activityClass);

        intent.putExtra(PROFILE_EXTRA, position);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(intent);
    }
}