package com.unitedcreation.myclinic.ui;

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

    TextView mShare;
    View mPaytmPayment,mBhimPayment;
    FragmentTransaction ft;
    Fragment prev;
    DialogFragment dialogFragment;
    TextView mPaySuccess;
    ImageView mClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mBhimPayment = findViewById(R.id.view_payment_bhim);
        mPaytmPayment = findViewById(R.id.view_payment_paytm);
        mShare = findViewById(R.id.tv_payment_share);
        ft = getFragmentManager().beginTransaction();
        prev = getFragmentManager().findFragmentByTag("dialog");

        dialogFragment = new MyCustomDialogFragment();
        mClose = dialogFragment.getDialog().findViewById(R.id.iv_share_close);
        mClose.setOnClickListener(v -> {

            dialogFragment.dismiss();
            finish();

        });

        mBhimPayment.setOnClickListener(v -> {

            if (prev != null) {
                ft.remove(prev);
            }

            ft.addToBackStack(null);
            dialogFragment.show(getSupportFragmentManager(), "dialog");

        });

        mPaytmPayment.setOnClickListener(v -> {

            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialogFragment.show(getSupportFragmentManager(), "dialog");

        });

        mShare.setOnClickListener(v -> {

            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            mPaySuccess =dialogFragment.getDialog().findViewById(R.id.tv_payment_sucess);
            mPaySuccess.setText("");
            dialogFragment.show(getSupportFragmentManager(), "dialog");

        });
    }
    public static class MyCustomDialogFragment extends DialogFragment {

        Boolean state = false;
        public MyCustomDialogFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.activity_share, container, false);

            // Do all the stuff to initialize your custom view

            return v;
        }
    }
}
