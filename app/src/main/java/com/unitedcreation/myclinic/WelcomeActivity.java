package com.unitedcreation.myclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Patient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
}
