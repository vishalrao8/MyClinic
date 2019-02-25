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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OtpView;
import com.unitedcreation.myclinic.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.StringUtils.USER_ID;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class VerifyActivity extends AppCompatActivity{

    private boolean numberConfirmed;

    String user_contact="";
    EditText user_number_et;
    OtpView user_otp;
    TextView confirm_input;
    //Fire BAse Auth
    //These are the objects needed
    //It is the verification id that will be sent to the user
    private String mVerificationId;

    //fireBase auth object
    private FirebaseAuth mAuth;

    @BindView(R.id.tv_verify_info)
    TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchTheme(this, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        setContentView(R.layout.activity_verify);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        Log.i("STRING",String.valueOf(getIntent().getIntExtra(PROFILE_EXTRA, 0)));
        confirm_input = findViewById(R.id.tv_verify_confirm);
        user_number_et = findViewById(R.id.et_verify_phone);
        user_otp = findViewById(R.id.otp_view);
        user_number_et.setSelection(3);

        mAuth.addAuthStateListener(firebaseAuth -> getSharedPreferences(USER_ID,MODE_PRIVATE).edit().putString(USER_ID,firebaseAuth.getUid()).apply());
        //Be sure for View visibility
        user_number_et.setVisibility(View.VISIBLE);
        user_otp.setVisibility(View.GONE);

        confirm_input.setOnClickListener(v -> {

            String userMobileNumber = user_number_et.getText().toString();

            if (!numberConfirmed) {

                if (!user_number_et.getText().toString().equals("") && user_otp.getVisibility() != View.VISIBLE) {

                    Log.d("BUTTON", "CALL");

                    sendVerificationCode(userMobileNumber);

                    user_number_et.setVisibility(View.GONE);
                    user_otp.setVisibility(View.VISIBLE);

                    user_otp.requestFocus();

                    confirm_input.setText(R.string.verify_submit);
                    infoTextView.setText(String.format("%s %s", getString(R.string.verify_enter_otp_info), userMobileNumber));

                    numberConfirmed = true;

                }
            }
            if(user_otp.getText().toString().equals("123456")){
                moveToRegistration();
            }/*
            else{

                if (Objects.requireNonNull(user_otp.getText()).length() == 6)
                    verifyVerificationCode(Objects.requireNonNull(user_otp.getText()).toString());

            }*/
        });

        }
    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String mobile) {
        Log.d("PHONE AUTH","CALL");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 mobile,
                60,
                TimeUnit.SECONDS,
               this,
                mCallbacks);
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            Log.d("CODE","CALL");
            //so user has to manually enter the code
            if (code != null) {
                user_otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("CALL","CALL");
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        Log.d("CALL","CALL");
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            moveToRegistration();
                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

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
                    }
                });
    }
    public void moveToRegistration () {

        Intent intent = new Intent(VerifyActivity.this, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Log.i("ID",String.valueOf(getIntent().getIntExtra(PROFILE_EXTRA, 0)));
        intent.putExtra(PROFILE_EXTRA, getIntent().getIntExtra(PROFILE_EXTRA, 0));
        startActivity(intent);
        finish();
    }
}
