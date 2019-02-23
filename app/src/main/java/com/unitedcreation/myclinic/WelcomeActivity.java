package com.unitedcreation.myclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
TextView stem_cell_tv,medical_kit_tv,mk_doctor,mk_patient,mk_supplier,mk_vendor;
String user_profile="";
ConstraintLayout mk_view,welcome_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Patient);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Welcome View
        stem_cell_tv=findViewById(R.id.tv_welcome_stem);
        medical_kit_tv=findViewById(R.id.tv_welcome_medical);
        mk_view=findViewById(R.id.mk_view);
        welcome_view=findViewById(R.id.welcome_view);
        //mk_view Group Buttons
        mk_doctor=findViewById(R.id.tv_mk_doctor);
        mk_patient=findViewById(R.id.tv_mk_patient);
        mk_supplier=findViewById(R.id.tv_mk_supplier);
        mk_vendor=findViewById(R.id.tv_mk_vendor);

        //Be sure for View Visiblity
        mk_view.setVisibility(View.GONE);
        welcome_view.setVisibility(View.VISIBLE);

        //CLick lIstner for mk_views

        mk_doctor.setOnClickListener(this);
        mk_vendor.setOnClickListener(this);
        mk_supplier.setOnClickListener(this);
        mk_patient.setOnClickListener(this);

        //Welcome View Click Listners

        stem_cell_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeActivity.this,VerifyActivity.class);
                intent.putExtra("user_profile","Stem Cell");
                startActivity(intent);
                finish();
            }
        });
        medical_kit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mk_view.getVisibility()!=View.VISIBLE && welcome_view.getVisibility()==View.VISIBLE){
                    mk_view.setVisibility(View.VISIBLE);
                welcome_view.setVisibility(View.GONE);}
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(mk_view.getVisibility()==View.VISIBLE){
                switch (v.getId()){
                    case R.id.tv_mk_doctor:
                        user_profile="Doctor";
                        break;
                    case R.id.tv_mk_vendor:
                        user_profile="Vendor";
                        break;
                    case R.id.tv_mk_supplier:
                        user_profile="Supplier";
                        break;
                    case R.id.iv_mk_patient:
                        user_profile="Patienr";
                }
                Intent intent=new Intent(WelcomeActivity.this,VerifyActivity.class);
                intent.putExtra("user_profile",user_profile);
                startActivity(intent);
                finish();
        }
    }
}
