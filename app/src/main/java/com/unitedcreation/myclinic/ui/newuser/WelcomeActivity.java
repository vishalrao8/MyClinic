package com.unitedcreation.myclinic.ui.newuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;


import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;
import com.unitedcreation.myclinic.database.DataTableHelper;
import com.unitedcreation.myclinic.ui.doctor.DoctorActivity;
import com.unitedcreation.myclinic.ui.patient.PatientActivity;
import com.unitedcreation.myclinic.ui.stemcell.StemActivity;
import com.unitedcreation.myclinic.ui.supplier.SupplierActivity;
import com.unitedcreation.myclinic.ui.vendor.VendorActivity;

import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.ViewUtils.moveToCorrespondingUi;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isLayoutOneVisible = true;

    @BindView(R.id.view_welcome_medical)
    View medicalKitButton;

    @BindView(R.id.view_welcome_stem)
    View stemButton;

    @BindView(R.id.view_mk_doctor)
    View doctorButton;

    @BindView(R.id.view_mk_patient)
    View patientButton;

    @BindView(R.id.view_mk_supplier)
    View supplierButton;

    @BindView(R.id.view_mk_vendor)
    View vendorButton;

    @BindView(R.id.layout_welcome_one)
    ConstraintLayout layoutOne;

    @BindView(R.id.layout_welcome_two)
    ConstraintLayout layoutTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);

        DataTableHelper dataTableHelper = new DataTableHelper(this);
        Cursor cursor = dataTableHelper.getAllData();

        if (cursor.getCount() != 0) {

            cursor.moveToNext();
            Class nextActivity = null;
            int position = cursor.getInt(cursor.getColumnIndex(DataContract.DataTable.P_ID));

            switch (position) {

                case 0:
                    nextActivity = StemActivity.class;
                    break;
                case 1:
                    nextActivity = DoctorActivity.class;
                    break;
                case 2:
                    nextActivity = PatientActivity.class;
                    break;
                case 3:
                    nextActivity = SupplierActivity.class;
                    break;
                case 4:
                    nextActivity = VendorActivity.class;

            }
            moveToCorrespondingUi(this, nextActivity, position);
        }
        cursor.close();

        medicalKitButton.setOnClickListener(v -> flipLayouts());

        stemButton.setOnClickListener(this);
        doctorButton.setOnClickListener(this);
        patientButton.setOnClickListener(this);
        supplierButton.setOnClickListener(this);
        vendorButton.setOnClickListener(this);

    }

    public void flipLayouts () {

        if (isLayoutOneVisible) {

            layoutOne.animate().alpha(0).setDuration(500).start();
            layoutTwo.animate().alpha(1).setDuration(500).start();

            layoutOne.setVisibility(View.GONE);
            layoutTwo.setVisibility(View.VISIBLE);

            isLayoutOneVisible = false;

        } else {

            layoutOne.animate().alpha(1).setDuration(500).start();
            layoutTwo.animate().alpha(0).setDuration(500).start();

            layoutOne.setVisibility(View.VISIBLE);
            layoutTwo.setVisibility(View.GONE);

            isLayoutOneVisible = true;

        }
    }

    public void moveToVerification (int position) {

        Intent intent = new Intent(this, VerifyActivity.class);
        intent.putExtra(PROFILE_EXTRA, position);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {

        if (!isLayoutOneVisible)
            flipLayouts();
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.view_mk_doctor: moveToVerification(1); break;

            case R.id.view_mk_patient: moveToVerification(2); break;

            case R.id.view_mk_supplier: moveToVerification(3); break;

            case R.id.view_mk_vendor: moveToVerification(4); break;

            default: moveToVerification(0);

        }
    }
}
