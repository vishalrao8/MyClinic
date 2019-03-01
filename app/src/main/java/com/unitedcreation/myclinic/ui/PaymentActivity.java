package com.unitedcreation.myclinic.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;

public class PaymentActivity extends AppCompatActivity {

    TextView shareTextView;
    View paytmButton, bhimButton;
    FragmentTransaction ft;
    Fragment prev;
    DialogFragment dialogFragment;
    TextView mPaySuccess;
    ImageView mClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        bhimButton = findViewById(R.id.view_payment_bhim);
        paytmButton = findViewById(R.id.view_payment_paytm);
        shareTextView = findViewById(R.id.tv_payment_share);
        ft = getFragmentManager().beginTransaction();
        prev = getFragmentManager().findFragmentByTag("dialog");

        dialogFragment = new MyCustomDialogFragment();
        mClose = dialogFragment.getDialog().findViewById(R.id.iv_share_close);
        mClose.setOnClickListener(v -> {

            dialogFragment.dismiss();
            finish();

        });

        bhimButton.setOnClickListener(v -> {

            if (prev != null) {
                ft.remove(prev);
            }

            ft.addToBackStack(null);
            dialogFragment.show(getSupportFragmentManager(), "dialog");

        });

        paytmButton.setOnClickListener(v -> {

            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialogFragment.show(getSupportFragmentManager(), "dialog");

        });

        shareTextView.setOnClickListener(v -> {

            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            mPaySuccess = dialogFragment.getDialog().findViewById(R.id.tv_payment_sucess);
            mPaySuccess.setText("");
            dialogFragment.show(getSupportFragmentManager(), "dialog");

        });
    }

    public static class MyCustomDialogFragment extends DialogFragment {

        Boolean state = false;
        public MyCustomDialogFragment(){}

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // Do all the stuff to initialize your custom view

            return inflater.inflate(R.layout.activity_share, container, false);
        }
    }
}
