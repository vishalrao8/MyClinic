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
import com.unitedcreation.myclinic.database.DataTableHelper;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.model.Patient;
import com.unitedcreation.myclinic.model.StemCellUser;
import com.unitedcreation.myclinic.model.Supplier;
import com.unitedcreation.myclinic.model.Vendor;

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

    //Edit Texts

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
        /**
         *As we are dealing with multiple users in a same app we are identifying them by single digit id 0-5 to be precise
         * 0 for Stem
         * 1 for Doctor
         * 2 for Patient
         * 3 for Vendor
         * 4 for Supplier
         */
        switch (getIntent().getIntExtra(PROFILE_EXTRA, 0))
        {
            case 0:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child= STEM ;
                //Hiding Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.GONE);
                break;
            case 1:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child= DOCTOR ;
                //Showing Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.VISIBLE);
                //Renaming Issue/Qualification EditText hint to Qualifications
                mVariable_et.setHint("Qualifications");
                //Showing Licence EditText from Registration Activity
                mLicence_cv.setVisibility(View.VISIBLE);
                break;
            case 2:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child= PATIENT ;
                //Showing Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.VISIBLE);
                //Renaming Issue/Qualification EditText hint to Issue
                mVariable_et.setHint("Issue");
                //Hiding Licence EditText from Registration Activity
                mLicence_cv.setVisibility(View.GONE);
                break;
            case 3:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child= SUPPLIER ;
                //Showing Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.VISIBLE);
                //Renaming Issue/Qualification EditText hint to Licence
                mVariable_et.setHint("Licence");
                break;
            case 4:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child= VENDOR ;
                //Showing Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.VISIBLE);
                //Renaming Issue/Qualification EditText hint to Licence
                mVariable_et.setHint("Licence");
        }

        // Creating a Firebase Database Instance to root directory
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Changing reference to a fresh leaf node for storing data according to child Variable

            DatabaseReference myRef = database.getReference(USERS).
                    child(child).child(getSharedPreferences(USER_ID, MODE_PRIVATE).getString(USER_ID, "O"));

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
                {String licence=null,issue=null,qualification=null;

                        switch (getIntent().getIntExtra(PROFILE_EXTRA, 0)){
                            case 0:
                                //Storing in local Database
                                StemCellUser stemCellUser=new StemCellUser(mName_et.getText().toString(),mStreet_et.getText().toString(),mState_et.getText().toString()
                                        ,mCity_et.getText().toString(),mZip_et.getText().toString());
                                myRef.setValue(stemCellUser);
                                dataTableHelper.insertItem(mName_et.getText().toString(),
                                        getIntent().getIntExtra(PROFILE_EXTRA, 0),
                                        Integer.parseInt(mAge.getText().toString()),
                                        mStreet_et.getText().toString(),
                                        mState_et.getText().toString()
                                        ,issue,qualification,licence);
                                moveToHome();
                                break;
                            case 1:
                                Doctor doctor=new Doctor(mName_et.getText().toString(),mStreet_et.getText().toString(),mState_et.getText().toString()
                                        ,mCity_et.getText().toString(),mZip_et.getText().toString(),mVariable_et.getText().toString(),mLicence_et.getText().toString());
                                myRef.setValue(doctor);
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
                                Patient patient=new Patient(mName_et.getText().toString(),mStreet_et.getText().toString(),mState_et.getText().toString()
                                        ,mCity_et.getText().toString(),mZip_et.getText().toString(),mVariable_et.getText().toString());
                                myRef.setValue(patient);
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
                                Vendor vendor=new Vendor(mName_et.getText().toString(),mStreet_et.getText().toString(),mState_et.getText().toString()
                                        ,mCity_et.getText().toString(),mZip_et.getText().toString(),mVariable_et.getText().toString());
                                myRef.setValue(vendor);
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
                                Supplier supplier=new Supplier(mName_et.getText().toString(),mStreet_et.getText().toString(),mState_et.getText().toString()
                                    ,mCity_et.getText().toString(),mZip_et.getText().toString(),mVariable_et.getText().toString());
                                myRef.setValue(supplier);
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

