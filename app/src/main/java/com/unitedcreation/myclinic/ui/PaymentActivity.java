package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mShare;
    View mPaytmPayment,mBhimPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mBhimPayment = findViewById(R.id.view_payment_bhim);
        mBhimPayment.setOnClickListener(this);
        mPaytmPayment = findViewById(R.id.view_payment_paytm);
        mPaytmPayment.setOnClickListener(this);
        mShare = findViewById(R.id.tv_payment_share);
        mShare.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(PaymentActivity.this,ShareActivity.class);
        if(v.getId()==R.id.tv_payment_share){
            intent.putExtra("HIDE",true);
        }
        startActivity(intent);
        finish();
    }
}
