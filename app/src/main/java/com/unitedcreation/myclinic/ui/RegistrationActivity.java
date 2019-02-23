package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.utils.StringUtils;

import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.NAME;
import static com.unitedcreation.myclinic.utils.StringUtils.PATIENT;
import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.STATE;
import static com.unitedcreation.myclinic.utils.StringUtils.STEM;
import static com.unitedcreation.myclinic.utils.StringUtils.STREET;
import static com.unitedcreation.myclinic.utils.StringUtils.SUPPLIER;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;
import static com.unitedcreation.myclinic.utils.StringUtils.USER_ID;
import static com.unitedcreation.myclinic.utils.StringUtils.VAR;
import static com.unitedcreation.myclinic.utils.StringUtils.VENDOR;
import static com.unitedcreation.myclinic.utils.StringUtils.ZIP;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class RegistrationActivity extends AppCompatActivity {
    //Database Child Variable
    static String child="";

    //Edit TExts

    @BindView(R.id.et_registration_issue)
    EditText mVariable_et;

    @BindView(R.id.et_registration_name)
    EditText mName_et;

    @BindView(R.id.et_registration_city)
    EditText mCity_et;

    @BindView(R.id.et_registration_state)
    EditText mState_et;

    @BindView(R.id.et_registration_street)
    EditText mStreet_et;

    @BindView(R.id.et_registration_zip)
    EditText mZip_et;

    //Text View
    @BindView(R.id.tv_registration_fill)
    TextView mFill_tv;

    //Card View
    @BindView(R.id.cv_registration_issue)
    CardView mVariable_cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_patient);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        ButterKnife.bind(this);
        switch (getIntent().getIntExtra(PROFILE_EXTRA, 0)){
            case 0:
                child= STEM ;
                mVariable_cv.setVisibility(View.GONE);
                break;
            case 1:
                child= PATIENT ;
                mVariable_et.setHint("Issue");
                break;
            case 2:
                child= DOCTOR ;
                mVariable_et.setHint("Qualifications");
                break;
            case 3:
                child= VENDOR ;
                mVariable_cv.setVisibility(View.GONE);
                break;
            case 4:
                child= SUPPLIER ;
                mVariable_cv.setVisibility(View.GONE);

        }

        // DataBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(USERS).child(child).child(getSharedPreferences(USER_ID,MODE_PRIVATE).getString(USER_ID,"O"));

        mFill_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCity_et.getText().toString().equals("") || mFill_tv.getText().toString().equals("") ||
                mName_et.getText().toString().equals("") || mState_et.getText().toString().equals("") ||
                mStreet_et.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter Al Details",Toast.LENGTH_LONG).show();
                }
                else
                {
                 myRef.child(NAME).setValue(mName_et.getText().toString());
                 myRef.child(STATE).setValue(mState_et.getText().toString());
                 myRef.child(STREET).setValue(mStreet_et.getText().toString());
                 myRef.child(ZIP).child(mZip_et.getText().toString());
                 myRef.child(VAR).setValue(mVariable_et.getText().toString());
                }
            }
        });

    }
}
