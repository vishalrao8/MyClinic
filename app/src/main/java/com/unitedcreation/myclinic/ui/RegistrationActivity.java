package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.unitedcreation.myclinic.R;

import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        setContentView(R.layout.activity_registration);



    }
}
