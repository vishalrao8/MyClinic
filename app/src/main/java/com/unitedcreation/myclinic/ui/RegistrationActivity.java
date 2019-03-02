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
import android.widget.Toast;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataTableHelper;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.model.Patient;
import com.unitedcreation.myclinic.model.StemCellUser;
import com.unitedcreation.myclinic.model.Supplier;
import com.unitedcreation.myclinic.model.Vendor;

import static com.unitedcreation.myclinic.utils.FireBaseUtils.getDatabaseReference;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.getUserId;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.PATIENT;
import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.STEM;
import static com.unitedcreation.myclinic.utils.StringUtils.SUPPLIER;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;
import static com.unitedcreation.myclinic.utils.StringUtils.VENDOR;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class RegistrationActivity extends AppCompatActivity {

    private String child;
    private String uid;

    private int primaryKey;

    private DataTableHelper dataTableHelper;

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

    @BindView(R.id.view_registration_fill)
    View fillButton;

    @BindView(R.id.cv_registration_variable)
    CardView mVariable_cv;

    @BindView(R.id.cv_registration_licence)
    CardView mLicence_cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        setContentView(R.layout.activity_registration);

        // Getting user id from Shared Preferences.
        uid = getUserId(this);

        // Initiating primary key for SQLiteDatabase
        primaryKey = getIntent().getIntExtra(PROFILE_EXTRA, 0);

        ButterKnife.bind(this);
        dataTableHelper = new DataTableHelper(this);
        Log.i("TAG", String.valueOf(getIntent().getIntExtra(PROFILE_EXTRA, 0)));
        /**
         *As we are dealing with multiple users in a same app we are identifying them by single digit id 0-5 to be precise
         * 0 for Stem
         * 1 for Doctor
         * 2 for Patient
         * 3 for Vendor
         * 4 for Supplier
         */
        switch (getIntent().getIntExtra(PROFILE_EXTRA, 0)) {

            case 0:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child = STEM ;
                //Hiding Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.GONE);
                break;

            case 1:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child = DOCTOR ;
                //Showing Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.VISIBLE);
                //Renaming Issue/Qualification EditText hint to Qualifications
                mVariable_et.setHint("Qualifications");
                //Showing Licence EditText from Registration Activity
                mLicence_cv.setVisibility(View.VISIBLE);
                break;

            case 2:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child = PATIENT ;
                //Showing Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.VISIBLE);
                //Renaming Issue/Qualification EditText hint to Issue
                mVariable_et.setHint("Issue");
                //Hiding Licence EditText from Registration Activity
                mLicence_cv.setVisibility(View.GONE);
                break;

            case 3:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child = SUPPLIER ;
                //Showing Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.VISIBLE);
                //Renaming Issue/Qualification EditText hint to Licence
                mVariable_et.setHint("Licence");
                break;

            case 4:
                // Setting child variable ,which decides branch of the user according to the key valued passed from previous activities
                child = VENDOR ;
                //Showing Issue/Qualification EditText from Registration Activity
                mVariable_cv.setVisibility(View.VISIBLE);
                //Renaming Issue/Qualification EditText hint to Licence
                mVariable_et.setHint("Licence");

        }

        fillButton.setOnClickListener(v -> {

            if (mCity_et.getText().toString().equals("")
                    || mName_et.getText().toString().equals("")
                    || mState_et.getText().toString().equals("")
                    || mStreet_et.getText().toString().equals("")
                    || mAge.getText().toString().equals("")) {

                Toast.makeText(getApplicationContext(),"Enter Al Details", Toast.LENGTH_LONG).show();

            } else {

                String licence = null, issue = null, qualification = null;

                switch (getIntent().getIntExtra(PROFILE_EXTRA, 0)) {

                    case 0:
                        StemCellUser stemCellUser = new StemCellUser(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString());
                        insertData(licence, issue, qualification, stemCellUser);
                        moveToStemHome();
                        break;

                    case 1:
                        Doctor doctor = new Doctor(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString(),
                                mVariable_et.getText().toString(),
                                mLicence_et.getText().toString());
                        qualification = mVariable_et.getText().toString();
                        licence = mLicence_et.getText().toString();
                        insertData(licence, issue, qualification, doctor);
                        moveToDoctorHome();
                        break;

                    case 2:
                        Patient patient = new Patient(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString(),
                                mVariable_et.getText().toString());
                        issue = mVariable_et.getText().toString();
                        insertData(licence, issue, qualification, patient);
                        moveToPatientHome();
                        break;

                    case 3:
                        Vendor vendor = new Vendor(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString(),
                                mVariable_et.getText().toString());
                        licence = mVariable_et.getText().toString();
                        insertData(licence, issue, qualification, vendor);
                        moveToSupplierHome();
                        break;

                    case 4:
                        Supplier supplier = new Supplier(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString(),
                                mVariable_et.getText().toString());
                        licence = mVariable_et.getText().toString();
                        //Storing in local Database
                        insertData(licence, issue, qualification, supplier);
                        moveToVendorHome();

                }
            }
        });
    }

    private void insertData(String licence, String issue, String qualification, Object user) {

        getDatabaseReference().child(USERS).child(child).child(uid).setValue(user);

        dataTableHelper.insertItem(mName_et.getText().toString(),
                primaryKey,
                Integer.parseInt(mAge.getText().toString()),
                mStreet_et.getText().toString(),
                mState_et.getText().toString(),
                issue,
                qualification,
                licence);

    }

    public void moveToStemHome(){
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