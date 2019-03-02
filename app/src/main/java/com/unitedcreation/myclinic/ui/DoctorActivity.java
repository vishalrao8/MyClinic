package com.unitedcreation.myclinic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unitedcreation.myclinic.adapter.DoctorRecyclerAdapter;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.database.DataContract;
import com.unitedcreation.myclinic.database.DataTableHelper;

import static com.unitedcreation.myclinic.utils.FireBaseUtils.SignOut;
import static com.unitedcreation.myclinic.utils.ViewUtils.moveToHome;

public class DoctorActivity extends AppCompatActivity {

    private DataTableHelper dataTableHelper;

    @BindView(R.id.doctor_logout_button)
    ImageButton logOutButton;

    @BindView(R.id.doctor_rv)
    RecyclerView doctorRecyclerView;

    @BindView(R.id.tv_doctor_profile)
    TextView doctorName;

    @BindView(R.id.tv_doctor_licence)
    TextView doctorLicence;

    @BindView(R.id.tv_doctor_qualification)
    TextView doctorQualification;

    private String time_list[] = {"10:10", "11:45", "12:15", "13:00", "14:30", "15:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        ButterKnife.bind(this);

        dataTableHelper = new DataTableHelper(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        doctorRecyclerView.setLayoutManager(layoutManager);

        DoctorRecyclerAdapter adapter = new DoctorRecyclerAdapter(time_list);
        doctorRecyclerView.setAdapter(adapter);
        Cursor cursor = dataTableHelper.getAllData();

        /**
         * Setting value to corresponding TextViews from the database.
         */
        if (cursor.moveToNext()) {

            doctorQualification.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_QUALIFICATION)));
            doctorLicence.setText(cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_LICENCE)));
            doctorName.setText(String.format("Hi %s,", cursor.getString(cursor.getColumnIndex(DataContract.DataTable.P_NAME))));

        }

        /**
         * Deleting all the user data from the database and Logging out the user on the click of logout button.
         */
        logOutButton.setOnClickListener(v -> SignOut(dataTableHelper, this));
    }
}
