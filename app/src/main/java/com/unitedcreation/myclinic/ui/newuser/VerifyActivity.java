package com.unitedcreation.myclinic.ui.newuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mukesh.OtpView;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.model.Doctor;
import com.unitedcreation.myclinic.model.Patient;
import com.unitedcreation.myclinic.model.StemCellUser;
import com.unitedcreation.myclinic.utils.FireBaseUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getDataTableHelper;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.addDoctorData;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.addPatientData;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.addStemUserData;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.addUser;
import static com.unitedcreation.myclinic.utils.PreferencesUtils.getUserId;
import static com.unitedcreation.myclinic.utils.StringUtils.DOCTOR;
import static com.unitedcreation.myclinic.utils.StringUtils.PATIENT;
import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.STEM;
import static com.unitedcreation.myclinic.utils.StringUtils.USERS;
import static com.unitedcreation.myclinic.utils.ViewUtils.moveToCorrespondingUi;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class VerifyActivity extends AppCompatActivity{

    private static final String NAME = VerifyActivity.class.getSimpleName();

    private int position;

    private String uid ;

    private String verificationId;
    private ProgressDialog progressDialog;

    private boolean numberConfirmed;

    @BindView(R.id.et_verify_phone)
    EditText mobileNumberEditText;

    @BindView(R.id.otp_view)
    OtpView user_otp;

    @BindView(R.id.tv_verify_confirm)
    TextView confirmTv;

    @BindView(R.id.view_verify_confirm)
    View confirmButton;

    @BindView(R.id.tv_verify_info)
    TextView infoTextView;

    @BindView(R.id.pb_verify_progress)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        setContentView(R.layout.activity_verify);

        ButterKnife.bind(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Verifying User ...");

        position = getIntent().getIntExtra(PROFILE_EXTRA, 0);

        mobileNumberEditText.setSelection(3);

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                firebaseAuth.removeAuthStateListener(this);
                addUser(getApplicationContext(), firebaseAuth.getUid());

            }
        });
        uid=getUserId(VerifyActivity.this);

        confirmButton.setOnClickListener(v -> {

            String userMobileNumber = mobileNumberEditText.getText().toString();

            if (!numberConfirmed) {

                if (userMobileNumber.length() == 13) {

                    sendVerificationCode(userMobileNumber);

                    mobileNumberEditText.setVisibility(View.GONE);
                    user_otp.setVisibility(View.VISIBLE);

                    user_otp.requestFocus();

                    confirmTv.setText(R.string.verify_submit);
                    infoTextView.setText(String.format("%s %s", getString(R.string.verify_enter_otp_info), userMobileNumber));

                    numberConfirmed = true;

                }

            } else {

                if (Objects.requireNonNull(user_otp.getText()).length() == 6)
                    verifyVerificationCode(Objects.requireNonNull(user_otp.getText()).toString());

            }
        });
    }

    /**
     * Method to send verification code to provided mobile number.
     * @param mobile Mobile number provided by the user.
     */
    private void sendVerificationCode(String mobile) {

        progressBar.setVisibility(View.VISIBLE);

        Log.d(NAME, "Sending verification code to " + mobile);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
               this,
                mCallbacks);
    }

    /**
     * Callback functions to keep track of the verification status.
     */
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            Log.d (NAME, "Verification Complete");

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            Log.d(NAME, "Verification code is " + code);

            /*
              Sometime the verification code is not detected automatically.
              In that case the code will be null and the user has to manually enter the code.
             */
            if (code != null) {

                user_otp.setText(code);

                //verifying the code
                verifyVerificationCode(code);

            }else{
                checkIfUserExists();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Log.d(NAME, "Verification failed");
            Toast.makeText(VerifyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            progressBar.setVisibility(View.GONE);
            progressDialog.show();
            Log.d(NAME, "Code sent successfully");
            //Keeping the verification id sent to the user.
            verificationId = s;

        }
    };

    /**
     * Method to verify authentication code
     * @param code Authentication code generated and sent to the user.
     */
    void verifyVerificationCode(String code) {

        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){

        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(VerifyActivity.this, task -> {

                    if (task.isSuccessful()) {

                        Log.d(NAME, "Singing in successful");
                        checkIfUserExists();

                    } else {

                        //verification unsuccessful.. display an error message
                        String message = "Something is wrong, we will fix it soon...";

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered...";
                        }

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Dismiss", v -> {

                        });
                        snackbar.show();

                    }
                });
    }

    public void checkIfUserExists() {

        new FireBaseUtils().getDatabaseReference().child(USERS).child(Objects.requireNonNull(getType())).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                if (dataSnapshot.exists()) {

                    moveToCorrespondingUi(VerifyActivity.this, position);
                    fillDatabase(dataSnapshot);

                }
                else
                    moveToRegistration();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });

    }

    private void fillDatabase(DataSnapshot dataSnapshot) {

        String licence = null, issue = null, qualification = null;

        switch (position) {

            case 0:
                StemCellUser stemCellUser = dataSnapshot.getValue(StemCellUser.class);
                addStemUserData(this,stemCellUser);
                break;

            case 1:
                Doctor doctor = dataSnapshot.getValue(Doctor.class);
                addDoctorData(this,doctor);
                break;

            case 2:
                Patient patient = dataSnapshot.getValue(Patient.class);
                addPatientData(this,patient);
                break;

        }

    }

    private void moveToRegistration () {

        Intent intent = new Intent(VerifyActivity.this, RegistrationActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(PROFILE_EXTRA, position);

        startActivity(intent);
    }

    private String getType() {

        switch (position) {
            case 0:
                return STEM ;

            case 1:
                return DOCTOR ;

            case 2:
                return PATIENT ;
        }
        return null;
    }
    public  void emergency_call(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }
}
