package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;
import com.unitedcreation.myclinic.database.DataTableHelper;
import com.unitedcreation.myclinic.model.Supplier;

import static com.unitedcreation.myclinic.utils.ViewUtils.moveToHome;
import static com.unitedcreation.myclinic.utils.ViewUtils.switchTheme;

public class SupplierActivity extends AppCompatActivity {

    DataTableHelper dataTableHelper;

    @BindView(R.id.supplier_logout_button)
    ImageButton mLogout;

    @BindView(R.id.tv_supplier_profile)
    TextView mName;

    @BindView(R.id.tv_supplier_licence)
    TextView mLicence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

        ButterKnife.bind(this);

        dataTableHelper = new DataTableHelper(this);

        Cursor cursor=dataTableHelper.getAllData();
        if(cursor.moveToNext()){
            mName.setText("Hi " + cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME)) + ",");
            mLicence.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_LICENCE)));

        }
        mLogout.setOnClickListener(v -> {

            // FirebaseAuth.getInstance().signOut();
            dataTableHelper.deleteData();
            Log.i("LOGOUT","LOGOUT");
            moveToHome(SupplierActivity.this);
            finish();
        });
    }

}
