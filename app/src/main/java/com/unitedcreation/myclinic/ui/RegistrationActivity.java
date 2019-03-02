package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.model.Patient;
import com.unitedcreation.myclinic.model.StemCellUser;
import com.unitedcreation.myclinic.model.Supplier;
import com.unitedcreation.myclinic.model.Vendor;
import com.unitedcreation.myclinic.utils.FireBaseUtils;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getDataTableHelper;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.getUserId;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.PATIENT;
import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.STEM;
import static com.unitedcreation.myclinic.utils.StringUtils.SUPPLIER;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;
import static com.unitedcreation.myclinic.utils.StringUtils.VENDOR;
import static com.unitedcreation.myclinic.utils.ViewUtils.moveToCorrespondingUi;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class RegistrationActivity extends AppCompatActivity {

    private int position;

    private String type;
    private String uid;

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

        // Getting user identifier.
        position = getIntent().getIntExtra(PROFILE_EXTRA, 0);

        ButterKnife.bind(this);
        /*
          Since we are dealing with multiple users in a same app there4 we are identifying them by a single digit identifier ranging 0-5.
          0 for Stem
          1 for Doctor
          2 for Patient
          3 for Vendor
          4 for Supplier
         */
        switch (position) {

            case 0:
                type = STEM ;
                mVariable_cv.setVisibility(View.GONE);
                break;

            case 1:
                type = DOCTOR ;
                mVariable_cv.setVisibility(View.VISIBLE);
                mVariable_et.setHint("Qualifications");
                mLicence_cv.setVisibility(View.VISIBLE);
                break;

            case 2:
                type = PATIENT ;
                mVariable_cv.setVisibility(View.VISIBLE);
                mVariable_et.setHint("Issue");
                mLicence_cv.setVisibility(View.GONE);
                break;

            case 3:
                type = SUPPLIER ;
                mVariable_cv.setVisibility(View.VISIBLE);
                mVariable_et.setHint("Licence");
                break;

            case 4:
                type = VENDOR ;
                mVariable_cv.setVisibility(View.VISIBLE);
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
                Object object = null;
                Class nextActivity = null;

                switch (getIntent().getIntExtra(PROFILE_EXTRA, 0)) {

                    case 0:
                        object = new StemCellUser(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString());
                        nextActivity = StemActivity.class;
                        break;

                    case 1:
                        object = new Doctor(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString(),
                                mVariable_et.getText().toString(),
                                mLicence_et.getText().toString());
                        qualification = mVariable_et.getText().toString();
                        licence = mLicence_et.getText().toString();
                        nextActivity = DoctorActivity.class;
                        break;

                    case 2:
                        object = new Patient(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString(),
                                mVariable_et.getText().toString());
                        issue = mVariable_et.getText().toString();
                        nextActivity = PatientActivity.class;
                        break;

                    case 3:
                        object = new Vendor(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString(),
                                mVariable_et.getText().toString());
                        licence = mVariable_et.getText().toString();
                        nextActivity = VendorActivity.class;
                        break;

                    case 4:
                        object = new Supplier(mName_et.getText().toString(),
                                mStreet_et.getText().toString(),
                                mState_et.getText().toString(),
                                mCity_et.getText().toString(),
                                mZip_et.getText().toString(),
                                mVariable_et.getText().toString());
                        licence = mVariable_et.getText().toString();
                        nextActivity = SupplierActivity.class;

                }

                insertData(licence, issue, qualification, object);
                moveToCorrespondingUi(this, nextActivity, position);
            }
        });
    }

    private void insertData(String licence, String issue, String qualification, Object object) {

        if (object != null) {

            new FireBaseUtils().getDatabaseReference().child(USERS).child(type).child(uid).setValue(object);

            getDataTableHelper(this).insertItem(mName_et.getText().toString(),
                    position,
                    Integer.parseInt(mAge.getText().toString()),
                    mStreet_et.getText().toString(),
                    mState_et.getText().toString(),
                    issue,
                    qualification,
                    licence);
        }
    }
}