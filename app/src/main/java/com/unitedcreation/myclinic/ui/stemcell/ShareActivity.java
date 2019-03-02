package com.unitedcreation.myclinic.ui.stemcell;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;

public class ShareActivity extends AppCompatActivity {
    @BindView(R.id.iv_share_close)
    ImageView mClose;

    @BindView(R.id.tv_payment_sucess)
    TextView mPaymentSucess;

    Boolean state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

        try{
            state=getIntent().getBooleanExtra("HIDE",false);
            if(state){
                mPaymentSucess.setVisibility(View.INVISIBLE);
            }
            else
                mPaymentSucess.setVisibility(View.VISIBLE);
        }
        catch (RuntimeException e){
            e.printStackTrace();
        }

        mClose.setOnClickListener(v -> finish());
    }
}
