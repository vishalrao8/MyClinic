package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;

public class PaymentActivity extends AppCompatActivity {
    TextView mShare;
    View mPaytmPayment,mBhimPayment;
    FragmentTransaction ft;
    Fragment prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mBhimPayment=findViewById(R.id.view_payment_bhim);
        mPaytmPayment=findViewById(R.id.view_payment_paytm);
        mShare=findViewById(R.id.tv_payment_share);
        ft = getFragmentManager().beginTransaction();
        prev = getFragmentManager().findFragmentByTag("dialog");

        mBhimPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mPaytmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFragment dialogFragment = new MyCustomDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }
    public static class MyCustomDialogFragment extends DialogFragment {
        Boolean state=false;
        public MyCustomDialogFragment(){}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_share, container, false);

            // Do all the stuff to initialize your custom view

            return v;
        }
    }
}
