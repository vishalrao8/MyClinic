package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.SQLiteDatabase.DataTableHelper;
import com.unitedcreation.myclinic.model.Supplier;

import static com.unitedcreation.myclinic.utils.StringUtils.AGE;
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
import static com.unitedcreation.myclinic.utils.StringUtils.VENDOR;
import static com.unitedcreation.myclinic.utils.StringUtils.ZIP;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class RegistrationActivity extends AppCompatActivity {
    //Database Child Variable
    static String child="";
    DataTableHelper dataTableHelper;

    //Edit TExts

    @BindView(R.id.et_registration_variable)
    EditText mVariable_et;

    @BindView(R.id.et_registration_licence)
    EditText mLicence_et;

    @BindView(R.id.et_registration_name)
    EditText mName_et;

    @BindView(R.id.et_registration_city)
    EditText mCity_et;

    @BindView(R.id.et_registration_state)
    EditText mState_et;

    @BindView(R.id.et_registration_age)
    EditText mAge;
    @BindView(R.id.et_registration_street)
    EditText mStreet_et;

    @BindView(R.id.et_registration_zip)
    EditText mZip_et;

    //Text View
    @BindView(R.id.tv_registration_fill)
    TextView mFill_tv;

    //Card View
    @BindView(R.id.cv_registration_variable)
    CardView mVariable_cv;

    @BindView(R.id.cv_registration_licence)
    CardView mLicence_cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        dataTableHelper=new DataTableHelper(this);
        Log.i("TAG",String.valueOf(getIntent().getIntExtra(PROFILE_EXTRA, 0)));
        switch (getIntent().getIntExtra(PROFILE_EXTRA, 0)){
            case 0:
                child= STEM ;
                mVariable_cv.setVisibility(View.GONE);
                break;
            case 1:
                child= DOCTOR ;
                Log.i("TAG","CALLED");
                mVariable_cv.setVisibility(View.VISIBLE);
                mVariable_et.setHint("Qualifications");
                mLicence_cv.setVisibility(View.VISIBLE);
                break;
            case 2:
                child= PATIENT ;
                Log.i("TAG","CALLED");
                mVariable_cv.setVisibility(View.VISIBLE);
                mVariable_et.setHint("Issue");
                mLicence_cv.setVisibility(View.GONE);
                break;
            case 3:
                child= SUPPLIER ;
                mVariable_cv.setVisibility(View.VISIBLE);
                mVariable_et.setHint("Licence");
                break;
            case 4:
                child= VENDOR ;
                mVariable_cv.setVisibility(View.VISIBLE);
                mVariable_et.setHint("Licence");


        }

        // DataBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(USERS).child(child).child(getSharedPreferences(USER_ID,MODE_PRIVATE).getString(USER_ID,"O"));

        mFill_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCity_et.getText().toString().equals("") || mFill_tv.getText().toString().equals("") ||
                mName_et.getText().toString().equals("") || mState_et.getText().toString().equals("") ||
                mStreet_et.getText().toString().equals("") || mAge.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter Al Details",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String licence=null,issue=null,qualification=null;
                    myRef.child(NAME).setValue(mName_et.getText().toString());
                    myRef.child(STATE).setValue(mState_et.getText().toString());
                    myRef.child(STREET).setValue(mStreet_et.getText().toString());
                    myRef.child(ZIP).child(mZip_et.getText().toString());
                    myRef.child(AGE).setValue(mAge.getText().toString());
                        switch (getIntent().getIntExtra(PROFILE_EXTRA, 0)){
                            case 0:
                                //Storing in local Database
                                dataTableHelper.insertItem(mName_et.getText().toString(),
                                        getIntent().getIntExtra(PROFILE_EXTRA, 0),
                                        Integer.parseInt(mAge.getText().toString()),
                                        mStreet_et.getText().toString(),
                                        mState_et.getText().toString()
                                        ,issue,qualification,licence);
                                moveToHome();
                                break;
                            case 1:
                                myRef.child(mVariable_et.getHint().toString()).setValue(mVariable_et.getText().toString());
                                myRef.child(mLicence_et.getHint().toString()).setValue(mLicence_et.getText().toString());
                                qualification=mVariable_et.getText().toString();
                                licence=mLicence_et.getText().toString();
                                //Storing in local Database
                                dataTableHelper.insertItem(mName_et.getText().toString(),
                                        getIntent().getIntExtra(PROFILE_EXTRA, 0),
                                        Integer.parseInt(mAge.getText().toString()),
                                        mStreet_et.getText().toString(),
                                        mState_et.getText().toString()
                                        ,issue,qualification,licence);
                                moveToDoctorHome();
                                break;
                            case 2:
                                myRef.child(mVariable_et.getHint().toString()).setValue(mVariable_et.getText().toString());
                                issue=mVariable_et.getText().toString();
                                //Storing in local Database
                                dataTableHelper.insertItem(mName_et.getText().toString(),
                                        getIntent().getIntExtra(PROFILE_EXTRA, 0),
                                        Integer.parseInt(mAge.getText().toString()),
                                        mStreet_et.getText().toString(),
                                        mState_et.getText().toString()
                                        ,issue,qualification,licence);
                                moveToPatientHome();
                                break;
                            case 3:
                                myRef.child(mVariable_et.getHint().toString()).setValue(mVariable_et.getText().toString());
                                licence=mVariable_et.getText().toString();
                                //Storing in local Database
                                dataTableHelper.insertItem(mName_et.getText().toString(),
                                        getIntent().getIntExtra(PROFILE_EXTRA, 0),
                                        Integer.parseInt(mAge.getText().toString()),
                                        mStreet_et.getText().toString(),
                                        mState_et.getText().toString()
                                        ,issue,qualification,licence);
                                moveToSupplierHome();
                                break;
                            case 4:
                                myRef.child(mVariable_et.getHint().toString()).setValue(mVariable_et.getText().toString());
                                licence=mVariable_et.getText().toString();
                                //Storing in local Database
                                dataTableHelper.insertItem(mName_et.getText().toString(),
                                        getIntent().getIntExtra(PROFILE_EXTRA, 0),
                                        Integer.parseInt(mAge.getText().toString()),
                                        mStreet_et.getText().toString(),
                                        mState_et.getText().toString()
                                        ,issue,qualification,licence);
                                moveToVendorHome();

                        }

                }
            }
        });



    }
    public void moveToHome(){
        Intent intent = new Intent(RegistrationActivity.this, StemActivity.class);
        intent.putExtra(PROFILE_EXTRA,getIntent().getIntExtra(PROFILE_EXTRA, 0));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void moveToDoctorHome(){
        Intent intent = new Intent(RegistrationActivity.this, DoctorActivity.class);
        intent.putExtra(PROFILE_EXTRA,getIntent().getIntExtra(PROFILE_EXTRA, 0));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void moveToPatientHome(){
        Intent intent = new Intent(RegistrationActivity.this, PatientActivity.class);
        intent.putExtra(PROFILE_EXTRA,getIntent().getIntExtra(PROFILE_EXTRA, 0));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void moveToVendorHome(){
        Intent intent = new Intent(RegistrationActivity.this, VendorActivity.class);
        intent.putExtra(PROFILE_EXTRA,getIntent().getIntExtra(PROFILE_EXTRA, 0));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public void moveToSupplierHome(){
        Intent intent = new Intent(RegistrationActivity.this, SupplierActivity.class);
        intent.putExtra(PROFILE_EXTRA,getIntent().getIntExtra(PROFILE_EXTRA, 0));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}

