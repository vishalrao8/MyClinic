package com.unitedcreation.myclinic.ui.pharmacist;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;

import static com.unitedcreation.myclinic.utils.DatabaseUtils.getCursor;
import static com.unitedcreation.myclinic.utils.FireBaseUtils.SignOut;

public class PharmacistActivity extends AppCompatActivity {

    @BindView(R.id.vendor_logout_button)
    ImageButton logOutButton;

    @BindView(R.id.tv_vendor_profile)
    TextView mName;

    @BindView(R.id.tv_vendor_licence)
    TextView mLicence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist);

        ButterKnife.bind(this);

        Cursor cursor = getCursor(this);

        if(cursor.moveToNext()){

            mName.setText(String.format("Hi %s,", cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME))));
            mLicence.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_LICENCE)));

        }
        cursor.close();

        logOutButton.setOnClickListener(v -> SignOut(this));

    }
}
