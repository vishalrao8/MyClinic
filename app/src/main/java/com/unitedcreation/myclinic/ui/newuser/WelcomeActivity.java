package com.unitedcreation.myclinic.ui.newuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;
import com.unitedcreation.myclinic.database.DataTableHelper;
import com.unitedcreation.myclinic.ui.doctor.DoctorActivity;
import com.unitedcreation.myclinic.ui.patient.PatientActivity;
import com.unitedcreation.myclinic.ui.stemcell.StemActivity;
import com.unitedcreation.myclinic.ui.retailer.RetailerActivity;
import com.unitedcreation.myclinic.ui.pharmacist.PharmacistActivity;

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
            int position = cursor.getInt(cursor.getColumnIndex(DataContract.DataTable.P_ID));
            moveToCorrespondingUi(this, position);

        }
        cursor.close();

        medicalKitButton.setOnClickListener(v -> flipLayouts());

        stemButton.setOnClickListener(this);
        doctorButton.setOnClickListener(this);
        patientButton.setOnClickListener(this);
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

            default: moveToVerification(0);

        }
    }
    public  void emergency_call(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }
}
