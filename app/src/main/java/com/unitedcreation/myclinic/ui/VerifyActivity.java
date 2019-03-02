package com.unitedcreation.myclinic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OtpView;
import com.unitedcreation.myclinic.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.unitedcreation.myclinic.utils.PreferencesUtils.addUser;
import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class VerifyActivity extends AppCompatActivity{

    private boolean numberConfirmed;
    private static final String NAME = VerifyActivity.class.getSimpleName();

    EditText user_number_et;
    OtpView user_otp;
    TextView confirmTv;

    @BindView(R.id.view_verify_confirm)
    View confirmButton;

    private String verificationId;

    @BindView(R.id.tv_verify_info)
    TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        setContentView(R.layout.activity_verify);

        ButterKnife.bind(this);

        Log.i("STRING", String.valueOf(getIntent().getIntExtra(PROFILE_EXTRA, 0)));
        confirmTv = findViewById(R.id.tv_verify_confirm);
        user_number_et = findViewById(R.id.et_verify_phone);
        user_otp = findViewById(R.id.otp_view);
        user_number_et.setSelection(3);

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                firebaseAuth.removeAuthStateListener(this);
                addUser(getApplicationContext(), firebaseAuth.getUid());

            }
        });

        //Be sure for View visibility
        user_number_et.setVisibility(View.VISIBLE);
        user_otp.setVisibility(View.GONE);

        confirmButton.setOnClickListener(v -> {

            String userMobileNumber = user_number_et.getText().toString();

            if (!numberConfirmed) {

                if (!user_number_et.getText().toString().equals("") && user_otp.getVisibility() != View.VISIBLE) {

                    sendVerificationCode(userMobileNumber);

                    user_number_et.setVisibility(View.GONE);
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

            /**
             * Sometime the verification code is not detected automatically.
             * In that case the code will be null and the user has to manually enter the code.
             */
            if (code != null) {

                user_otp.setText(code);

                //verifying the code
                verifyVerificationCode(code);

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
                        moveToRegistration();

                    } else {

                        //verification unsuccessful.. display an error message
                        String message = "Something is wrong, we will fix it soon...";

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered...";
                        }

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        snackbar.show();

                    }
                });
    }

    public void moveToRegistration () {

        Intent intent = new Intent(VerifyActivity.this, RegistrationActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(PROFILE_EXTRA, getIntent().getIntExtra(PROFILE_EXTRA, 0));

        startActivity(intent);
        finish();
    }
}
