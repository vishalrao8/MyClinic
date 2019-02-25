package com.unitedcreation.myclinic.utils;

import android.content.Context;
import android.content.Intent;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.ui.WelcomeActivity;

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

    public static void moveToHome (Context context) {

        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);

    }

}
