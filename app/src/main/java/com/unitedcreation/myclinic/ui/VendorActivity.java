package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.SQLiteDatabase.DataContract;
import com.unitedcreation.myclinic.SQLiteDatabase.DataTableHelper;

import static com.unitedcreation.myclinic.utils.StringUtils.PROFILE_EXTRA;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class VendorActivity extends AppCompatActivity {
    @BindView(R.id.vendor_logout_button)
    ImageButton mLogout;
    DataTableHelper dataTableHelper;

    @BindView(R.id.tv_vendor_profile)
    TextView mName;

    @BindView(R.id.tv_vendor_licence)
    TextView mLicence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dataTableHelper=new DataTableHelper(this);
        switchTheme(this, 4);
        setContentView(R.layout.activity_vendor);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        Cursor cursor=dataTableHelper.getAllData();
        if(cursor.moveToNext()){
            mName.setText("Hi ,"+cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)));
            mLicence.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_LICENCE)));

        }
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FirebaseAuth.getInstance().signOut();
                dataTableHelper.deleteData();
                Log.i("LOGOUT","LOGOUT");
                finishAndRemoveTask();
            }
        });
    }
}
