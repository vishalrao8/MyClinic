package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mukesh.OtpView;
import com.unitedcreation.myclinic.R;

import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class VerifyActivity extends AppCompatActivity {

    String user_profile="",user_contact="";
    EditText user_number_et;
    OtpView user_otp;
    TextView confirm_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        setContentView(R.layout.activity_verify);

        ButterKnife.bind(this);
/*
        user_profile=getIntent().getStringExtra("user_profile");
        confirm_input =findViewById(R.id.tv_verify_confirm);
        user_number_et=findViewById(R.id.et_verify_phone);
        user_otp=findViewById(R.id.otp_view);
        user_number_et.setSelection(3);

        //Be sure for View visibility
        user_number_et.setVisibility(View.VISIBLE);
        user_otp.setVisibility(View.GONE);

        confirm_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!user_number_et.getText().toString().equals("") && user_otp.getVisibility() != View.VISIBLE) {

                    user_contact=user_number_et.getText().toString();
                    user_number_et.setVisibility(View.GONE);
                    user_otp.setVisibility(View.VISIBLE);
                    user_otp.requestFocus();
                    confirm_input.setText("Submit");

                }
                else if(user_otp.getVisibility()==View.VISIBLE) {

                    if(user_otp.getText().toString().equals("1234")) {

                        startActivity(new Intent(VerifyActivity.this,RegistrationActivity.class));
                        finish();

                    }
                    else
                        Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }
}
