package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.unitedcreation.myclinic.R;

import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.POSITION_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.PATIENT_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.STEM_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.SUPPLIER_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.VENDOR_EXTRA;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean isLayoutOneVisible = true;

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

    public void moveToVerification (String profile, int position) {

        Intent intent = new Intent(this, VerifyActivity.class);
        intent.putExtra(PROFILE_EXTRA, profile);
        intent.putExtra(POSITION_EXTRA, position);
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

            case R.id.view_mk_doctor: moveToVerification(DOCTOR_EXTRA, 1); break;

            case R.id.view_mk_patient: moveToVerification(PATIENT_EXTRA, 2); break;

            case R.id.view_mk_supplier: moveToVerification(SUPPLIER_EXTRA, 3); break;

            case R.id.view_mk_vendor: moveToVerification(VENDOR_EXTRA, 4); break;

            default: moveToVerification(STEM_EXTRA, 0);

        }
    }
}
